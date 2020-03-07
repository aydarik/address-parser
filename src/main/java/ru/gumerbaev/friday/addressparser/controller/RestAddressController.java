package ru.gumerbaev.friday.addressparser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gumerbaev.friday.addressparser.model.Address;
import ru.gumerbaev.friday.addressparser.parser.AddressParser;

@RestController
@RequestMapping(value = "api/v1", produces = "application/json")
public class RestAddressController {

    private final AddressParser addressParser;

    public RestAddressController(AddressParser addressParser) {
        this.addressParser = addressParser;
    }

    @GetMapping
    public Address parseAddress(@RequestParam("address") String addressString) {
        return addressParser.parse(addressString);
    }
}
