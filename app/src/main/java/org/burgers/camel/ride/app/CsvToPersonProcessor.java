package org.burgers.camel.ride.app;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CsvToPersonProcessor {
    private List<Person> people = new ArrayList<Person>();
    private int timesInvoked = 0;

    public void process(List<List<String>> csvRows) {
        for (List<String> csvRow : csvRows) {
            Person person = new Person();
            person.setFirstName(csvRow.get(0));
            person.setLastName(csvRow.get(1));
            people.add(person);
        }
        timesInvoked++;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public int getTimesInvoked() {
        return timesInvoked;
    }
}
