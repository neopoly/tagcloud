import java.awt.*;
public class ColorHelper {
    public static int transform(Color c){
        return (c.getAlpha() << 24) | (c.getRed() << 16) | (c.getGreen() << 8) | c.getBlue();
    }

    public static int decode(String c){
        return transform(Color.decode(c));
    }
}
