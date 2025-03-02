package com.code.blog.baeji77.jacksonstreamingapi.service;

import com.code.blog.baeji77.jacksonstreamingapi.model.Person;

import java.util.List;

public record GetPersonResponse(
        List<Person> persons
) {
}
