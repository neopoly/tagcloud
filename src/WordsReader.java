import wordcram.Word;

import java.io.*;
import java.util.ArrayList;

/**
 * Helper to read list of Words with initial weights from a simple Textfile.
 * Supported text format:
 * WORD1,WEIGHT1
 * WORD2,WEIGHT2
 *
 * WORD must not contain a comma, but can include a space character.
 * WEIGHT will be parsed as a Float
 */
public class WordsReader {
    private static final String UTF8 = "UTF-8";
    private String file;
    public static final String SEPARATOR = ",";

    /**
     * Constructs a reader
     * @param file to be parsed
     */
    public WordsReader(String file){
        this.file = file;
    }

    /**
     * Read the file and parse a list of words.
     * @return an array of weighted words.
     */
    public Word[] getWords(){
        ArrayList<Word> words = new ArrayList<Word>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Word[0];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new Word[0];
        }

        String line;
        try {
            while((line = reader.readLine()) != null){
                Word word = parse(line);
                if(word != null)
                    words.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Word[0];
        }

        return words.toArray(new Word[words.size()]);
    }

    private Word parse(String line){
        String[] values = line.split(SEPARATOR);
        if(values.length == 2){
            return new Word(values[0].trim(), Float.parseFloat(values[1].trim()));
        }
        else {
            return null;
        }
    }
}
