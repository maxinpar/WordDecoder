package boucoiran.fr.worddecoder.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import static boucoiran.fr.worddecoder.utils.Constants.DIR_H;
import static boucoiran.fr.worddecoder.utils.Constants.DIR_V;

public class WordGrid {
    private int width;
    private int height;
    private WordSquare[][] grid = new WordSquare[width][height];
    private ArrayList<Word> words = new ArrayList<Word>();

    public ArrayList getWords() {
        words.addAll(getHorizontalWords());
        words.addAll(getVerticalWords());
        return words;
    }

    private ArrayList getHorizontalWords() {
        ArrayList<Word> hWords = new ArrayList<Word>();
        ArrayList<WordSquare> w;

        width = grid.length;
        height = grid[0].length;

        //Log.i("WordGrid", "getHorizontal Words - Cycling through with width = "+width + " and height = "+ height);

        for(int x=0; x<height; x++) {
            w = new ArrayList<WordSquare>();
            for(int y=0; y<width;y++) {
                //Log.i("WordGrid", "getHorizontal Words - Cycling through with ["+x+";"+y+"]");
                if(grid[x][y].isBlackSquare()) {
                    //check word, save word
                    //Log.i("WordGrid", "\tgetHorizontal Words - square is black");
                    if (w.size() > 1) {
                        //Word is valid, longer than 1 square, create a Word object and save
                        hWords.add(new Word(y - w.size()+1, x, DIR_H, w.size(), (WordSquare[]) w.toArray(new WordSquare[w.size()])));
                    }
                    w = new ArrayList<WordSquare>();
                } else {
                    //we've reached a letter square. Let's add it to our word.
                    //Log.i("WordGrid", "\tgetHorizontal Words - square is NOT black");
                    //Log.i("WordGrid", "\tgetHorizontal Words - square is "+grid[x][y].getLetter());
                    w.add(grid[x][y]);
                    if(y==width-1) {
                        //reached end of grid
                        if (w.size() > 1) {

                            //Word is valid, longer than 1 square, create a Word object and save
                            hWords.add(new Word(y - w.size()+1, x,  DIR_H, w.size(), w.toArray(new WordSquare[w.size()])));
                        }
                        w = new ArrayList<WordSquare>();
                    }
                }
            }
        }
        return hWords;
    }

    private ArrayList getVerticalWords() {
        ArrayList<Word> vWords = new ArrayList<Word>();
        ArrayList<WordSquare> w;

        width = grid.length;
        height = grid[0].length;

        for(int y=0; y<width; y++) {
            w = new ArrayList<WordSquare>();
            for(int x=0; x<height;x++) {
                if(grid[x][y].isBlackSquare()) {
                    //check word, save word
                    if (w.size() > 1) {
                        //Word is valid, longer than 1 square, create a Word object and save
                        vWords.add(new Word(y, x - w.size()+1, DIR_V, w.size(), (WordSquare[]) w.toArray(new WordSquare[w.size()])));
                    }
                    w = new ArrayList<WordSquare>();
                } else {
                    //we've reached a letter square. Let's add it to our word.
                    //Log.i("WordGrid", "\tgetHorizontal Words - square is NOT black");
                    //Log.i("WordGrid", "\tgetHorizontal Words - square is "+grid[x][y].getLetter());
                    w.add(grid[x][y]);
                    if(x==height-1) {
                        //reached end of grid
                        if (w.size() > 1) {
                            //Word is valid, longer than 1 square, create a Word object and save
                            vWords.add(new Word(y, x - w.size()+1, DIR_V, w.size(), (WordSquare[]) w.toArray(new WordSquare[w.size()])));
                        }
                        w = new ArrayList<WordSquare>();
                    }
                }
            }
        }
        return vWords;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public WordSquare[][] getGrid() {
        return grid;
    }

    public void setGrid(WordSquare[][] grid) {
        this.grid = grid;
    }

    public String toString() {
        Log.i("in WordGrid", "trying to generate a string");
        String toRet = " \n";
        for(int row=0; row<height; row++) {
            for(int col=0; col<width; col++) {
                toRet+=grid[row][col].toString();
                toRet+="\t";
            }
            toRet+="\n";
        }
        return(toRet);
    }

    public WordGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new WordSquare[height][width];
    }

    public void updateSquare(int row, int col, String letter) {
        this.grid[row][col].setSolved(true);
        this.grid[row][col].setLetter(letter);
    }

    private void displayWord(ArrayList w) {
        Log.i("WordGrid", "\t\t********************************************************");
        Iterator iter = w.iterator();
        String disp = "";
        WordSquare ws;

        while(iter.hasNext()){
            ws = (WordSquare)iter.next();
            disp+=ws.getLetter();
        }

        Log.i("WordGrid", "\t\tWe have a word: "+disp);
        Log.i("WordGrid", "\t\t********************************************************");
    }
}
