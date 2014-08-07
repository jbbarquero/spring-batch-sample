package com.malsolo.springframework.batch.sample;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.batch.runtime.Metric;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by jbeneito on 6/08/14.
 */
public class MainHelper {

    public static void reportResults(JobExecution jobExecution) {
        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();

        System.out.println("***********************************************************");
        System.out.println(String.format("%s finished with a status of %s (%s).", jobExecution.getJobInstance().getJobName(), jobExecution.getExitStatus().getExitDescription(), jobExecution.getExitStatus().getExitCode()) );
        System.out.println("* Steps executed:");
        for (StepExecution stepExecution : stepExecutions) {
            System.out.println(String.format("\t%s : %s" , stepExecution.getStepName(), stepExecution.getExitStatus()));

            System.out.println(stepExecution.getSummary());

        }
        System.out.println("***********************************************************");

    }

    public static void reportResults(JobOperator jobOperator, long executionId) throws NoSuchJobExecutionException {
        StringBuilder sb = new StringBuilder();//To avoid logs from DB access of JobOperator
        sb.append("\n***********************************************************\n");
        sb.append(jobOperator.getSummary(executionId));
        sb.append("\n* Steps executed:");
        Map<Long, String> stepExecutionSummaries = jobOperator.getStepExecutionSummaries(executionId);
        for (String stepExecutionSummary : stepExecutionSummaries.values()) {
            sb.append("\n").append(stepExecutionSummary);
        }
        sb.append("\n***********************************************************\n");
        System.out.print(sb.toString());


    }

    public static void reportResults(javax.batch.operations.JobOperator operator, long executionId) {
        javax.batch.runtime.JobExecution jobExecution = operator.getJobExecution(executionId);
        List<javax.batch.runtime.StepExecution> stepExecutions = operator.getStepExecutions(jobExecution.getExecutionId());

        System.out.println("***********************************************************");
        System.out.println(String.format("%s finished with a status of %s.", jobExecution.getJobName(), jobExecution.getBatchStatus()) );
        System.out.println("Steps executed:");
        for (javax.batch.runtime.StepExecution stepExecution : stepExecutions) {
            System.out.println(String.format("\t%s : %s" , stepExecution.getStepName(), stepExecution.getBatchStatus()));

            for (Metric metric : stepExecution.getMetrics()) {
                System.out.println(String.format("\t\t%s : %s", metric.getType(), metric.getValue()));
            }
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
