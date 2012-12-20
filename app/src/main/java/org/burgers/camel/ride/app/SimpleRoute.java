package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleRoute extends SpringRouteBuilder {
    @Value("${camel.ride.app.output}")
    private String from;

    @Value("${camel.ride.app.input}")
    private String to;

    @Value("${camel.ride.app.error}")
    private String errorDestination;

    @Override
    public void configure() throws Exception {
        from(to)
            .to(from)
                .errorHandler(deadLetterChannel(errorDestination)).routeId("simpleRoute");
    }

}