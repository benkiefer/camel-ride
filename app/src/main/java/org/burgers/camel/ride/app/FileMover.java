package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileMover extends SpringRouteBuilder {
    @Value("${camel.ride.app.output.directory}")
    private String outputDirectory;

    @Value("${camel.ride.app.input.directory}")
    private String inputDirectory;

    @Value("${camel.ride.app.error.directory}")
    private String errorDirectory;

    @Override
    public void configure() throws Exception {
        from("file:" + inputDirectory)
                .to("file:" + outputDirectory)
                    .errorHandler(deadLetterChannel("file:" + errorDirectory));
    }

}