package ru.gumerbaev.friday.addressparser.parser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.gumerbaev.friday.addressparser.model.Address;

@Service
@Profile("local")
public class LocalParserImpl implements AddressParser {

    @Value("${pattern.housenumber}")
    private String housenumberPattern;

    public LocalParserImpl() {
        log.info("LocalParser will be used");
    }

    @Override
    public Address parse(String addressString) {
        log.info("Parsing address: {}", addressString);
        if (addressString == null || addressString.isBlank()) {
            log.info("Address is empty");
            return Address.build(null, null);
        }

        // most common case - all splitted by comma
        var commaSplit = addressString.split(",");
        // easiest one - take two last entries for street and house
        if (commaSplit.length > 1) {
            var street = commaSplit[commaSplit.length - 2];
            var housenumber = commaSplit[commaSplit.length - 1];
            if (isHousenumber(housenumber)) {
                log.info("Address is comma-separated string");
                return Address.build(street, housenumber);
            } else if (isHousenumber(street)) {
                log.info("Address is reversed comma-separated string");
                return Address.build(housenumber, street);
            }
        }
        // in all other cases we can just guess :`(
        return guessAddress(addressString);
    }

    private boolean isHousenumber(String housenumber) {
        return housenumber.strip().matches(housenumberPattern);
    }

    private Address guessAddress(String addressString) {
        log.info("Trying to guess correct address...");
        var address = tryMatchHousenumber(addressString, false);
        if (address == null) {
            log.info("Trying to guess on reversed address...");
            address = tryMatchHousenumber(addressString, true);
        }

        if (address != null) {
            log.info("Address found");
            return address;
        }

        // in case when can't find housenumber - return full address as a street
        log.info("No luck, returning default");
        return Address.build(addressString, null);
    }

    private Address tryMatchHousenumber(String addressString, boolean reversed) {
        var i = -1;
        while (i < addressString.length()) {
            i = addressString.indexOf(' ', i + 1);
            if (i < 0) break;

            var street = reversed ? addressString.substring(i + 1) : addressString.substring(0, i);
            var housenumber = reversed ? addressString.substring(0, i) : addressString.substring(i + 1);
            log.info("Trying with housenumber '{}'", housenumber);
            if (isHousenumber(housenumber)) {
                log.info("Suitable housenumber found");
                return Address.build(street, housenumber);
            }
        }

        return null;
    }
}
