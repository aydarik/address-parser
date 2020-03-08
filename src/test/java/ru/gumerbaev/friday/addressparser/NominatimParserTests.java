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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        String[] testCase = {"4, rue de la revolution", "Rue de la Révolution", "4"};
        given(nominatimService.geocode(testCase[0])).willReturn("[{\"place_id\":39587900,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"node\",\"osm_id\":2980660747,\"boundingbox\":[\"48.8564067\",\"48.8565067\",\"2.429004\",\"2.429104\"],\"lat\":\"48.8564567\",\"lon\":\"2.429054\",\"display_name\":\"4, Rue de la Révolution, Étienne-Marcel - Chanzy, Монтрёй, Bobigny, Сена-Сен-Дени, Иль-де-Франс, Метрополия Франции, 93100, Франция\",\"class\":\"place\",\"type\":\"house\",\"importance\":0.411,\"address\":{\"house_number\":\"4\",\"road\":\"Rue de la Révolution\",\"suburb\":\"Étienne-Marcel - Chanzy\",\"city\":\"Монтрёй\",\"county\":\"Bobigny\",\"state\":\"Иль-де-Франс\",\"country\":\"Франция\",\"postcode\":\"93100\",\"country_code\":\"fr\"}},{\"place_id\":27220782,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"node\",\"osm_id\":2639429585,\"boundingbox\":[\"48.8128497\",\"48.8129497\",\"2.3958493\",\"2.3959493\"],\"lat\":\"48.8128997\",\"lon\":\"2.3958993\",\"display_name\":\"4, Rue de la Révolution, Ivry port, Иври-сюр-Сен, Л’Э-ле-Роз, Валь-де-Марн, Иль-де-Франс, Метрополия Франции, 94200, Франция\",\"class\":\"place\",\"type\":\"house\",\"importance\":0.411,\"address\":{\"house_number\":\"4\",\"road\":\"Rue de la Révolution\",\"suburb\":\"Ivry port\",\"town\":\"Иври-сюр-Сен\",\"county\":\"Л’Э-ле-Роз\",\"state\":\"Иль-де-Франс\",\"country\":\"Франция\",\"postcode\":\"94200\",\"country_code\":\"fr\"}},{\"place_id\":20283943,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"node\",\"osm_id\":2047962020,\"boundingbox\":[\"48.8563424\",\"48.8564424\",\"2.4289896\",\"2.4290896\"],\"lat\":\"48.8563924\",\"lon\":\"2.4290396\",\"display_name\":\"4 bis, Rue de la Révolution, Étienne-Marcel - Chanzy, Монтрёй, Bobigny, Сена-Сен-Дени, Иль-де-Франс, Метрополия Франции, 93100, Франция\",\"class\":\"place\",\"type\":\"house\",\"importance\":0.401,\"address\":{\"house_number\":\"4 bis\",\"road\":\"Rue de la Révolution\",\"suburb\":\"Étienne-Marcel - Chanzy\",\"city\":\"Монтрёй\",\"county\":\"Bobigny\",\"state\":\"Иль-де-Франс\",\"country\":\"Франция\",\"postcode\":\"93100\",\"country_code\":\"fr\"}},{\"place_id\":105702950,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"way\",\"osm_id\":80878785,\"boundingbox\":[\"54.731605\",\"54.7319514\",\"55.9824166\",\"55.9828001\"],\"lat\":\"54.7317782\",\"lon\":\"55.98260835\",\"display_name\":\"201/4, Революционная улица, Советский район, Уфа, городской округ Уфа, Башкортостан, Приволжский федеральный округ, 450000, Россия\",\"class\":\"building\",\"type\":\"yes\",\"importance\":0.101,\"address\":{\"house_number\":\"201/4\",\"road\":\"Революционная улица\",\"city_district\":\"Советский район\",\"city\":\"Уфа\",\"county\":\"городской округ Уфа\",\"state\":\"Башкортостан\",\"postcode\":\"450000\",\"country\":\"Россия\",\"country_code\":\"ru\"}}]");

        mvc.perform(get("/api/v1")
                .param("address", testCase[0])
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.street").value(testCase[1]))
                .andExpect(jsonPath("$.housenumber").value(testCase[2]));
    }
}
