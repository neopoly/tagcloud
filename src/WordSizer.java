import processing.core.PApplet;
import wordcram.Word;

public class WordSizer implements wordcram.WordSizer{

    private boolean allEqual = true;
    private float minSize  = 4;
    private float maxSize  = 38;

    public WordSizer(float minSize, float maxSize, Word[] words){
        this.minSize = minSize;
        this.maxSize = maxSize;
        if(words.length > 0){
            float initial = words[0].weight;
            for(Word w : words){
                if(Math.abs(w.weight - initial) > 0.1){
                    allEqual = false;
                    break;
                }
            }
        }
    }

    @Override
    public float sizeFor(Word word, int wordRank, int wordCount) {
        if(allEqual){
            return (maxSize - minSize) / 3.0f;
        }
        else{
            return PApplet.lerp(minSize, maxSize, word.weight);
        }
    }
}
