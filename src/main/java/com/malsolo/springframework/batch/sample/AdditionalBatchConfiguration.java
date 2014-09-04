package com.malsolo.springframework.batch.sample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobFactory;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jbeneito on 7/08/14.
 */
@Configuration
public class AdditionalBatchConfiguration {

    @Autowired
    JobRepository jobRepository;
    @Autowired
    JobRegistry jobRegistry;
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    JobExplorer jobExplorer;
//    @Autowired
//    Job importUserJob;

    @Bean
    public JobOperator jobOperator() {
        SimpleJobOperator jobOperator = new SimpleJobOperator();
        jobOperator.setJobExplorer(jobExplorer);
        jobOperator.setJobLauncher(jobLauncher);
        jobOperator.setJobRegistry(jobRegistry);
        jobOperator.setJobRepository(jobRepository);
        return jobOperator;
    }

//    Too manual, I don't like this approach
//    @Bean
//    public JobFactory jobFactory() {
//        return new ReferenceJobFactory(importUserJob);
//    }

//    This bean post processors doesn't operate on the job bean because is not defined here, so it's moved to the BatchConfiguration java file
//    @Bean
//    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
//        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
//        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
//        return jobRegistryBeanPostProcessor;
//    }

//    Not enough knowledge from the time being: the application context factory tries to create the application factory resulting in two possible errors:
//    · With new GenericApplicationContextFactory(ApplicationConfiguration.class.getPackage().getName()); Error creating bean with name 'additionalBatchConfiguration': Failed to execute SQL script statement at line 1 of resource class path resource [org/springframework/batch/core/schema-hsqldb.sql]: CREATE TABLE BATCH_JOB_INSTANCE ( JOB_INSTANCE_ID BIGINT IDENTITY NOT NULL PRIMARY KEY , VERSION BIGINT , JOB_NAME VARCHAR(100) NOT NULL, JOB_KEY VARCHAR(32) NOT NULL, constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY) ) ; nested exception is java.sql.SQLSyntaxErrorException: object name already exists: BATCH_JOB_INSTANCE in statement [CREATE TABLE BATCH_JOB_INSTANCE...
//    · With new GenericApplicationContextFactory(BatchConfiguration.class); Error creating bean with name 'importUserJob': No qualifying bean of type [javax.sql.DataSource] found for dependency: expected at least 1 bean
//    @Bean
//    public AutomaticJobRegistrar automaticJobRegistrar() {
//        AutomaticJobRegistrar automaticJobRegistrar = new AutomaticJobRegistrar();
//        GenericApplicationContextFactory genericApplicationContextFactory = new GenericApplicationContextFactory(BatchConfiguration.class);
//        ApplicationContextFactory[] applicationContextFactories = {genericApplicationContextFactory};
//        automaticJobRegistrar.setApplicationContextFactories(applicationContextFactories);
//        DefaultJobLoader defaultJobLoader = new DefaultJobLoader(jobRegistry);
//        automaticJobRegistrar.setJobLoader(defaultJobLoader);
//        return automaticJobRegistrar;
//    }

}
