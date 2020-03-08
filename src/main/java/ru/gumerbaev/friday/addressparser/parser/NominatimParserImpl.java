package ru.gumerbaev.friday.addressparser.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.gumerbaev.friday.addressparser.integration.NominatimService;
import ru.gumerbaev.friday.addressparser.model.Address;

@Service
@Profile("osm")
public class NominatimParserImpl implements AddressParser {

    private NominatimService nominatimService;
    private ObjectMapper mapper = new ObjectMapper();

    public NominatimParserImpl(NominatimService nominatimService) {
        log.info("NominatimParser will be used");
        this.nominatimService = nominatimService;
    }

    @Override
    public Address parse(String addressString) {
        var nominatimResponse = nominatimService.geocode(addressString);

        JsonNode finestResult;
        try {
            finestResult = mapper.readTree(nominatimResponse).get(0);
        } catch (JsonProcessingException e) {
            log.error("Can not process Nominatim response", e);
            return null;
        }
        if (finestResult == null) {
            log.warn("Can not be found in Nominatim");
            return null;
        }

        var addressNode = finestResult.path("address");
        var street = addressNode.path("road").asText();
        var housenumber = addressNode.path("house_number").asText();
        return Address.build(street, housenumber);
    }
}
