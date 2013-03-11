import wordcram.Word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputWords {
    private String file;

    public InputWords(String file){
        this.file = file;
    }

    public Word[] getWords(){
        ArrayList<Word> words = new ArrayList<Word>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Word[0];
        }

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
            return new Word[0];
        }

        return words.toArray(new Word[words.size()]);
    }
}
