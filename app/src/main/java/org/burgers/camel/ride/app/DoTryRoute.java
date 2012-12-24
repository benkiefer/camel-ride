package org.burgers.camel.ride.app;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DoTryRoute extends SpringRouteBuilder {
    private String from;
    private String to;
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

    @Value("${camel.ride.app.do.try.input}")
    public void setFrom(String from) {
        this.from = from;
    }

    @Value("${camel.ride.app.do.try.output}")
    public void setTo(String to) {
        this.to = to;
    }

    @Value("${camel.ride.app.do.try.error}")
    public void setError(String error) {
        this.error = error;
    }
}
