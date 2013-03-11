import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static Configuration instance = null;

    private Properties prop = new Properties();

    private Configuration(){}

    public String getShapeFile(){ return prop.getProperty("shapeFile"); }
    public String getInputFile(){ return prop.getProperty("inputFile"); }
    public String getOutputFile(){ return prop.getProperty("outputFile"); }
    public int getWidth() { return Integer.parseInt(prop.getProperty("width")); }
    public int getHeight(){ return Integer.parseInt(prop.getProperty("height")); }
    public int getMinSize(){ return Integer.parseInt(prop.getProperty("minSize")); }
    public int getMaxSize(){ return Integer.parseInt(prop.getProperty("maxSize")); }
    public String getColors(){ return prop.getProperty("colors"); }
    public String getBackgroundColor(){return prop.getProperty("background"); }
    public boolean isDebug(){
        return prop.getProperty("debug") != null && prop.getProperty("debug").equals("true");
    }

    public void load(){
        try {
            prop.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getInstance(){
        if(instance == null){
            instance = new Configuration();
        }
        return instance;
    }

}
