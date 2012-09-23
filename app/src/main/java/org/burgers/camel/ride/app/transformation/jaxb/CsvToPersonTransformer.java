package org.burgers.camel.ride.app.transformation.jaxb;

import org.burgers.camel.ride.app.transformation.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CsvToPersonTransformer {
    public List<Person> convert(List<List<String>> csvRows){
        List<Person> results = new ArrayList<Person>();
        for (List<String> csvRow : csvRows) {
            Person person = new Person();
            person.setFirstName(csvRow.get(0));
            person.setLastName(csvRow.get(1));
            results.add(person);
        }
        return results;
    }
}
