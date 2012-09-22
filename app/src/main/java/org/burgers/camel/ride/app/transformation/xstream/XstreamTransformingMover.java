package org.burgers.camel.ride.app.transformation.xstream;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class XstreamTransformingMover extends SpringRouteBuilder {
    private @Value("${camel.ride.app.transform.destination}") String destination;
    private @Value("${camel.ride.app.transform.source}") String source;

//    Take a csv file, and split each row into a separate xml file.
    @Override
    public void configure() throws Exception {
        from(source).unmarshal().csv().split().method("csvToXmlTransformer", "convert").streaming().to(destination + "?fileName=${bean:uniqueFileNameGenerator?method=generate}.xml");
    }
}
