package org.burgers.camel.ride.app.transformation;

import org.springframework.stereotype.Component;

@Component
public class UniqueFileNameGenerator {
    public String generate(){
        return String.valueOf(System.currentTimeMillis());
    }

}
