package org.burgers.camel.ride.app;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ContentBasedFileMover extends SpringRouteBuilder {
    private @Value("${camel.ride.app.content.based.xml.destination}") String xmlDestination;
    private @Value("${camel.ride.app.content.based.csv.destination}") String csvDestination;
    private @Value("${camel.ride.app.content.based.source}") String source;

    @Override
    public void configure() throws Exception {
        from(source).
                choice()
                .when(header("CamelFileName").endsWith("csv")).to(csvDestination)
                .when(new XmlPredicate()).to(xmlDestination);
    }

    class XmlPredicate implements Predicate  {
        @Override
        public boolean matches(Exchange exchange) {
            String fileName = (String) exchange.getIn().getHeader("CamelFileName");
            return fileName.endsWith(".xml");
        }
    }


}