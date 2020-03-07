package ru.gumerbaev.friday.addressparser.parser;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.gumerbaev.friday.addressparser.model.Address;

@Service
@Profile("google")
public class GoogleParserImpl implements AddressParser {

    public GoogleParserImpl() {
        log.info("GoogleParser will be used");
    }

    @Override
    public Address parse(String addressString) {
        return Address.build(null, null); // TODO: implement
    }
}
