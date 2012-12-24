package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OnExceptionRoute extends SpringRouteBuilder {
    private String from;
    private String to;
    private String error;

    @Override
    public void configure() throws Exception {
        onException(RuntimeException.class).handled(true).to(error).id(error);

        from(from)
            .to(to)
                .routeId(OnExceptionRoute.class.getSimpleName());
    }

    @Value("${camel.ride.app.on.exception.input}")
    public void setFrom(String from) {
        this.from = from;
    }

    @Value("${camel.ride.app.on.exception.output}")
    public void setTo(String to) {
        this.to = to;
    }

    @Value("${camel.ride.app.on.exception.error}")
    public void setError(String error) {
        this.error = error;
    }
}