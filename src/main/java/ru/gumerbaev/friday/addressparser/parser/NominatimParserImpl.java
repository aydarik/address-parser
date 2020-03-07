package ru.gumerbaev.friday.addressparser.parser;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.gumerbaev.friday.addressparser.integration.NominatimService;
import ru.gumerbaev.friday.addressparser.model.Address;

@Service
@Profile("osm")
public class NominatimParserImpl implements AddressParser {

    private NominatimService nominatimService;

    public NominatimParserImpl(NominatimService nominatimService) {
        log.info("NominatimParser will be used");
        this.nominatimService = nominatimService;
    }

    @Override
    public Address parse(String addressString) {
        return nominatimService.geocode(addressString);
    }
}
