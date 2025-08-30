package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private Properties prop;

    public ConfigLoader() { 
        prop = new Properties();
        try {
           
            FileInputStream fis = new FileInputStream("config/config.properties");
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }
}
