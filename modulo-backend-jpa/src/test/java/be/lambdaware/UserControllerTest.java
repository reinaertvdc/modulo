package be.lambdaware;

import be.lambdaware.enums.Sex;
import be.lambdaware.enums.UserRole;
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
public class UserControllerTest {

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
    public void testGet() throws Exception {
        rest.perform(get("/user/id/1")).andExpect(status().isForbidden());

        rest.perform(get("/user/id/1").header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("pieter.goossens@tihh.be"))
                .andExpect(jsonPath("$.firstName").value("Pieter"))
                .andExpect(jsonPath("$.lastName").value("Goossens"))
                .andExpect(jsonPath("$.role").value("ADMIN"));

        rest.perform(get("/user/id/-1").header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isNotFound());

        rest.perform(get("/user/id/test").header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAll() throws Exception {
        rest.perform(get("/user/all")).andExpect(status().isForbidden());
        rest.perform(get("/user/all").header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testGetByEmail() throws Exception {
        rest.perform(get("/user/email/pieter.goossens@tihh.be")).andExpect(status().isForbidden());

        rest.perform(get("/user/email/pieter.goossens@tihh.be").header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("pieter.goossens@tihh.be"))
                .andExpect(jsonPath("$.firstName").value("Pieter"))
                .andExpect(jsonPath("$.lastName").value("Goossens"))
                .andExpect(jsonPath("$.role").value("ADMIN"));

        rest.perform(get("/user/email/nomatch@test.com").header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
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

        MvcResult result = rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jan.vermeulen@hotmail.com"))
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Vermeulen"))
                .andExpect(jsonPath("$.role").value("PARENT"))
                .andExpect(jsonPath("$.sex").value("MALE"))
                .andReturn();

        jsonUser = result.getResponse().getContentAsString();
        User getUser = mapper.readValue(jsonUser, User.class);

        //try adding a user with an already existing email
        rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isBadRequest());

        rest.perform(delete("/user/id/"+getUser.getId())).andExpect(status().isForbidden());

        rest.perform(delete("/user/id/"+getUser.getId()).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
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

        MvcResult result = rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("kris.fit@hotmail.com"))
                .andExpect(jsonPath("$.firstName").value("Kris"))
                .andExpect(jsonPath("$.lastName").value("Fit"))
                .andExpect(jsonPath("$.role").value("TEACHER"))
                .andExpect(jsonPath("$.sex").value("MALE"))
                .andReturn();

        jsonUser = result.getResponse().getContentAsString();
        User getUser = mapper.readValue(jsonUser, User.class);

        //try adding a user with an already existing email
        rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isBadRequest());

        rest.perform(delete("/user/id/"+getUser.getId())).andExpect(status().isForbidden());

        rest.perform(delete("/user/id/"+getUser.getId()).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
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

        MvcResult result = rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("adda.man@hotmail.com"))
                .andExpect(jsonPath("$.firstName").value("Adda"))
                .andExpect(jsonPath("$.lastName").value("Man"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.sex").value("FEMALE"))
                .andReturn();

        jsonUser = result.getResponse().getContentAsString();
        User getUser = mapper.readValue(jsonUser, User.class);

        //try adding a user with an already existing email
        rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isBadRequest());

        rest.perform(delete("/user/id/"+getUser.getId())).andExpect(status().isForbidden());

        rest.perform(delete("/user/id/"+getUser.getId()).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isOk());

    }


    @Test
    public void AddStudent() throws Exception {
        User user = new User();
        user.setRole(UserRole.STUDENT);
        user.setEmail("alfred.danken@tihh.be");
        user.setFirstName("Alfred");
        user.setLastName("Danken");
        user.setSex(Sex.MALE);

        StudentInfo info = new StudentInfo();
        info.setBankAccount("BE0123456789");
        Date date = new Date(1993514);
        info.setBirthDate(date);
        info.setBirthPlace("testCity");
        info.setCity("testCity");
        info.setEmergencyNumber("00123456321");
        info.setHouseNumber("26");
        info.setNationalIdentificationNumber("987654321");
        info.setNationality("belgische");
        info.setPhoneNumber("00321654123");
        info.setPostalCode("4565");
        info.setStreet("testStreet");

        user.setStudentInfo(info);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonUser = ow.writeValueAsString(user);

        rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser)).andExpect(status().isForbidden());

        MvcResult result = rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("alfred.danken@tihh.be"))
                .andExpect(jsonPath("$.firstName").value("Alfred"))
                .andExpect(jsonPath("$.lastName").value("Danken"))
                .andExpect(jsonPath("$.role").value("STUDENT"))
                .andExpect(jsonPath("$.sex").value("MALE"))
                .andExpect(jsonPath("$.studentInfo.city").value("testCity"))
                .andExpect(jsonPath("$.studentInfo.bankAccount").value("BE0123456789"))
                .andExpect(jsonPath("$.studentInfo.birthDate").value(date.toString()))
                .andExpect(jsonPath("$.studentInfo.birthPlace").value("testCity"))
                .andExpect(jsonPath("$.studentInfo.emergencyNumber").value("00123456321"))
                .andExpect(jsonPath("$.studentInfo.houseNumber").value("26"))
                .andExpect(jsonPath("$.studentInfo.nationalIdentificationNumber").value("987654321"))
                .andExpect(jsonPath("$.studentInfo.nationality").value("belgische"))
                .andExpect(jsonPath("$.studentInfo.phoneNumber").value("00321654123"))
                .andExpect(jsonPath("$.studentInfo.postalCode").value("4565"))
                .andExpect(jsonPath("$.studentInfo.street").value("testStreet"))
                .andReturn();

        jsonUser = result.getResponse().getContentAsString();
        User getUser = mapper.readValue(jsonUser, User.class);

        //try adding a user with an already existing email
        rest.perform(post("/user/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonUser).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isBadRequest());

        rest.perform(delete("/user/id/"+getUser.getId())).andExpect(status().isForbidden());

        rest.perform(delete("/user/id/"+getUser.getId()).header("X-Auth", "cGlldGVyLmdvb3NzZW5zQHRpaGguYmU6cGFzc3dvcmQ="))
                .andExpect(status().isOk());

    }
}