package gob.minsa.cedtic.integrations;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EquipoIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void shouldReturnHello() throws Exception {
        mvc.perform(get("/api/equipo").with(jwt()))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello")));
    }
}
