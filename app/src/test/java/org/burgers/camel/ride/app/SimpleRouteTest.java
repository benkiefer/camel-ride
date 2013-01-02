package org.burgers.camel.ride.app;

import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleRouteTest extends CamelTestSupport {
    @EndpointInject(uri = "mock:to")
    private MockEndpoint mockTo;

    @Produce(uri = "direct:simpleRouteStart")
    private ProducerTemplate template;

    @Mock
    private Processor mockProcessor;

    private SimpleRoute route;

    @Before
    public void setup() throws Exception {
        createCamelContext();
        route = new SimpleRoute();
        route.setTo(mockTo.getEndpointUri());
        route.setFrom("direct:simpleRouteStart");
        route.setProcessor(mockProcessor);
        context().addRoutes(route);
    }

    @Test
    public void happy_path() throws Exception {
        mockTo.expectedMessageCount(1);

        template.sendBody("hi");

        verify(mockProcessor).process(any(Exchange.class));
        assertMockEndpointsSatisfied();
    }

    @After
    public void tearDown(){
        resetMocks();
    }

}
