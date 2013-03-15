import java.awt.*;

/**
 * Utility class to transform and parse colors.
 */
public class ColorHelper {
    /**
     * Transforms a java.awt.Color to processings internal int representation.
     *
     * @param c the color to be transformed
     * @return A processing color value
     */
    public static int transform(Color c) {
        return (c.getAlpha() << 24) | (c.getRed() << 16) | (c.getGreen() << 8) | c.getBlue();
    }

    /**
     * Decodes a color string in HEX-Values
     *
     * @param c the color string to be transformed
     * @return A processing color value
     */
    public static int decodeHex(String c) {
        return transform(Color.decode(c));
    }
}
