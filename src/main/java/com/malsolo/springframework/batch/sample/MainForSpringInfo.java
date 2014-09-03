package com.malsolo.springframework.batch.sample;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by jbeneito on 1/09/14.
 */
@Configuration
@EnableBatchProcessing
@Import(DataSourceConfiguration.class)
public class MainForSpringInfo {

    public static void main(String... args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainForSpringInfo.class);
        info(context);
        context.close();
    }

    private static void info(ApplicationContext context) {
        String[] beanNames = context.getBeanDefinitionNames();
        System.out.println("***** Loaded beans *********");
        for (String beanName : beanNames) {
            Object beanInstance = context.getBean(beanName);
            System.out.printf("Bean name: %s. Class: %s\n", beanName, beanInstance.getClass().getName());
        }
        System.out.println("***** END Loaded beans *********");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
