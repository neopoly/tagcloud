import wordcram.Word;
import wordcram.WordColorer;

import java.util.ArrayList;
import java.util.Random;

/**
 * Colors a tag cloud instance by using a random color per word, from a CSV list of
 * HEX string colors.
 */
public class TagCloudColorer implements WordColorer {

    private ArrayList<Integer> colors = new ArrayList<Integer>();
    private Random randomGenerator = new Random();

    public static final int FALLBACK_COLOR = ColorHelper.decodeHex("#000000");
    public static final String SEPARATOR = ",";

    /**
     * Initializes a instance with CSV of HEX color strings.
     * Will use FALLBACK_COLOR as default, if no color could be parsed.
     *
     * @param colorString A CSV of HEX color string, e.g. "#000000,#111111,#222222"
     */
    public TagCloudColorer(String colorString) {
        loadColorString(colorString);
        if (colors.isEmpty()) {
            colors.add(FALLBACK_COLOR);
        }
    }

    @Override
    public int colorFor(Word word) {
        return colors.get(randomGenerator.nextInt(colors.size()));
    }

    private void loadColorString(String colorString) {
        for (String c : colorString.split(SEPARATOR)) {
            colors.add(ColorHelper.decodeHex(c));
        }
    }
}
