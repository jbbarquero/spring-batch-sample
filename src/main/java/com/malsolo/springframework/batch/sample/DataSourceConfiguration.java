package com.malsolo.springframework.batch.sample;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

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
