package com.code.blog.baeji77.jacksonstreamingapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class PersonService {

    public InputStream getInputStreamJsonFile(String filePath) {
        ClassPathResource resource = new ClassPathResource(filePath);
        try {
            return resource.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetPersonResponse getObjectFromJsonFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource(filePath);

        try (InputStream input = resource.getInputStream()) {
            return mapper.readValue(input, GetPersonResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
