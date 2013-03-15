import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import processing.core.PVector;
import wordcram.Word;
import wordcram.WordNudger;
import wordcram.WordPlacer;

/**
 * Places and nudges words in the TagCloud basing on a shape image.
 * <p/>
 * Main parts from the comment section of: http://wordcram.org/2013/02/13/shapes-for-wordcram/
 */
class TagCloudPlacer implements WordPlacer, WordNudger {

    public static int TOLERANCE = 5;

    private Area area;
    private float minX, minY, maxX, maxY;
    private Random random;
    private BufferedImage image;

    private TagCloudPlacer(BufferedImage image) {
        this.image = image;
    }

    private TagCloudPlacer init() {
        random = new Random();
        Rectangle2D areaBounds = area.getBounds2D();
        this.minX = (float) areaBounds.getMinX();
        this.minY = (float) areaBounds.getMinY();
        this.maxX = (float) areaBounds.getMaxX();
        this.maxY = (float) areaBounds.getMaxY();
        return this;
    }

    /**
     * Build a place with bases on an image load from a File
     * @param path  Path to load the file from.
     * @param color Color to use as foreground
     * @param precise Shell the used shape be precise as the image information. Seems to be better with false
     * @return a placer instance bound to the image
     */
    public static TagCloudPlacer fromFile(String path, Color color, boolean precise) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fromImage(image, color, precise);
    }

    /**
     * Build a place with bases on an image
     * @param image The shape image
     * @param color Color to use as foreground
     * @param precise Shell the used shape be precise as the image information. Seems to be better with false
     * @return a placer instance bound to the image
     */
    public static TagCloudPlacer fromImage(BufferedImage image, Color color, boolean precise){
        TagCloudPlacer result = new TagCloudPlacer(image);
        if (precise) {
            result.fromImagePrecise(color);
        } else {
            result.fromImageSloppy(color);
        }
        return result.init();
    }

    private void fromImagePrecise(Color color) {
        Area area = new Area();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixel = new Color(image.getRGB(x, y));
                if (isIncluded(color, pixel)) {
                    Rectangle r = new Rectangle(x, y, 1, 1);
                    area.add(new Area(r));
                }
            }
        }
        this.area = area;
    }

    private void fromImageSloppy(Color color) {
        Area area = new Area();
        Rectangle r;
        int y1, y2;
        for (int x = 0; x < image.getWidth(); x++) {
            y1 = 99;
            y2 = -1;
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixel = new Color(image.getRGB(x, y));
                if (isIncluded(color, pixel)) {
                    if (y1 == 99) {
                        y1 = y;
                        y2 = y;
                    }
                    if (y > (y2 + 1)) {
                        r = new Rectangle(x, y1, 1, y2 - y1);
                        area.add(new Area(r));
                        y1 = y;
                    }
                    y2 = y;
                }
            }
            if ((y2 - y1) >= 0) {
                r = new Rectangle(x, y1, 1, y2 - y1);
                area.add(new Area(r));
            }
        }
        this.area = area;
    }

    @Override
    public PVector place(Word w, int rank, int count, int ww, int wh, int fw, int fh) {

        w.setProperty("width", ww);
        w.setProperty("height", wh);

        for (int i = 0; i < 1000; i++) {
            float newX = randomBetween(minX, maxX);
            float newY = randomBetween(minY, maxY);
            if (area.contains(newX, newY, ww, wh)) {
                return new PVector(newX, newY);
            }
        }

        return new PVector(-1, -1);
    }

    @Override
    public PVector nudgeFor(Word word, int attempt) {
        PVector target = word.getTargetPlace();
        float wx = target.x;
        float wy = target.y;
        float ww = (Integer) word.getProperty("width");
        float wh = (Integer) word.getProperty("height");

        for (int i = 0; i < 1000; i++) {
            float newX = randomBetween(minX, maxX);
            float newY = randomBetween(minY, maxY);

            if (area.contains(newX, newY, ww, wh)) {
                return new PVector(newX - wx, newY - wy);
            }
        }

        return new PVector(-1, -1);
    }

    private float randomBetween(float a, float b) {
        return a + random.nextFloat() * (b - a);
    }

    private boolean isIncluded(Color target, Color pixel) {
        int rT = target.getRed();
        int gT = target.getGreen();
        int bT = target.getBlue();
        int rP = pixel.getRed();
        int gP = pixel.getGreen();
        int bP = pixel.getBlue();
        return ((rP - TOLERANCE <= rT) && (rT <= rP + TOLERANCE)
                && (gP - TOLERANCE <= gT) && (gT <= gP + TOLERANCE)
                && (bP - TOLERANCE <= bT) && (bT <= bP + TOLERANCE));
    }
}
