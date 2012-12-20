package org.burgers.camel.ride.app;


import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;

public class SimpleRouteTest extends CamelTestCase {
    @Value("${camel.ride.app.output}")
    private String from;

    @Value("${camel.ride.app.input}")
    private String to;

    @Value("${camel.ride.app.error}")
    private String errorDestination;

    @EndpointInject(uri = "mock:error")
    private MockEndpoint mockError;

    @EndpointInject(uri = "mock:output")
    private MockEndpoint mockTo;

    @EndpointInject(uri = "direct:in")
    private ProducerTemplate producer;

    @Test
    @DirtiesContext
    public void route_happy_path() throws Exception {
        String routeName = "simpleRoute";

        replaceByUriInRoute(routeName, to, mockTo.getEndpointUri());
        replaceByUriInRoute(routeName, errorDestination, mockError.getEndpointUri());

        mockError.setExpectedMessageCount(0);
        mockTo.setExpectedMessageCount(1);

        producer.sendBody("hi");

        mockTo.assertIsSatisfied();
        mockError.assertIsSatisfied();
    }

}
