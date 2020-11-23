package com.gmail.psyh2409;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static FileInputStream fis;
    private static Properties properties;

    public static Properties getMyProp(){
        properties = new Properties();
        try {
            fis = new FileInputStream(
                    "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "config.properties");
            properties.load(fis);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
