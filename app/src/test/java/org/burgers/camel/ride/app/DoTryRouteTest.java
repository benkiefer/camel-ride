package org.burgers.camel.ride.app;

import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DoTryRouteTest extends CamelTestSupport {
    @EndpointInject(uri = "mock:to")
    private MockEndpoint mockTo;

    @EndpointInject(uri = "mock:error")
    private MockEndpoint mockError;

    @Produce(uri = "direct:doTryStart")
    private ProducerTemplate template;

    @Before
    public void setup() throws Exception {
        DoTryRoute doTryRoute = new DoTryRoute();
        doTryRoute.setTo(mockTo.getEndpointUri());
        doTryRoute.setFrom("direct:doTryStart");
        doTryRoute.setError(mockError.getEndpointUri());

        context().addRoutes(doTryRoute);
    }

    @Test
    public void route_happy_path() throws Exception {
        mockError.setExpectedMessageCount(0);
        mockTo.setExpectedMessageCount(1);
        template.sendBody("hi");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void route_error_handling() throws Exception {
        mockTo.whenAnyExchangeReceived(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                throw new RuntimeException("Kaboom!!!");
}
        });

        mockError.setExpectedMessageCount(1);
        mockTo.setExpectedMessageCount(1);

        template.sendBody("hi");

        assertMockEndpointsSatisfied();
    }

    @Test
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
            template.sendBody("hi");
            fail("expected failure");
        } catch (CamelExecutionException e) {
            assertMockEndpointsSatisfied();
        }
    }

    @After
    public void tearDown(){
        resetMocks();
    }
}