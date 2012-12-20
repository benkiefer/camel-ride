package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OnExceptionRoute extends SpringRouteBuilder {
    @Value("${camel.ride.app.input}")
    private String from;

    @Value("${camel.ride.app.output}")
    private String to;

    @Value("${camel.ride.app.error}")
    private String error;

    @Override
    public void configure() throws Exception {
        from(from).id(from)
            .to(from).id(to).routeId(OnExceptionRoute.class.getSimpleName())
                .onException(RuntimeException.class).to(error).id(error).handled(true);
    }

}