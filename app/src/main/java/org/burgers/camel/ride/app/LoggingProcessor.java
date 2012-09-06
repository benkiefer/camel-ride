package org.burgers.camel.ride.app;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoggingProcessor implements Processor {
    Logger logger = Logger.getLogger(this.getClass());
    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("There is an exchange going on.");
        logger.info(exchange.getFromEndpoint().getEndpointUri());
        logger.info(exchange.getIn().getHeader("CamelFileName"));
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
