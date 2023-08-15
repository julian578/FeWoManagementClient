package Data;

import java.io.*;
import java.util.Properties;

public class PropertiesConfig {

    private static final String CONFIG_FILE = "/config.properties";
    private static Properties properties = new Properties();


    public static void loadConfig() {
        try (InputStream input = PropertiesConfig.class.getResourceAsStream(CONFIG_FILE)) {
            properties.load(input);

            properties.getProperty("invoiceFolderPath");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try (OutputStream output = PropertiesConfig.class.getClassLoader().getResource(CONFIG_FILE).openConnection().getOutputStream()) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getInvoiceFolderPath() {
        return properties.getProperty("invoiceFolderPath");
    }

    public static void setInvoiceFolderPath(String invoiceFolderPath) {
        properties.setProperty("invoiceFolderPath", invoiceFolderPath);
        saveConfig();
    }






}
