package ru.gumerbaev.friday.addressparser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gumerbaev.friday.addressparser.model.Address;

public interface AddressParser {

    Logger log = LoggerFactory.getLogger(AddressParser.class);

    Address parse(String addressString);
}
