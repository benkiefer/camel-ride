package org.burgers.camel.ride.app;


import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.fail;

public class OnExceptionRouteTest extends CamelTestCase {
    @Value("${camel.ride.app.input}")
    private String from;

    @Value("${camel.ride.app.output}")
    private String to;

    @Value("${camel.ride.app.error}")
    private String error;

    @EndpointInject(uri = "mock:error")
    private MockEndpoint mockError;

    @EndpointInject(uri = "mock:output")
    private MockEndpoint mockTo;

    @EndpointInject(uri = "direct:in")
    private ProducerTemplate producer;

    private final String route = OnExceptionRoute.class.getSimpleName();

    @Before
    public void setup() throws Exception {
        replaceEndpointByIdInRoute(route, to, mockTo.getEndpointUri());
        replaceEndpointByIdInRoute(route, error, mockError.getEndpointUri());
    }

    @Test
    @DirtiesContext
    public void route_happy_path() throws Exception {
        mockError.setExpectedMessageCount(0);
        mockTo.setExpectedMessageCount(1);

        producer.sendBody("hi");

        mockTo.assertIsSatisfied();
        mockError.assertIsSatisfied();
    }

    // only runtime exceptions and their subclasses are caught
    @Test
    @DirtiesContext
    public void route_error_handling() throws Exception {
        mockTo.whenAnyExchangeReceived(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                throw new RuntimeException("Kaboom!!!");
            }
        });

        mockError.setExpectedMessageCount(1);
        mockTo.setExpectedMessageCount(1);

        producer.sendBody("hi");

        mockTo.assertIsSatisfied();
        mockError.assertIsSatisfied();
    }

    // only runtime exceptions and their subclasses are caught
    @Test
    @DirtiesContext
    public void route_error_handling_not_handled() throws Exception {
        mockTo.whenAnyExchangeReceived(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                throw new Exception("Kaboom!!!");
            }
        });

        mockError.setExpectedMessageCount(0);
        mockTo.setExpectedMessageCount(1);

        try {
            producer.sendBody("hi");
            fail("expected failure");
        } catch (CamelExecutionException e) {
            mockTo.assertIsSatisfied();
            mockError.assertIsSatisfied();
        }
    }

}
