package com.malsolo.springframework.batch.sample.jsr352components.reader;

import com.malsolo.springframework.batch.sample.Person;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemReader;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jbeneito on 7/08/14.
 */
public class FileItemReader implements ItemReader {
    @Inject
    @BatchProperty
    private String fileName;

    private BufferedReader reader;

    private long count;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        assert fileName != null && fileName.length() > 0;

        ;
        reader = new BufferedReader(new FileReader(new File(this.getClass().getClassLoader().getResource(fileName).toURI())));

        if(checkpoint != null) {
            Long oldCount = (Long) ((Map) checkpoint).get("recordCount");

            if(oldCount != null) {
                long curCount = oldCount.longValue();

                for(long i = 0; i < curCount; i++) {
                    reader.readLine();
                }

                count = curCount;
            }
        }
    }

    @Override
    public void close() throws Exception {
        if(reader != null) {
            reader.close();
        }
    }

    @Override
    public Object readItem() throws Exception {
        String line = reader.readLine();
        System.out.println("read line: " + line);

        if(line != null) {
            String[] fields = line.split(",");

            Person person = new Person();
            person.setFirstName(fields[0]);
            person.setLastName(fields[1]);

            return person;
        } else {
            return null;
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        HashMap<String, Object> checkpoint = new HashMap<String, Object>();
        checkpoint.put("recordCount", count);

        return checkpoint;
    }
}
