package org.burgers.camel.ride.app;


import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OnExceptionRouteTest extends CamelTestSupport {
    @EndpointInject(uri = "mock:error")
    private MockEndpoint mockError;

    @EndpointInject(uri = "mock:to")
    private MockEndpoint mockTo;

    @EndpointInject(uri = "direct:onExceptionStart")
    private ProducerTemplate producer;

    @Before
    public void setup() throws Exception {
        OnExceptionRoute onExceptionRoute = new OnExceptionRoute();
        onExceptionRoute.setTo(mockTo.getEndpointUri());
        onExceptionRoute.setFrom("direct:onExceptionStart");
        onExceptionRoute.setError(mockError.getEndpointUri());

        context().addRoutes(onExceptionRoute);
    }

    @Test
    public void route_happy_path() throws Exception {
        mockError.setExpectedMessageCount(0);
        mockTo.setExpectedMessageCount(1);

        producer.sendBody("hi");

        assertMockEndpointsSatisfied();
    }

    // only runtime exceptions and their subclasses are caught
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

        producer.sendBody("hi");

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
            producer.sendBody("hi");
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
