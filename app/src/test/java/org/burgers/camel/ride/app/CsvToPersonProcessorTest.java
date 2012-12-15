package org.burgers.camel.ride.app;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class CsvToPersonProcessorTest {
    private CsvToPersonProcessor processor;

    @Before
    public void setup(){
        processor = new CsvToPersonProcessor();
        processor.getPeople().clear();
    }

    @Test
    public void convert(){
        List<String> row1 = asList("first", "last");
        List<List<String>> csvRows = asList(row1);

        processor.process(csvRows);

        List<Person> people = processor.getPeople();
        assertEquals(1, people.size());
        assertEquals("first", people.get(0).getFirstName());
        assertEquals("last", people.get(0).getLastName());
        assertEquals(1, processor.getTimesInvoked());
    }

}
