package org.burgers.camel.ride.app;

import org.apache.camel.CamelContext;
import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:contexts/camel-ride-bootstrap.xml"})
abstract public class CamelFileBasedTestCase {
    @Autowired
    private CamelContext camelContext;

    protected void loadFileToProcess(String fileName, String content){
        File file = new File(getInputDirectory(), fileName);
        try {
            FileUtils.write(file, content);
        } catch (IOException e) {
            fail("Couldn't make the file because: " + e.getMessage());
        }
    }

    protected void runCamelAndWaitForItToFinish(){
        try {
            camelContext.start();
            boolean outOfFiles = false;
            while (!outOfFiles){
                outOfFiles = getInputDirectory().listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File myFile) {
                        return !myFile.isDirectory();
                    }
                }).length == 0;
            }
            camelContext.stop();
        } catch (Exception e) {
            fail("Couldn't run camel because: " + e.getMessage());
        }
    }

    abstract File getInputDirectory();
}

