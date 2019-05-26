package boucoiran.fr.worddecoder.Models;

public class CodeCrackerGame {
    private WordGrid wGrid;
    private LetterGrid lGrid;

    public WordGrid getwGrid() {
        return wGrid;
    }

    public void setwGrid(WordGrid wGrid) {
        this.wGrid = wGrid;
    }

    public LetterGrid getlGrid() {
        return lGrid;
    }

    public void setlGrid(LetterGrid lGrid) {
        this.lGrid = lGrid;
    }

    /*
     * The addLetter method will add a new letter to the known letters in the letter grid and
     * update the word grid accordingly
     */

    public void addLetter(int pos, String letter) {
        for(int i = 0; i<lGrid.getLetters().size(); i++) {
            if(lGrid.getLetters().get(i).getNumber() == pos) {
                lGrid.getLetters().get(i).setKnown(true);
                lGrid.getLetters().get(i).setLetter(letter);
            }
        }

        for(int row=0; row<wGrid.getHeight(); row++) {
            for(int col=0; col<wGrid.getWidth(); col++) {
                if(wGrid.getGrid()[row][col].getLetterNumber() == pos) {
                    wGrid.updateSquare(row, col, letter);
                    //Log.i("CodeCrackerGame addL:",  "updated ["+col+";"+row+"] with letter "+letter);
                }
            }
        }
    }
}
