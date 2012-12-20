package org.burgers.camel.ride.app;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.impl.DefaultEndpoint;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:contexts/camel-ride-bootstrap.xml"})
abstract public class CamelTestCase {

    @Autowired
    private CamelContext camelContext;

    protected void runRoute(String route){
        try {
            context().startRoute(route);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    protected void replaceByIdInRoute(String routeId, final String oldValue, final String newValue) throws Exception{
        context().getRouteDefinition(routeId).adviceWith(context(), new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveById(oldValue).replace().to(newValue);
            }
        });
    }

    protected void replaceByUriInRoute(String routeId, String oldUri, final String newValue) throws Exception {
        DefaultEndpoint endpoint = (DefaultEndpoint) context().getEndpoint(oldUri);
        replaceByIdInRoute(routeId, endpoint.getId(), newValue);
    }

    protected CamelContext context(){
        return camelContext;
    }

}

