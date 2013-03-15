import processing.core.PApplet;
import wordcram.Word;

/**
 * Determines the size of words in a TagCloud instance.
 * The size will be determined as following:
 * A) All words have the same weight: use (maxSize - minSize) / 3
 * B) Words have different weights:   use a linear interpolation between [minSize, maxSize] for [0,1]
 * <p/>
 * Hint: WordCramp normalizes after loading all word the word weight's to [0,1]
 */
public class TagCloudSizer implements wordcram.WordSizer {

    private boolean allEqual = true;
    private float minSize;
    private float maxSize;

    public static final float DEFAULT_TOLERANCE = 0.1f;

    /**
     * Initialize an instance for minSize to maxSize for a given array of words.
     * Determines if all words have equal weight using the DEFAULT_TOLERANCE
     *
     * @param minSize minimum text size
     * @param maxSize maximum text size
     * @param words   the list of words
     */
    public TagCloudSizer(float minSize, float maxSize, Word[] words) {
        this(minSize, maxSize, words, DEFAULT_TOLERANCE);
    }

    /**
     * Initialize an instance for minSize to maxSize for a given array of words.
     * Determines if all words have equal weight using the tolerance
     *
     * @param minSize   minimum text size
     * @param maxSize   maximum text size
     * @param words     the list of words
     * @param tolerance the maximum difference beween weight to be equal
     */
    public TagCloudSizer(float minSize, float maxSize, Word[] words, float tolerance) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        if (words.length > 0) {
            float initial = words[0].weight;
            for (Word w : words) {
                if (Math.abs(w.weight - initial) > tolerance) {
                    allEqual = false;
                    break;
                }
            }
        }
    }

    @Override
    public float sizeFor(Word word, int wordRank, int wordCount) {
        if (allEqual) {
            return (maxSize - minSize) / 3.0f;
        } else {
            return PApplet.lerp(minSize, maxSize, word.weight);
        }
    }
}
