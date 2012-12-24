package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterRoute extends SpringRouteBuilder {
    private String from;
    private String to;
    private String error;
    private int retries;

    @Override
    public void configure() throws Exception {
        errorHandler(deadLetterChannel(error).maximumRedeliveries(retries));

        from(from)
            .to(to)
                .routeId(DeadLetterRoute.class.getSimpleName());
    }

    @Value("${camel.ride.app.dead.letter.retries}")
    public void setRetries(int retries) {
        this.retries = retries;
    }

    @Value("${camel.ride.app.dead.letter.input}")
    public void setFrom(String from) {
        this.from = from;
    }

    @Value("${camel.ride.app.dead.letter.output}")
    public void setTo(String to) {
        this.to = to;
    }

    @Value("${camel.ride.app.dead.letter.error}")
    public void setError(String error) {
        this.error = error;
    }
}
