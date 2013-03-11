import wordcram.Word;
import wordcram.WordColorer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TreeColorer implements WordColorer {

    private ArrayList<Integer> colors = new ArrayList<Integer>();
    private Random randomGenerator    = new Random();

    public TreeColorer(String colorString){
        for(String c : colorString.split(",")){
            colors.add(ColorHelper.decode(c));
        }
        if(colors.isEmpty()){
            colors.add(ColorHelper.decode("#000000"));
        }
    }

    @Override
    public int colorFor(Word word) {
        return colors.get(randomGenerator.nextInt(colors.size()));
    }
}
