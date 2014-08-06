package com.malsolo.springframework.batch.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by jbeneito on 6/08/14.
 */
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ApplicationConfiguration.class)})
@EnableAutoConfiguration
public class MainBoot {

    public static void main(String... args) {

        ApplicationContext context = SpringApplication.run(MainBoot.class);

        MainHelper.reportPeople(context.getBean(JdbcTemplate.class));

    }
}
