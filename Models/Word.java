package boucoiran.fr.worddecoder.Models;

import static boucoiran.fr.worddecoder.utils.Constants.DIR_H;
import static boucoiran.fr.worddecoder.utils.Constants.DIR_V;

public class Word {
        private int x;
        private int y;
        private int direction;
        private int length;
        private WordSquare[] letters;

    public Word(int x, int y, int direction, int length, WordSquare[] letters) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.length = length;
        this.letters = letters;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public WordSquare[] getLetters() {
        return letters;
    }

    public void setLetters(WordSquare[] letters) {
        this.letters = letters;
    }

    public String getWordAsString() {
        String strWord = "";
        for(int i=0; i<letters.length; i++) {
            strWord += letters[i].getLetter();
        }
        return strWord;
    }

    public String getNumbersAsString() {
        String strWord = "";
        for(int i=0; i<letters.length; i++) {
            strWord += letters[i].getLetterNumber() + " ";
        }
        return strWord;
    }

    public String getDirectionAsString() {
        if(direction == DIR_H) return "Horizontal";
        if(direction == DIR_V) return "Vertical";
        return "Dir Unknnown";
    }

    public String getStartPositionAsString () {
        return "("+x+";"+y+")";
    }

    public String toString() {
        return getDirectionAsString()+" "+getStartPositionAsString() + " " + getWordAsString();
    }

    public boolean isSolved() {
        for(int i=0; i<letters.length; i++) {
            if(!letters[i].isSolved()) return false;
        }
        return true;
    }
}
