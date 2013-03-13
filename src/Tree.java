import processing.core.*;
import wordcram.WordCram;

import java.awt.*;
import java.awt.image.PackedColorModel;
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
        background(ColorHelper.decode(config.getBackgroundColor()));

        ShapeBasedPlacer placer  = ShapeBasedPlacer.fromFile(config.getShapeFile(), Color.black);
        InputWords       input   = new InputWords(config.getInputFile());
        Word[]           words   = input.getWords();
        TreeColorer      colorer = new TreeColorer(config.getColors());
        WordSizer        sizer   = new WordSizer(config.getMinSize(), config.getMaxSize(), words);

        cram = new WordCram(this)
            .fromWords(words)
            .withColorer(colorer)
            .withPlacer(placer)
            .withNudger(placer)
            .withSizer(sizer);
    }



    public void draw() {
        if(this.draw){
            System.out.println("Start drawing tag cloud...");
            cram.drawAll();
            this.draw = false;
            System.out.println("Finished drawing");

            if(Configuration.getInstance().isDebug()){
                printDebug();
            }

            save(Configuration.getInstance().getOutputFile());
            exit();
        }
    }

    private void printDebug(){
        //tell me what didn’t get drawn
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
}
