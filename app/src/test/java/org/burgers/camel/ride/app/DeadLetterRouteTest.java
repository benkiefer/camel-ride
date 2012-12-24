package org.burgers.camel.ride.app;

import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

public class DeadLetterRouteTest extends CamelTestSupport {
    @EndpointInject(uri = "mock:error")
    private MockEndpoint mockError;

    @EndpointInject(uri = "mock:to")
    private MockEndpoint mockTo;

    @Produce(uri = "direct:start")
    private ProducerTemplate template;

    private DeadLetterRoute route;

    @Before
    public void setup() throws Exception {
        route = new DeadLetterRoute();
        route.setError(mockError.getEndpointUri());
        route.setTo(mockTo.getEndpointUri());
        route.setFrom("direct:start");
        route.setRetries(0);
    }

    @Test
    public void happy_path() throws Exception {
        context().addRoutes(route);
        mockTo.setExpectedMessageCount(1);
        mockError.setExpectedMessageCount(0);

        template.sendBody("hi");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void dead_letter_channel_no_retry() throws Exception {
        context().addRoutes(route);
        failWhenSendingTo(mockTo);

        mockTo.setExpectedMessageCount(1);
        mockError.setExpectedMessageCount(1);

        template.sendBody("hi");

        assertMockEndpointsSatisfied();
    }

    // 2 messages are sent to the "to" endpoint, but both fail. Only one ends up on the dead letter channel.
    @Test
    public void dead_letter_channel_with_retry() throws Exception {
        route.setRetries(1);
        context().addRoutes(route);
        failWhenSendingTo(mockTo);

        mockTo.setExpectedMessageCount(2);
        mockError.setExpectedMessageCount(1);

        template.sendBody("hi");

        assertMockEndpointsSatisfied();
    }

    private void failWhenSendingTo(MockEndpoint mockEndpoint) {
        mockEndpoint.whenAnyExchangeReceived(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                throw new RuntimeException("Kaboom!");
            }
        });
    }

}

