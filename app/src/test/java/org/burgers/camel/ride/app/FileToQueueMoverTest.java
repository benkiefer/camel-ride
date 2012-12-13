package org.burgers.camel.ride.app;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.jms.JMSException;
import java.io.File;
import java.net.URISyntaxException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FileToQueueMoverTest extends CamelFileBasedTestCase {
    @Value("${camel.ride.app.queue.source}")
    private File inputDirectory;

    @Value("${camel.ride.app.queue.destination}")
    private String destination;

    private CountingProcessor processor;

    @Before
    public void setup(){
        processor = new CountingProcessor();
    }

    @Test
    public void route() throws JMSException, URISyntaxException {
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
        assertEquals(expected, processor.getCount());
    }

    private void registerDestination() {
        try{
            getCamelContext().addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from(destination).process(processor);
                }
            });
        } catch (Exception e){
            fail("Failed because: " + e.getMessage());
        }
    }

    class CountingProcessor implements Processor {
        private int count = 0;

        @Override
        public void process(Exchange exchange) throws Exception {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

}
