package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileMover extends SpringRouteBuilder {
    private @Value("${camel.ride.app.output.directory}") String outputDirectory;
    private @Value("${camel.ride.app.input.directory}") String inputDirectory;
    private @Value("${camel.ride.app.delay}") long delay;
    private @Autowired LoggingProcessor loggingProcessor;

    @Override
    public void configure() throws Exception {
        from("file:" + inputDirectory).to("file:" + outputDirectory).delay(delay).process(loggingProcessor);
    }

}