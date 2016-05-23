package be.lambdaware;

import be.lambdaware.enums.ClassType;
import be.lambdaware.enums.Sex;
import be.lambdaware.enums.UserRole;
import be.lambdaware.models.Clazz;
import be.lambdaware.models.StudentInfo;
import be.lambdaware.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ModuloBackendApplication.class)
@WebAppConfiguration
public class ClassControllerTest {

    static {
        System.setProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/modulo_test");
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc rest;

    @Before
    public void setup() throws Exception {
        rest = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void AddClass() throws Exception {
        Clazz clazz = new Clazz();
        clazz.setType(ClassType.PAV);
        clazz.setName("New Pav class");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonClass = ow.writeValueAsString(clazz);

        rest.perform(post("/class/teacher/id/3/grade/id/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonClass)).andExpect(status().isForbidden());
        rest.perform(post("/class/teacher/id/1/grade/id/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonClass).header("X-Auth", "c3RldmVuLmphY29ic0B0aWhoLmJlOnBhc3N3b3Jk"))
        .andExpect(status().isBadRequest());

        MvcResult result = rest.perform(post("/class/teacher/id/3/grade/id/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonClass).header("X-Auth", "c3RldmVuLmphY29ic0B0aWhoLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Pav class"))
                .andExpect(jsonPath("$.type").value("PAV"))
                .andReturn();

        jsonClass = result.getResponse().getContentAsString();
        Clazz getClazz = mapper.readValue(jsonClass, Clazz.class);

        rest.perform(delete("/class/id/"+getClazz.getId())).andExpect(status().isForbidden());

        rest.perform(delete("/class/id/"+getClazz.getId()).header("X-Auth", "c3RldmVuLmphY29ic0B0aWhoLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk());

    }
}