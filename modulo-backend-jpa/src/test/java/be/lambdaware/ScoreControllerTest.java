package be.lambdaware;

import be.lambdaware.enums.ScoreType;
import be.lambdaware.models.CourseTopic;
import be.lambdaware.models.Objective;
import be.lambdaware.models.PAVScore;
import be.lambdaware.models.StudentInfo;
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

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Vincent on 23/05/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ModuloBackendApplication.class)
@WebAppConfiguration
public class ScoreControllerTest {

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
    public void AddPAVScore() throws Exception {
        PAVScore score = new PAVScore();
        score.setScore(ScoreType.A);
        score.setWeek(30);
        score.setRemarks("Moet beter opletten");

        StudentInfo s = new StudentInfo();
        s.setId(10);
        score.setStudentInfo(s);

        Objective o = new Objective();
        o.setId(35);
        score.setObjective(o);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonScore = ow.writeValueAsString(score);

        rest.perform(post("/score/id/10/pav/7/35").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonScore)).andExpect(status().isForbidden());

        MvcResult result = rest.perform(post("/score/id/10/pav/7/35").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonScore).header("X-Auth", "c3RldmVuLmphY29ic0B0aWhoLmJlOnBhc3N3b3Jk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value("A"))
                .andExpect(jsonPath("$.week").value(30))
                .andExpect(jsonPath("$.remarks").value("Moet beter opletten"))
                .andExpect(jsonPath("$.objective.id").value(35))
                .andExpect(jsonPath("$.studentInfo.id").value(2))
                .andExpect(jsonPath("$.courseTopic.id").value(7))
                .andReturn();
        jsonScore = result.getResponse().getContentAsString();
    }
}
