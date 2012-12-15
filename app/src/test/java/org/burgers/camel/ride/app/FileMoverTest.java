package org.burgers.camel.ride.app;


import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileMoverTest extends CamelFileBasedTestCase {
    @Value("${camel.ride.app.output.directory}")
    private File outputDirectory;

    @Value("${camel.ride.app.input.directory}")
    private File inputDirectory;

    @Autowired
    private CsvToPersonProcessor processor;
    
    @Before
    public void setup() throws IOException {
        FileUtils.cleanDirectory(inputDirectory);
        FileUtils.cleanDirectory(outputDirectory);
    }

    @Test
    public void route() {
        String fileName = "test.txt";

        loadFileToProcess(fileName, "John,Smith\nSue,Anderson");

        runCamelAndWaitForItToFinish();

        assertFalse(new File(inputDirectory, fileName).exists());

        assertTrue(new File(outputDirectory, fileName).exists());

        assertEquals(2, processor.getPeople().size());
    }

    @Override
    protected File getInputDirectory() {
        return inputDirectory;
    }
}
