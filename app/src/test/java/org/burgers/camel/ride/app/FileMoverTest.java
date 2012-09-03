package org.burgers.camel.ride.app;


import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.processor.CamelLogProcessor;
import org.apache.camel.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:contexts/ApplicationContext.xml"})
public class FileMoverTest {
    private
    @Value("${camel.ride.app.output.directory}")
    File outputDirectory;
    private
    @Value("${camel.ride.app.input.directory}")
    File inputDirectory;
    
    @Autowired
    private CamelContext camelContext;

    @Before
    public void setup() throws IOException {
        FileUtils.cleanDirectory(inputDirectory);
        FileUtils.cleanDirectory(outputDirectory);
    }

    @Test
    public void x() throws Exception {
        File file = new File(inputDirectory, "test.txt");
        FileUtils.write(file, "Hello World!");

        Thread.sleep(1000);

        File[] files = inputDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("*.txt");
            }
        });

        assertTrue(files.length == 0);
        assertTrue(1 == outputDirectory.listFiles().length);
    }

}
