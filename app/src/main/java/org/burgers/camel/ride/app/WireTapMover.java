package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WireTapMover extends SpringRouteBuilder{
    private @Value("${camel.ride.app.wire.tap.tapped.destination}") String tappedDestination;
    private @Value("${camel.ride.app.wire.tap.original.destination}") String destination;
    private @Value("${camel.ride.app.wire.tap.source}") String source;

//    This lets the real message continue to the destination but fires off the message to a tapped destination

    @Override
    public void configure() throws Exception {
        from(source).wireTap(tappedDestination).to(destination);
    }
}
