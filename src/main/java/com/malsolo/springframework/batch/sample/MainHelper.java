package com.malsolo.springframework.batch.sample;

import org.springframework.batch.core.StepExecution;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.batch.runtime.Metric;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by jbeneito on 6/08/14.
 */
public class MainHelper {

    public static void reportResults(org.springframework.batch.core.JobExecution jobExecution) {
        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();

        System.out.println("***********************************************************");
        System.out.println(String.format("%s finished with a status of %s (%s).", jobExecution.getJobInstance().getJobName(), jobExecution.getExitStatus().getExitDescription(), jobExecution.getExitStatus().getExitCode()) );
        System.out.println("Steps executed:");
        for (StepExecution stepExecution : stepExecutions) {
            System.out.println(String.format("\t%s : %s" , stepExecution.getStepName(), stepExecution.getExitStatus()));

            System.out.println(stepExecution.getSummary());

        }
        System.out.println("***********************************************************");

    }


    public static void reportPeople(JdbcTemplate jdbcTemplate) {

        List<Person> people = jdbcTemplate.query(
                "SELECT first_name, last_name FROM people",
                (rs, rowNum) -> new Person(rs.getString(1), rs.getString(2))
        );

        System.out.println("***********************************************************");
        System.out.println("* People found:");
        for (Person person : people) {
            System.out.println(String.format("\n* Found %s in the database" , person));
        }
        System.out.println("***********************************************************");

    }
}
