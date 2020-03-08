package ru.gumerbaev.friday.addressparser.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gumerbaev.friday.addressparser.parser.AddressParser;

@Service
@Profile("osm")
public class NominatimService {

    @Value("${thirdparty.nominatim.url}")
    private String url;

    private Logger log = LoggerFactory.getLogger(AddressParser.class);

    private RestTemplate restTemplate = new RestTemplate();

    public String geocode(String stringAddress) {
        log.info("Sending request to Nominatim API");
        var response = restTemplate.getForEntity(String.format(url, stringAddress), String.class).getBody();
        if (response == null) {
            log.warn("No answer returned from Nominatim");
            return null;
        }

        log.info("Nominatim response received");
        log.trace(response);
        return response;
    }
}
