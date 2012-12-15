package org.burgers.camel.ride.app;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class SimpleProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("There is an exchange going on.");
        System.out.println(exchange.getIn().getHeader("CamelFileName"));
        System.out.println(exchange.getIn().getBody());
        System.out.println(exchange.getIn().getBody().getClass());
    }
}
