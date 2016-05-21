package be.lambdaware;

import be.lambdaware.enums.Sex;
import be.lambdaware.enums.UserRole;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ModuloBackendApplication.class)
@WebAppConfiguration
public class UserControllerTest {

    static {
        System.setProperty("spring.datasource.url", "jdbc:postgresql://localhost:5433/modulo_test");
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc rest;

    @Before
    public void setup() throws Exception {
        rest = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGet() throws Exception {
        rest.perform(get("/user/id/1")).andExpect(status().isForbidden());

        rest.perform(get("/user/id/1").header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("admin@school.be"))
                .andExpect(jsonPath("$.firstName").value("Pieter"))
                .andExpect(jsonPath("$.lastName").value("Beheerder"))
                .andExpect(jsonPath("$.role").value("ADMIN"));

        rest.perform(get("/user/id/-1").header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isNotFound());

        rest.perform(get("/user/id/test").header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAll() throws Exception {
        rest.perform(get("/user/all")).andExpect(status().isForbidden());
        rest.perform(get("/user/all").header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testGetByEmail() throws Exception {
        rest.perform(get("/user/email/admin@school.be")).andExpect(status().isForbidden());

        rest.perform(get("/user/email/admin@school.be").header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("admin@school.be"))
                .andExpect(jsonPath("$.firstName").value("Pieter"))
                .andExpect(jsonPath("$.lastName").value("Beheerder"))
                .andExpect(jsonPath("$.role").value("ADMIN"));

        rest.perform(get("/user/email/nomatch@test.com").header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void AddParent() throws Exception {
        User user = new User();
        user.setRole(UserRole.PARENT);
        user.setEmail("jan.vermeulen@hotmail.com");
        user.setFirstName("Jan");
        user.setLastName("Vermeulen");
        user.setSex(Sex.MALE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonUser = ow.writeValueAsString(user);

        rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser)).andExpect(status().isForbidden());

        MvcResult result = rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser).header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jan.vermeulen@hotmail.com"))
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Vermeulen"))
                .andExpect(jsonPath("$.role").value("PARENT"))
                .andExpect(jsonPath("$.sex").value("MALE"))
                .andReturn();

        jsonUser = result.getResponse().getContentAsString();
        User getUser = mapper.readValue(jsonUser, User.class);
        rest.perform(delete("/user/id/"+getUser.getId())).andExpect(status().isForbidden());

        rest.perform(delete("/user/id/"+getUser.getId()).header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk());

    }

    @Test
    public void AddTeacher() throws Exception {
        User user = new User();
        user.setRole(UserRole.TEACHER);
        user.setEmail("kris.fit@hotmail.com");
        user.setFirstName("Kris");
        user.setLastName("Fit");
        user.setSex(Sex.MALE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonUser = ow.writeValueAsString(user);

        rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser)).andExpect(status().isForbidden());

        MvcResult result = rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser).header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("kris.fit@hotmail.com"))
                .andExpect(jsonPath("$.firstName").value("Kris"))
                .andExpect(jsonPath("$.lastName").value("Fit"))
                .andExpect(jsonPath("$.role").value("TEACHER"))
                .andExpect(jsonPath("$.sex").value("MALE"))
                .andReturn();

        jsonUser = result.getResponse().getContentAsString();
        User getUser = mapper.readValue(jsonUser, User.class);
        rest.perform(delete("/user/id/"+getUser.getId())).andExpect(status().isForbidden());

        rest.perform(delete("/user/id/"+getUser.getId()).header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk());

    }

    @Test
    public void AddAdmin() throws Exception {
        User user = new User();
        user.setRole(UserRole.ADMIN);
        user.setEmail("adda.man@hotmail.com");
        user.setFirstName("Adda");
        user.setLastName("Man");
        user.setSex(Sex.FEMALE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonUser = ow.writeValueAsString(user);

        rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser)).andExpect(status().isForbidden());

        MvcResult result = rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser).header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("adda.man@hotmail.com"))
                .andExpect(jsonPath("$.firstName").value("Adda"))
                .andExpect(jsonPath("$.lastName").value("Man"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.sex").value("FEMALE"))
                .andReturn();

        jsonUser = result.getResponse().getContentAsString();
        User getUser = mapper.readValue(jsonUser, User.class);
        rest.perform(delete("/user/id/"+getUser.getId())).andExpect(status().isForbidden());

        rest.perform(delete("/user/id/"+getUser.getId()).header("X-Auth", "YWRtaW5Ac2Nob29sLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk());

    }
}