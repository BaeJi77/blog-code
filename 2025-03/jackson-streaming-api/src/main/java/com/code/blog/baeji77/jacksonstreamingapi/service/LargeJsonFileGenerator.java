package com.code.blog.baeji77.jacksonstreamingapi.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

public class LargeJsonFileGenerator {

    /**
     * // json format
     {
        "persons": [
            {
            "id": 1,
            "name": "John",
            "age": 22,
            "address": {
                "street": "123 Main St",
                "city": "New York",
                "state": "NY",
                "zip": "10001"
            }, {
            "id": 2,
            "name": "Jane",
            "age": 23,
            "address": {
                "street": "456 Main St",
                "city": "New York",
                "state": "NY",
                "zip": "10001"
            }, {...}
        ]
     }
     */
    public static void generate(String filePath, Long objectCount) throws IOException {
        JsonFactory factory = new JsonFactory();
        try (JsonGenerator generator = factory.createGenerator(new FileOutputStream(filePath))) {
            generator.writeStartObject();
            generator.writeArrayFieldStart("persons");
            for (int i = 0; i < objectCount; i++) {
                generator.writeStartObject();
                generator.writeNumberField("id", i);
                generator.writeStringField("name", "name" + i);
                generator.writeNumberField("age", 20 + i);
                generator.writeFieldName("address");
                generator.writeStartObject();
                generator.writeStringField("street", "street" + i);
                generator.writeStringField("city", "city" + i);
                generator.writeStringField("state", "state" + i);
                generator.writeStringField("zip", "zip" + i);
                generator.writeEndObject();
                generator.writeEndObject();
            }
            generator.writeEndArray();
            generator.writeEndObject();
        }
    }
}
