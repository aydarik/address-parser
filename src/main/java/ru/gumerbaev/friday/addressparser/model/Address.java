package ru.gumerbaev.friday.addressparser.model;

public class Address {

    private String street;
    private String housenumber;

    public static Address build(String street, String housenumber) {
        return new Address(street, housenumber);
    }

    public Address(String street, String housenumber) {
        if (street != null) {
            this.street = street.strip();
        }
        if (housenumber != null) {
            this.housenumber = housenumber.strip();
        }
    }

    public String getStreet() {
        return street;
    }

    public String getHousenumber() {
        return housenumber;
    }
}
