package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileMover extends SpringRouteBuilder {
    private @Value("${camel.ride.app.output.directory}") String outputDirectory;
    private @Value("${camel.ride.app.input.directory}") String inputDirectory;

    @Override
    public void configure() throws Exception {
        from("file:" + inputDirectory)
                .multicast()
                    .to("file:" + outputDirectory)
                    .pipeline()
                        .unmarshal().csv().beanRef("csvToPersonProcessor", "process");
    }

}