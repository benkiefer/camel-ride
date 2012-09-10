package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MultipleDestinationDeliverer extends SpringRouteBuilder {
    private @Value("${camel.ride.app.multicast.destination.one}") String destinationOne;
    private @Value("${camel.ride.app.multicast.destination.two}") String destinationTwo;
    private @Value("${camel.ride.app.multicast.source}") String source;

//    Park the message at two destinations
    @Override
    public void configure() throws Exception {
        from(source).multicast().to(destinationOne, destinationTwo);
    }
}
