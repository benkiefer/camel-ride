package org.burgers.camel.ride.app;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static void makeOrCleanDirectory(File file) {
        if (!file.exists()) file.mkdir();
        else if (file.isFile()) throw new RuntimeException("This is a file: " + file.getAbsolutePath());
        else {
            try {
                FileUtils.cleanDirectory(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
