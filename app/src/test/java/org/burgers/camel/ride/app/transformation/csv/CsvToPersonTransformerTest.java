package org.burgers.camel.ride.app.transformation.csv;

import org.burgers.camel.ride.app.transformation.Person;
import org.burgers.camel.ride.app.transformation.csv.CsvToPersonTransformer;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class CsvToPersonTransformerTest {
    private CsvToPersonTransformer transformer;

    @Before
    public void setup(){
        transformer = new CsvToPersonTransformer();
    }

    @Test
    public void convert(){
        List<String> csvRow = Arrays.asList("john", "smith");
        List<Person> result = transformer.convert(Arrays.asList(csvRow));

        assertEquals(result.size(), 1);

        Person person = result.get(0);

        assertEquals(person.getLastName(), "smith");
        assertEquals(person.getFirstName(), "john");
    }


}
