package org.burgers.camel.ride.app;

import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileMover extends SpringRouteBuilder {
    private String to;
    private String from;
    private CsvToPersonProcessor csvToPersonProcessor;

    @Override
    public void configure() throws Exception {
        CsvDataFormat format = new CsvDataFormat();
        format.setSkipFirstLine(false);

        from(from)
                // send a copy of the file to my output directory
                .to(to)
                    // break each row of the file into a separate record and send it to the csv processor
                    .split(body().tokenize("\n")).streaming()
                    // breaks the csv row into a list of one list of strings
                    .unmarshal(format)
                    // send it to my processor
                    .bean(csvToPersonProcessor, "process");
    }

    @Value("${camel.ride.app.output.directory}")
    public void setTo(String to) {
        this.to = to;
    }

    @Value("${camel.ride.app.input.directory}")
    public void setFrom(String from) {
        this.from = from;
    }

    @Autowired
    public void setCsvToPersonProcessor(CsvToPersonProcessor csvToPersonProcessor) {
        this.csvToPersonProcessor = csvToPersonProcessor;
    }
}