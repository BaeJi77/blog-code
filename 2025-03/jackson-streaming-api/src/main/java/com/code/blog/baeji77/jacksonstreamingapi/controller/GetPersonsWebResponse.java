package com.code.blog.baeji77.jacksonstreamingapi.controller;

import com.code.blog.baeji77.jacksonstreamingapi.model.Person;

import java.util.List;

public record GetPersonsWebResponse(
        List<Person> persons
) {
}
