package org.burgers.camel.ride.app.transformation;

import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CsvToXmlTransformer {
    @Autowired
    private XStream xStream;

    public List<String> convert(List<List<String>> csvRows){
        List<String> resultingXml = new ArrayList<String>();
        for (List<String> csvRow : csvRows) {
            Person person = new Person();
            person.setFirstName(csvRow.get(0));
            person.setLastName(csvRow.get(1));
            resultingXml.add(xStream.toXML(person));
        }
        return resultingXml;
    }

    public void setXstream(XStream xstream) {
        this.xStream = xstream;
    }
}
