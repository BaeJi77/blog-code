package com.code.blog.baeji77.jacksonstreamingapi;

import com.code.blog.baeji77.jacksonstreamingapi.service.LargeJsonFileGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JacksonStreamingApiApplication {

    public static void main(String[] args) {
//        generateDummyJsonFile();

        SpringApplication.run(JacksonStreamingApiApplication.class, args);
    }

    private static void generateDummyJsonFile() {
        String filename = "data-10.json";
        Long objectCount = 10L;
        try {
            LargeJsonFileGenerator.generate(filename, objectCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
