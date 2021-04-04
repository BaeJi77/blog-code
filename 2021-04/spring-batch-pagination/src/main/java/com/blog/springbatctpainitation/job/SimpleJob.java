package com.blog.springbatctpainitation.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SimpleJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final int PAGE_SIZE = 3;
    private static final int CHUCK_SIZE = 3;

    public SimpleJob(JobBuilderFactory jobBuilderFactory,
                     StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job paginationJob() {
        return jobBuilderFactory.get("paginationJob")
                                .start(paginationStep())
                                .build();
    }

    @Bean
    public Step paginationStep() {
        return stepBuilderFactory.get("paginationStep")
                .<People, People>chunk(CHUCK_SIZE)
                .reader(new PaginationReader(PAGE_SIZE))
                .writer(printWrite())
                .build();
    }

    @Bean
    public ItemWriter<People> printWrite() {
        return list -> {
            log.info("writer={}", list);
        };
    }
}
