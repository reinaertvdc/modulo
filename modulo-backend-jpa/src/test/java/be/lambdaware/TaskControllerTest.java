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
public class TaskControllerTest {

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
    public void AddTask() throws Exception {
        Task task = new Task();
        task.setName("New Task");
        task.setDescription("Description");
        task.setDeadline(new Date(1993514));

        MvcResult resultClass = rest.perform(get("/class/id/1").contentType(MediaType.APPLICATION_JSON_UTF8).header("X-Auth", "c3RldmVuLmphY29ic0B0aWhoLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        String jsonClass = resultClass.getResponse().getContentAsString();
        Clazz getClass = mapper.readValue(jsonClass, Clazz.class);
        task.setClazz(getClass);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonTask = ow.writeValueAsString(task);

        rest.perform(post("/task/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonTask)).andExpect(status().isForbidden());

        MvcResult resultTask = rest.perform(post("/task/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonTask).header("X-Auth", "c3RldmVuLmphY29ic0B0aWhoLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Task"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.deadline").value(new Date(1993514).toString()))
                .andExpect(jsonPath("$.clazz.id").value(1))
                .andReturn();

        jsonTask = resultTask.getResponse().getContentAsString();
        Task getTask = mapper.readValue(jsonTask, Task.class);

        rest.perform(delete("/task/id/"+getTask.getId())).andExpect(status().isForbidden());

        rest.perform(delete("/task/id/"+getTask.getId()).header("X-Auth", "c3RldmVuLmphY29ic0B0aWhoLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk());

    }
}
