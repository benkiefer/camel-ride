package org.burgers.camel.ride.app.transformation;

import com.thoughtworks.xstream.XStream;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class CsvToXmlTransformerTest {
    private CsvToXmlTransformer transformer;
    XStream xstream;

    @Before
    public void setup(){
        transformer = new CsvToXmlTransformer();
        xstream = new XStream();
        transformer.setXstream(xstream);
    }

    @Test
    public void convert(){
        List<String> csvRow = Arrays.asList("john", "smith");
        List<String> result = transformer.convert(Arrays.asList(csvRow));

        assertEquals(result.size(), 1);

        Person person = (Person) xstream.fromXML(result.get(0));

        assertEquals(person.getLastName(), "smith");
        assertEquals(person.getFirstName(), "john");
    }


}
