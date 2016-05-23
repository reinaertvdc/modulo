package be.lambdaware;

import be.lambdaware.enums.ClassType;
import be.lambdaware.enums.Sex;
import be.lambdaware.enums.UserRole;
import be.lambdaware.models.*;
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
public class CourseTopicControllerTest {

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
    public void AddCourseTopic() throws Exception {
        CourseTopic courseTopic = new CourseTopic();
        courseTopic.setName("New Course Topic");
        courseTopic.setDescription("Description");

        MvcResult resultGrade = rest.perform(get("/grade/id/1").contentType(MediaType.APPLICATION_JSON_UTF8).header("X-Auth", "c3RldmVuLmphY29ic0B0aWhoLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        String jsonGrade = resultGrade.getResponse().getContentAsString();
        Grade getGrade = mapper.readValue(jsonGrade, Grade.class);

        courseTopic.setGrade(getGrade);


        MvcResult resultClass = rest.perform(get("/class/id/1").contentType(MediaType.APPLICATION_JSON_UTF8).header("X-Auth", "c3RldmVuLmphY29ic0B0aWhoLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonClass = resultClass.getResponse().getContentAsString();
        Clazz getClass = mapper.readValue(jsonClass, Clazz.class);

        courseTopic.setClazz(getClass);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonCourseTopic = ow.writeValueAsString(courseTopic);

        rest.perform(post("/coursetopic/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonCourseTopic)).andExpect(status().isForbidden());

        MvcResult resultCT = rest.perform(post("/coursetopic/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonCourseTopic).header("X-Auth", "c3RldmVuLmphY29ic0B0aWhoLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Course Topic"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.grade.name").value("2e graad 1e en 2e leerjaar"))
                .andReturn();

        jsonCourseTopic = resultCT.getResponse().getContentAsString();
        CourseTopic getCCourseTopic = mapper.readValue(jsonCourseTopic, CourseTopic.class);

        rest.perform(delete("/coursetopic/id/"+getCCourseTopic.getId())).andExpect(status().isForbidden());

        rest.perform(delete("/coursetopic/id/"+getCCourseTopic.getId()).header("X-Auth", "c3RldmVuLmphY29ic0B0aWhoLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk());

    }
}
