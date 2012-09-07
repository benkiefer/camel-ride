package org.burgers.camel.ride.app;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FileToQueueMoverTest extends CamelFileBasedTestCase {
    @Value("${camel.ride.app.queue.source}")
    private File inputDirectory;

    @Value("${camel.ride.app.queue.destination}")
    private String destination;

    final int[] count = {0};

    @Before
    public void setup(){
        FileUtil.cleanDirectory(inputDirectory);
    }

    @Test
    public void route() {
        registerDestination();
        loadFileToProcess("test.txt", "text");
        runCamelAndWaitForItToFinish();
        assertDestinationContainsCount(1);
    }

    @Override
    protected File getInputDirectory() {
        return inputDirectory;
    }

    private void assertDestinationContainsCount(int expected) {
        assertEquals(expected, count[0]);
    }

    private void registerDestination() {
        try{
            getCamelContext().addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from(destination).process(new Processor() {
                        @Override
                        public void process(Exchange exchange) throws Exception {
                            count[0] += 1;
                        }
                    });
                }
            });
        } catch (Exception e){
            fail("Failed because: " + e.getMessage());
        }
    }

}
