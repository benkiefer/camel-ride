package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileToQueueMover extends SpringRouteBuilder {
    private @Value("${camel.ride.app.queue.destination}") String destination;
    private @Value("${camel.ride.app.queue.source}") String source;

    @Override
    public void configure() throws Exception {
        from(source).id(source).to(destination).id(destination).routeId(FileToQueueMover.class.getSimpleName());
    }

}