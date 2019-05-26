package boucoiran.fr.worddecoder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import boucoiran.fr.worddecoder.DBUtils.DBHelper;
import boucoiran.fr.worddecoder.Models.CodeCrackerGame;
import boucoiran.fr.worddecoder.Models.LetterGrid;
import boucoiran.fr.worddecoder.Models.Word;
import boucoiran.fr.worddecoder.Models.WordGrid;
import boucoiran.fr.worddecoder.Models.WordSquare;
import boucoiran.fr.worddecoder.utils.Solver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runDemo();
    }

    private void runDemo() {
        String matches[] = null;
        WordGrid wg = new WordGrid(13, 13);
        wg = fillGrid();
        LetterGrid lg = new LetterGrid(26);

        CodeCrackerGame ccg = new CodeCrackerGame();
        ccg.setlGrid(lg);
        ccg.setwGrid(wg);
        //Log.i("MainActivity", wg.toString());

        ccg.addLetter(1, "l");
        ccg.addLetter(10, "s");
        ccg.addLetter(13, "y");
        ArrayList<Word> words = ccg.getwGrid().getWords();

        Solver.displayWords(words);
        Solver.displayLetters(ccg.getlGrid().getLetters());
        //Log.i("MainActivity", ccg.getwGrid().toString());
        DBHelper dbh = new DBHelper(this);

        try{
            boolean result = Solver.newSolver(words, ccg.getlGrid().getLetters(), this);
        } catch (Exception e) {
            Log.i("MainActivity", "Exception:"+e.getMessage());
        }
        displayListOfWords(words);
    }

    private void displayListOfWords(ArrayList w) {
        Word word;
        Iterator i = w.iterator();
        while (i.hasNext()) {
            word = (Word)i.next();
            Log.i("MainActivity", word.toString());
        }
    }


    private WordGrid fillGrid () {
        WordGrid w = new WordGrid(13,13);
        WordSquare[][] g = w.getGrid();
        g[0][0] = new WordSquare(6);
        g[0][1] = new WordSquare(22);
        g[0][2] = new WordSquare(22);
        g[0][3] = new WordSquare(16);
        g[0][4] = new WordSquare(1);
        g[0][5] = new WordSquare(16);
        g[0][6] = new WordSquare(3);
        g[0][7] = new WordSquare(6);
        g[0][8] = new WordSquare(5);
        g[0][9] = new WordSquare(20);
        g[0][10] = new WordSquare(12);
        g[0][11] = new WordSquare(15);
        g[0][12] = new WordSquare(true);

        g[1][0] = new WordSquare(1);
        g[1][1] = new WordSquare(true);
        g[1][2] = new WordSquare(14);
        g[1][3] = new WordSquare(true);
        g[1][4] = new WordSquare(6);
        g[1][5] = new WordSquare(true);
        g[1][6] = new WordSquare(6);
        g[1][7] = new WordSquare(true);
        g[1][8] = new WordSquare(16);
        g[1][9] = new WordSquare(true);
        g[1][10] = new WordSquare(22);
        g[1][11] = new WordSquare(true);
        g[1][12] = new WordSquare(25);

        g[2] = getLine(new int[] {10, 25, 20, 26, 13, 0, 2, 3, 16, 10, 14, 16, 3});
        g[3] = getLine(new int[] {12, 0,23, 0, 16, 0, 2, 0, 15, 0, 3, 0, 6});
        g[4] = getLine(new int[] {0, 20, 15, 12, 3, 17, 20, 15, 6, 5, 16, 1, 13});
        g[5] = getLine(new int[] {22, 0, 16, 0, 0, 0, 6, 0, 21, 0, 0, 0, 20});
        g[6] = getLine(new int[] {12, 24, 13, 21, 16, 15, 0, 19, 16, 6, 22, 12, 15});
        g[7] = getLine(new int[] {15, 0, 0, 0, 24, 0, 19, 0, 0, 0, 12, 0, 21});
        g[8] = getLine(new int[] {8, 12, 1, 18, 25, 5, 18, 12, 18, 10, 1, 13, 0});
        g[9] = getLine(new int[] {20, 0, 18, 0, 12, 0, 3, 0, 23, 0, 1, 0, 15});
        g[10] = getLine(new int[] {22, 12, 15, 4, 18, 3, 16, 0, 19, 1, 6, 9, 16});
        g[11] = getLine(new int[] {5, 0, 21, 0, 15, 0, 6, 0, 16, 0, 21, 0, 11});
        g[12] = getLine(new int[] {0, 14, 16, 6, 17, 7, 18, 6, 3, 5, 16, 3, 10});


        w.setGrid(g);
        return w;
    }

    private WordSquare[] getLine(int[] numbers) {
        WordSquare[] toRet = new WordSquare[numbers.length];
        for (int i =0; i<numbers.length; i++) {
            if(numbers[i]==0) {
                toRet[i] = new WordSquare(true);
            } else {
                toRet[i] = new WordSquare(numbers[i]);
            }
        }
        return toRet;
    }
}
