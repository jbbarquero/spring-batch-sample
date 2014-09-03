package com.malsolo.springframework.batch.sample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jbeneito on 6/08/14.
 */
@Component
public class MainJobLauncher {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job importUserJob;

    public static void main(String... args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        MainJobLauncher main = context.getBean(MainJobLauncher.class);

        JobExecution jobExecution = main.jobLauncher.run(main.importUserJob, new JobParameters());

        MainHelper.reportResults(jobExecution);
        MainHelper.reportPeople(context.getBean(JdbcTemplate.class));

        context.close();

    }

}
