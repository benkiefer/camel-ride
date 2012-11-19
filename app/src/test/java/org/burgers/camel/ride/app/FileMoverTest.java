package org.burgers.camel.ride.app;


import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileMoverTest extends CamelFileBasedTestCase {
    @Value("${camel.ride.app.output.directory}")
    private File outputDirectory;

    @Value("${camel.ride.app.input.directory}")
    private File inputDirectory;
    
    @Before
    public void setup() throws IOException {
        FileUtils.cleanDirectory(inputDirectory);
        FileUtils.cleanDirectory(outputDirectory);
    }

    @Test
    public void route() {
        loadFileToProcess("test.txt", "Hello World");
        runCamelAndWaitForItToFinish();
        assertEquals(1,outputDirectory.listFiles().length);
    }

    @Override
    protected File getInputDirectory() {
        return inputDirectory;
    }
}
