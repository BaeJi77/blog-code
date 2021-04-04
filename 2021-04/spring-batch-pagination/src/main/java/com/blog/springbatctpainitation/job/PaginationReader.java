package com.blog.springbatctpainitation.job;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaginationReader extends AbstractPagingItemReader<People> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public PaginationReader(int pageSize) {
        setPageSize(pageSize);
    }

    @Override
    protected void doReadPage() {
        log.info("Read page. page number={}", getPage());
        if (results == null) {
            results = new ArrayList<>();
        } else {
            results.clear();
        }

        if (getPage() == 2) {
            return;
        }

        try {
            ClassPathResource classPathResource = new ClassPathResource(String.format("test_%d.json", getPage()));
            BufferedInputStream inputStream = new BufferedInputStream(classPathResource.getInputStream());

            List<People> peopleList = MAPPER.readValue(inputStream, new TypeReference<>() {});
            results.addAll(peopleList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doJumpToPage(int itemIndex) {

    }
}
