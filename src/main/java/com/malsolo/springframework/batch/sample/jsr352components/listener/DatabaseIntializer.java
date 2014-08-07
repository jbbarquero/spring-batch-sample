package com.malsolo.springframework.batch.sample.jsr352components.listener;

import org.springframework.beans.factory.annotation.Autowired;

import javax.batch.api.BatchProperty;
import javax.batch.api.listener.AbstractJobListener;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author mminella
 */
public class DatabaseIntializer extends AbstractJobListener {

    @Autowired
    private DataSource dataSource;

    @Inject
    @BatchProperty
    private String scriptPath;

    @Override
    public void beforeJob() throws Exception {

        InputStream resourceAsStream = this.getClass().getResourceAsStream(scriptPath);

        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));

        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();

        String sql = reader.readLine();

        while(sql != null) {
            if (!"".equals(sql))
                stmt.execute(sql);
            sql = reader.readLine();
        }

        stmt.close();
        con.close();
    }
}
