import processing.core.*;
import wordcram.WordCram;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import wordcram.*;
import wordcram.text.TextSource;

public class Tree extends PApplet {

    private WordCram cram;
    private boolean  draw = true;

    public void setup(){
        Configuration config = Configuration.getInstance();
        size(config.getWidth(),config.getHeight());
        background(255);

        ShapeBasedPlacer placer = ShapeBasedPlacer.fromFile(config.getShapeFile(), Color.black);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(config.getInputFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Word> words = new ArrayList<Word>();

        String line = null;
        try {
            while((line = reader.readLine()) != null){
                String[] values = line.split(",");
                if(values.length == 2){
                    words.add( new Word(values[0].trim(), Float.parseFloat(values[1].trim())));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        cram = new WordCram(this)
            .fromWords(words.toArray(new Word[words.size()]))
            .withPlacer(placer)
            .withNudger(placer)
            .sizedByWeight(config.getMinSize(),config.getMaxSize());
    }

    public void draw() {
        if(this.draw){
            System.out.println("Start drawing tag cloud...");
            cram.drawAll();
            this.draw = false;
            System.out.println("Finished drawing");
            //tell me what didn’t get drawn
            if(Configuration.getInstance().isDebug()){
                int noSpace = 0;
                int tooSmall = 0;
                Word[] skippedWords = cram.getSkippedWords();
                Word[] placedWords = cram.getWords();
                for (Word skipped: skippedWords) {
                    if (skipped.wasSkippedBecause() == WordCram.NO_SPACE) {
                        noSpace++;
                    } else if (skipped.wasSkippedBecause() == WordCram.SHAPE_WAS_TOO_SMALL) {
                        tooSmall++;
                    }
                }
                System.out.println("Total placed Words: " + placedWords.length);
                System.out.println("Total Skipped Words: " + skippedWords.length);
                System.out.println("Skipped because no Space: " + noSpace);
                System.out.println("Skipped because too small: " + tooSmall);
                System.out.println("Finished");
            }

            save(Configuration.getInstance().getOutputFile());
            exit();
        }
    }
}
