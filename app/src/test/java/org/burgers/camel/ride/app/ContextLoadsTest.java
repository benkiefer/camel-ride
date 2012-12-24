package org.burgers.camel.ride.app;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.fail;

public class ContextLoadsTest {
    @Test
    public void make_sure_context_loads(){
        try {
            new ClassPathXmlApplicationContext("contexts/camel-ride-bootstrap.xml");
        } catch (Exception e){
            fail("Context didn't load");
        }
    }

}
