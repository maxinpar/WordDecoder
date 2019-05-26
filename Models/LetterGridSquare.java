package boucoiran.fr.worddecoder.Models;

public class LetterGridSquare {
    private int number;
    private String letter;
    private boolean known;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    LetterGridSquare(int number) {
        this.number = number;
        this.known = false;
        this.letter = "";
    }

    public LetterGridSquare(int number, String l) {
        this.number = number;
        this.known = true;
        this.letter = l;
    }

}
