package com.code.blog.baeji77.jacksonstreamingapi.model;

public record Person(
        Long id,
        String name,
        int age,
        Address address
) {
    public record Address(
            String street,
            String city,
            String state,
            String zip
    ) {
    }
}
