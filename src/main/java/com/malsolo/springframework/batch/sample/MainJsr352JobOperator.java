package com.malsolo.springframework.batch.sample;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import java.util.Properties;

/**
 * Created by jbeneito on 6/08/14.
 */
public class MainJsr352JobOperator {

    private static final JobOperator operator = BatchRuntime.getJobOperator();

    public static void main(String... args) throws InterruptedException {

        long executionId = operator.start("importUserJob", new Properties());

        while(!isItDoneYet(executionId)) {
            Thread.sleep(1000);
        }

        MainHelper.reportResults(operator, executionId);
    }

    private static boolean isItDoneYet(long executionId) {
        BatchStatus batchStatus = operator.getJobExecution(executionId).getBatchStatus();
        return batchStatus.compareTo(BatchStatus.STOPPED) > 0;
    }
}
