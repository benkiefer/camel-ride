package org.burgers.camel.ride.app;

import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileMover extends SpringRouteBuilder {
    @Value("${camel.ride.app.output.directory}")
    private String outputDirectory;

    @Value("${camel.ride.app.input.directory}")
    private String inputDirectory;

    @Override
    public void configure() throws Exception {
        CsvDataFormat format = new CsvDataFormat();
        format.setSkipFirstLine(false);

        from("file:" + inputDirectory)
                // send a copy of the file to my output directory
                .to("file:" + outputDirectory)
                    // break each row of the file into a separate record and send it to the csv processor
                    .split(body().tokenize("\n")).streaming()
                    // breaks the csv row into a list of one list of strings
                    .unmarshal(format)
                    // send it to my processor
                    .beanRef("csvToPersonProcessor", "process");
    }

}