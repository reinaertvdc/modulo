package be.lambdaware;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ModuloBackendApplication.class)
@WebAppConfiguration
public class UserControllerTest {

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
                .andExpect(status().isOk());
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
}
