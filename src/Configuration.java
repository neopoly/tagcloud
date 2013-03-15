import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton holding the TagCloud configuration.
 */
public class Configuration {
    private static Configuration instance = null;

    private Properties prop = new Properties();

    private Configuration() {
    }

    public String getShapeFile() {
        return prop.getProperty("shapeFile");
    }

    public String getInputFile() {
        return prop.getProperty("inputFile");
    }

    public String getOutputFile() {
        return prop.getProperty("outputFile");
    }

    public int getWidth() {
        return Integer.parseInt(prop.getProperty("width"));
    }

    public int getHeight() {
        return Integer.parseInt(prop.getProperty("height"));
    }

    public int getMinSize() {
        return Integer.parseInt(prop.getProperty("minSize"));
    }

    public int getMaxSize() {
        return Integer.parseInt(prop.getProperty("maxSize"));
    }

    public String getColors() {
        return prop.getProperty("colors");
    }

    public String getBackgroundColor() {
        return prop.getProperty("background");
    }

    public boolean isDebug() {
        return prop.getProperty("debug") != null && prop.getProperty("debug").equals("true");
    }

    /**
     * Load configuration from an input stream
     *
     * @param inputStream to load from
     * @throws IOException
     */
    public void load(InputStream inputStream) throws IOException {
        prop.load(inputStream);
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

}
