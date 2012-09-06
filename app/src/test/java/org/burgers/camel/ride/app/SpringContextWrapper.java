package org.burgers.camel.ride.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextWrapper {
    private static ApplicationContext context;

    public static ApplicationContext getContext(){
        if (context == null){
            context = new ClassPathXmlApplicationContext("contexts/camel-ride-bootstrap.xml");
        }
        return context;
    }

}
