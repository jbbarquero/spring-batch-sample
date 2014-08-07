package com.malsolo.springframework.batch.sample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobFactory;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


/**
 * Created by jbeneito on 6/08/14.
 */
@Component
public class MainJobOperator {

    @Autowired
    JobFactory jobFactory;
    @Autowired
    JobRegistry jobRegistry;
    @Autowired
    JobOperator jobOperator;

    @Autowired
    Job importUserJob;

    public static void main(String... args) throws JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException, DuplicateJobException, NoSuchJobExecutionException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ApplicationConfiguration.class, DataSourceConfiguration.class, BatchConfiguration.class);
        context.refresh();

        MainJobOperator main = context.getBean(MainJobOperator.class);
        main.jobRegistry.register(main.jobFactory);
        long executionId = main.jobOperator.start(main.importUserJob.getName(), null);

        MainHelper.reportResults(main.jobOperator, executionId);
        MainHelper.reportPeople(context.getBean(JdbcTemplate.class));
        System.out.printf("\nFIN %s", main.getClass().getName());

    }
}
