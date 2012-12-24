package org.burgers.camel.ride.app;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:contexts/camel-ride-bootstrap.xml"})
abstract public class CamelTestCase {
    @Autowired
    private ModelCamelContext camelContext;

    protected void replaceEndpointByIdInRoute(String routeId, final String oldValue, final String newValue) throws Exception {
        context().getRouteDefinition(routeId).adviceWith(context(), new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveById(oldValue).replace().to(newValue);
            }
        });
    }

    protected ModelCamelContext context() {
        return camelContext;
    }

}

