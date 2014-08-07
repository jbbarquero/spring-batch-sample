package com.malsolo.springframework.batch.sample.jsr352components.processor;

import com.malsolo.springframework.batch.sample.Person;
import com.malsolo.springframework.batch.sample.PersonItemProcessor;

import javax.batch.api.chunk.ItemProcessor;

/**
 * Created by jbeneito on 7/08/14.
 */
public class CapsItemProcessor implements ItemProcessor {

    @Override
    public Object processItem(Object item) throws Exception {
        return new PersonItemProcessor().process((Person) item);
    }
}
