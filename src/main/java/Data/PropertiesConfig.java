package Data;

import java.io.*;
import java.util.Properties;

public class PropertiesConfig {

    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties = new Properties();


    public static void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);

            properties.getProperty("invoiceFolderPath");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
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


    public static String getTemplateFolderPath() {
        return properties.getProperty("templateFolderPath");
    }

    public static void setTemplateFolderPath(String templateFolderPath) {
        properties.setProperty("templateFolderPath", templateFolderPath);
        saveConfig();
    }



}
