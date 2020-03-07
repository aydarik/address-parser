package ru.gumerbaev.friday.addressparser;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.gumerbaev.friday.addressparser.integration.NominatimService;
import ru.gumerbaev.friday.addressparser.model.Address;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("osm")
@AutoConfigureMockMvc
class NominatimParserTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    private NominatimService nominatimService;

    @Test
    void mockCase() throws Exception {
        String[] testCase = {"4, rue de la revolution", "rue de la revolution", "4"};
        given(nominatimService.geocode(testCase[0])).willReturn(new Address(testCase[1], testCase[2]));

        mvc.perform(get("/api/v1")
                .param("address", testCase[0])
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.street").value(testCase[1]))
                .andExpect(jsonPath("$.housenumber").value(testCase[2]));
    }
}
