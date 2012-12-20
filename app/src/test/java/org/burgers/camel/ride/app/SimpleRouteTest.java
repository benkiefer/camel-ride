package org.burgers.camel.ride.app;


import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;

public class SimpleRouteTest extends CamelTestCase {
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

    @Test
    @DirtiesContext
    public void route_happy_path() throws Exception {
        String route = "SimpleRoute";

        replaceEndpointByIdInRoute(route, to, mockTo.getEndpointUri());
        replaceEndpointByIdInRoute(route, error, mockError.getEndpointUri());

        mockError.setExpectedMessageCount(0);
        mockTo.setExpectedMessageCount(1);

        producer.sendBody("hi");

        mockTo.assertIsSatisfied();
        mockError.assertIsSatisfied();
    }

    @Test
    @DirtiesContext
    public void route_error_handling() throws Exception {
        String route = "SimpleRoute";

        replaceEndpointByIdInRoute(route, to, mockTo.getEndpointUri());
        replaceEndpointByIdInRoute(route, error, mockError.getEndpointUri());

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

}
