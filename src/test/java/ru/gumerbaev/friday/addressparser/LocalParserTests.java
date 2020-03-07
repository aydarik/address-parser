package ru.gumerbaev.friday.addressparser;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@AutoConfigureMockMvc
class LocalParserTests {

    @Autowired
    MockMvc mvc;

    @Test
    void mostSimpleCases() throws Exception {
        String[][] testCases = {
                {"Winterallee 3", "Winterallee", "3"},
                {"Musterstrasse 45", "Musterstrasse", "45"},
                {"Blaufeldweg 123B", "Blaufeldweg", "123B"}
        };
        performRequestAndTest(testCases);
    }

    @Test
    void moreComplicatedCases() throws Exception {
        String[][] testCases = {
                {"Am Bächle 23", "Am Bächle", "23"},
                {"Auf der Vogelwiese 23 b", "Auf der Vogelwiese", "23 b"}
        };
        performRequestAndTest(testCases);
    }

    @Test
    void complexCases() throws Exception {
        String[][] testCases = {
                {"4, rue de la revolution", "rue de la revolution", "4"},
                {"200 Broadway Av", "Broadway Av", "200"},
                {"Calle Aduana, 29", "Calle Aduana", "29"},
                {"Calle 39 No 1540", "Calle 39", "No 1540"}
        };
        performRequestAndTest(testCases);
    }

    private void performRequestAndTest(String[][] testCases) throws Exception {
        for (var testCase : testCases)
            mvc.perform(get("/api/v1")
                    .param("address", testCase[0])
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.street").value(testCase[1]))
                    .andExpect(jsonPath("$.housenumber").value(testCase[2]));
    }
}
