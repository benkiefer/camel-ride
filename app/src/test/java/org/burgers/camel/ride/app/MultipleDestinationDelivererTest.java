package org.burgers.camel.ride.app;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

import static junit.framework.Assert.assertEquals;

public class MultipleDestinationDelivererTest extends CamelFileBasedTestCase {
    @Value("${camel.ride.app.multicast.destination.one}")
    private File destinationOne;

    @Value("${camel.ride.app.multicast.destination.two}")
    private File destinationTwo;

    @Value("${camel.ride.app.multicast.source}")
    private File inputDirectory;

    @Before
    public void setup(){
        FileUtil.makeOrCleanDirectory(inputDirectory);
        FileUtil.makeOrCleanDirectory(destinationOne);
        FileUtil.makeOrCleanDirectory(destinationTwo);
    }

    @Test
    public void x(){
        loadFileToProcess("test.xml", "some xml");
        runCamelAndWaitForItToFinish();

        assertEquals(1, destinationOne.listFiles().length);
        assertEquals(1, destinationTwo.listFiles().length);
    }

    @Override
    protected File getInputDirectory() {
        return inputDirectory;
    }
}
