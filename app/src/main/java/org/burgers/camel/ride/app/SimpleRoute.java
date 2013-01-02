package org.burgers.camel.ride.app;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleRoute extends SpringRouteBuilder {
    private String to;
    private String from;
    private Processor processor = new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {
            System.out.println("Hello World!");
        }
    };

    @Override
    public void configure() throws Exception {
        from(from).process(processor).to(to);
    }

    @Value("${camel.ride.app.simple.input}")
    public void setTo(String to) {
        this.to = to;
    }

    @Value("${camel.ride.app.simple.output}")
    public void setFrom(String from) {
        this.from = from;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }
}
