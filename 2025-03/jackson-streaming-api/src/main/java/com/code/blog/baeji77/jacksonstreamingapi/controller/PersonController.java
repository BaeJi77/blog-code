package com.code.blog.baeji77.jacksonstreamingapi.controller;


import com.code.blog.baeji77.jacksonstreamingapi.service.GetPersonResponse;
import com.code.blog.baeji77.jacksonstreamingapi.service.PersonService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons/object/{filename}")
    public GetPersonsWebResponse getPersonsObjectSmall(@PathVariable("filename") String filename) {
        GetPersonResponse response = personService.getObjectFromJsonFile(filename);

        return new GetPersonsWebResponse(response.persons());
    }

    @GetMapping("/persons/streaming/{filename}")
    public void getPersonsStreamSmall(
            @PathVariable("filename") String filename,
            HttpServletResponse response
    ) {
        try (InputStream input = personService.getInputStreamJsonFile(filename)) {
            response.setContentType("application/json");
            JsonFactory jsonFactory = new JsonFactory();

            try (JsonParser jsonParser = jsonFactory.createParser(input);
                 JsonGenerator jsonGenerator = jsonFactory.createGenerator(response.getOutputStream())) {

                // 응답 객체 시작
                jsonGenerator.writeStartObject();
                jsonGenerator.writeFieldName("persons");
                jsonGenerator.writeStartArray();

                // 파서로 입력 JSON 파일 파싱
                boolean inPersonsArray = false;

                while (jsonParser.nextToken() != null) {
                    if (jsonParser.getCurrentToken() == JsonToken.FIELD_NAME && "persons".equals(jsonParser.getCurrentName())) {
                        // persons 필드 발견
                        jsonParser.nextToken(); // START_ARRAY로 이동
                        inPersonsArray = true;
                    } else if (inPersonsArray && jsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
                        // persons 배열 내부의 객체 시작 - 각 person 객체
                    jsonGenerator.writeStartObject();

                    // person 객체 내의 각 필드 파싱
                    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                        if (jsonParser.getCurrentToken() == JsonToken.FIELD_NAME) {
                            String fieldName = jsonParser.getCurrentName();
                            jsonGenerator.writeFieldName(fieldName);

                            jsonParser.nextToken();
                            if ("address".equals(fieldName) && jsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
                                jsonGenerator.writeStartObject();
                                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                                    if (jsonParser.getCurrentToken() == JsonToken.FIELD_NAME) {
                                        String addressField = jsonParser.getCurrentName();
                                        jsonGenerator.writeFieldName(addressField);

                                        jsonParser.nextToken();
                                        jsonGenerator.copyCurrentEvent(jsonParser);
                                    }
                                }
                                jsonGenerator.writeEndObject();
                            } else {
                                jsonGenerator.copyCurrentEvent(jsonParser);
                            }
                        }
                    }

                    jsonGenerator.writeEndObject();
                    } else if (inPersonsArray && jsonParser.getCurrentToken() == JsonToken.END_ARRAY) {
                        // persons 배열의 끝
                        inPersonsArray = false;
                        break;
                    }
                }

                // 응답 배열과 객체 닫기
                jsonGenerator.writeEndArray();
                jsonGenerator.writeEndObject();
                jsonGenerator.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
