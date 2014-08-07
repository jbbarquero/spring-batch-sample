package com.malsolo.springframework.batch.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

/**
 * Created by jbeneito on 6/08/14.
 */
@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(HSQL)
                .addScript("schema-all.sql")
                .addScript("org/springframework/batch/core/schema-hsqldb.sql")
                .build();
    }

}
