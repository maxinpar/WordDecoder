package boucoiran.fr.worddecoder.Models;

public class WordSquare {
    private boolean isBlackSquare;
    private boolean isSolved = false;
    private int letterNumber = 0;
    private String letter = "";


    public WordSquare(int letterNumber) {
        this.letterNumber = letterNumber;
        this.isBlackSquare = false;
        this.isSolved = false;
        this.letter = "_";
    }

    public WordSquare(boolean isBlackSquare) {
        this.isBlackSquare = isBlackSquare;
    }

    public WordSquare(boolean isBlackSquare, boolean isSolved, int letterNumber, String letter) {
        this.isBlackSquare = isBlackSquare;
        this.isSolved = isSolved;
        this.letterNumber = letterNumber;
        this.letter = letter;
    }

    @Override
    public String toString() {
        String toRet = "";
        if(isBlackSquare) return "XXXXX";
        toRet+=this.getLetter();
        if(getLetterNumber()<10) toRet+="[0"+this.getLetterNumber()+"]";
        else toRet+="["+this.getLetterNumber()+"]";

        return toRet;
    }

    public boolean isBlackSquare() {
        return isBlackSquare;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public int getLetterNumber() {
        return letterNumber;
    }

    public String getLetter() {
        return letter;
    }

    public void setBlackSquare(boolean blackSquare) {
        isBlackSquare = blackSquare;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public void setLetterNumber(int letterNumber) {
        this.letterNumber = letterNumber;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
