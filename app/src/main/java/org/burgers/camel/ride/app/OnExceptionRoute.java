package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OnExceptionRoute extends SpringRouteBuilder {
    @Value("${camel.ride.app.on.exception.input}")
    private String from;

    @Value("${camel.ride.app.on.exception.output}")
    private String to;

    @Value("${camel.ride.app.on.exception.error}")
    private String error;

    @Override
    public void configure() throws Exception {
        from(from).id(from)
            .to(to).id(to).routeId(OnExceptionRoute.class.getSimpleName())
                .onException(RuntimeException.class).to(error).id(error).handled(true);
    }

}