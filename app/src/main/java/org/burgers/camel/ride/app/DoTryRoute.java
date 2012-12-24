package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DoTryRoute extends SpringRouteBuilder {
    @Value("${camel.ride.app.do.try.input}")
    private String from;

    @Value("${camel.ride.app.do.try.output}")
    private String to;

    @Value("${camel.ride.app.do.try.error}")
    private String error;

    @Override
    public void configure() throws Exception {
        from(from).id(from)
                .routeId(DoTryRoute.class.getSimpleName())
                .doTry()
                    .to(to).id(to)
                .doCatch(RuntimeException.class)
                    .to(error).id(error)
                .end();
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setError(String error) {
        this.error = error;
    }
}
