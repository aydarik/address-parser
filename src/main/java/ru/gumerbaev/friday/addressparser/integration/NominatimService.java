package ru.gumerbaev.friday.addressparser.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gumerbaev.friday.addressparser.model.Address;
import ru.gumerbaev.friday.addressparser.parser.AddressParser;

@Service
@Profile("osm")
public class NominatimService {

    @Value("${thirdparty.nominatim.url}")
    private String url;

    private Logger log = LoggerFactory.getLogger(AddressParser.class);

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper mapper = new ObjectMapper();

    public Address geocode(String stringAddress) {
        log.info("Sending request to Nominatim API");
        var response = restTemplate.getForEntity(String.format(url, stringAddress), String.class).getBody();
        if (response == null) {
            log.warn("No answer returned from Nominatim");
            return null;
        }

        log.info("Nominatim response received");
        log.trace(response);

        JsonNode finestResult;
        try {
            finestResult = mapper.readTree(response).get(0);
        } catch (JsonProcessingException e) {
            log.error("Can not process Nominatim answer", e);
            return null;
        }
        if (finestResult == null) {
            log.warn("Can not be found in Nominatim");
            return null;
        }

        var addressNode = finestResult.path("address");
        var street = addressNode.path("road").asText();
        var housenumber = addressNode.path("road").asText();
        return Address.build(street, housenumber);
    }
}
