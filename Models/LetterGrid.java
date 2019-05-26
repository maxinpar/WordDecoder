package boucoiran.fr.worddecoder.Models;

import java.util.ArrayList;

public class LetterGrid {
    private int size;
    private ArrayList<LetterGridSquare> letters;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<LetterGridSquare> getLetters() {
        return letters;
    }

    public void setLetters(ArrayList<LetterGridSquare> letters) {
        this.letters = letters;
    }

    public LetterGrid(int size) {
        this.size = size;
        this.letters = new ArrayList<LetterGridSquare>(size);
        for(int i =0; i<size; i++) letters.add(new LetterGridSquare(i+1));
    }

    public void setLetterByPos(int pos, String letter) {
        for(int i =0; i<size; i++) {
            if (letters.get(i).getNumber() == pos) {
                letters.get(i).setLetter(letter);
                letters.get(i).setKnown(true);
            }
        }
    }
}
