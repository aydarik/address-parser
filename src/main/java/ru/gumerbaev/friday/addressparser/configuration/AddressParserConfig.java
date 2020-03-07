package ru.gumerbaev.friday.addressparser.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:addressparser.properties")
public class AddressParserConfig {
}
