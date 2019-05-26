package boucoiran.fr.worddecoder.utils;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import boucoiran.fr.worddecoder.DBUtils.DBHelper;
import boucoiran.fr.worddecoder.Models.LetterGridSquare;
import boucoiran.fr.worddecoder.Models.Word;
import boucoiran.fr.worddecoder.Models.WordSquare;

public  class Solver {

    public static ArrayList<Word> words = null;
    public static ArrayList<LetterGridSquare> letters = null;
    public static boolean success = false;

    /*
     * Returns an ArrayList of all letters in String match that are not currently solved for in
     * Word w
     */

    public static ArrayList<LetterGridSquare> getNewLetters(Word w, String match) {
        ArrayList<LetterGridSquare> toRet = new ArrayList<LetterGridSquare>();
        for(int i=0; i<w.getLength(); i++) {
            if(!w.getLetters()[i].isSolved()) {
                toRet.add(new LetterGridSquare(w.getLetters()[i].getLetterNumber(), match.substring(i, i+1)));
            }
        }
        return toRet;
    }

    /*
     * Checks to see if all words in the passed ArrayList are solved. If true then the grid is
     * solved
     */

    private static boolean areWeDone(ArrayList<Word> words) {
        boolean toRet = false;
        for(int w=0; w<words.size(); w++) {
            if(!words.get(w).isSolved()) return false;
        }
        return true;
    }

    /*
     * This is the main solving algorithm. It works in a 4 step process:
     *  1) Find a word in our list of words that is not solved
     *  2) Find all words that match that length with letters already solved for. For each match
     *      2a) Make sure the match "makes sense" with regards to duplicate letters
     *      2b) Make sure the match does not already include known letters
     *  3) Set the new letters in our words
     *  4) Make sure each unsolved word has at least one match:
     *      4a) If they don't, revert the new letters and go to 2) with next match
     *      4b) If they do than go to 1)
     *  do until areWeDone returns true.
     */

    public static boolean newSolver(ArrayList<Word> words, ArrayList<LetterGridSquare> letters, Context context) {
        Word w;
        ArrayList<String> matches = null;
        DBHelper dbh = new DBHelper(context);

        String strDisp = "";

        if(areWeDone(words)) {
            Log.i("STEP 5", "WE ARE DONE");
            //displayWords(words);
            //displayLetters(letters);
            return true;
        }
        Log.i("SOLVER", "Starting a newSolver loop");

        // STEP 1: First let's find the first unsolved word:
        for (int i = 0; i < words.size(); i++) {
            if (!words.get(i).isSolved()) {
                w = words.get(i);
                Log.v("STEP 1", "Unsolved word is " + w.getWordAsString());
                matches = getMatches(w, dbh);

                if(matches.size() == 0) {
                    Log.v("STEP 2", "There is no step 2, this unsolved word has no matches. We stuffed up. Faze out.");
                    return false;
                }

                String match = "";
                //Step 2: cycle matches and find the ones that:
                for (int m = 0; m<matches.size(); m++) {
                    match = matches.get(m);

                    //Step 2a) make sure match makes sense:
                    boolean isInsaneMatch = isInsaneMatch(w, match);
                    if(!isInsaneMatch) Log.v("STEP 2", "Found sa sane match "+match);
                    // Step 2b) make sure it doesn't include know letters in it's new letters.
                    boolean matchContainsSolvedLetters = matchContainsKnownLetters(w, match, letters);

                    Log.v("STEP 2", "Analyzing match " + match + " isInsane: " + isInsaneMatch + " - contains solved letters: " + matchContainsSolvedLetters);
                    if(!isInsaneMatch && !matchContainsSolvedLetters) {
                        Log.v("STEP 2", "Sane match containing no new letters is " + match + " for word " + w.getWordAsString());

                        // ok so now we have a sane valid match we can start the 3rd step.
                        //Step 3: get new letters for words.
                        ArrayList<LetterGridSquare> newLetters = getNewLetters(w, match);
                        String logStr = "We have " + newLetters.size() + " new letter(s) to add: ";
                        for (int nl=0; nl<newLetters.size(); nl++) {
                            logStr += newLetters.get(nl).getLetter() + " ";
                        }
                        Log.v("STEP 3", logStr);

                        //set new letters in letterGrid
                        letters = addLettersFromArrayList(letters, newLetters);
                        //update words with new letters
                        words = updateWordsWithNewLetters(words, newLetters);

                        boolean newWordsExist = checkNewWords(words, dbh);
                        Log.v("STEP 4", "New words all have matches? " + newWordsExist);

                        if(!newWordsExist) {
                            //remove the new letters from our solved letters
                            letters = removeLettersFromArrayList(letters, newLetters);
                            //remove new letters from wordList
                            words = removeNewLettersFromWords(words, newLetters);
                        } else {
                            if (!newSolver(words, letters, context)) {
                                //remove the new letters from our solved letters
                                letters = removeLettersFromArrayList(letters, newLetters);
                                //remove new letters from wordList
                                words = removeNewLettersFromWords(words, newLetters);
                            } else {
                                Log.v("STEP 5", "WE ARE DOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOONE");
                                return true;
                            }
                        }
                    }

                    //if we have found no valid matches, we exit with false
                    if(m==matches.size()-1) {
                        Log.v("STEP 2", "We are exiting STEP 2 as no valid sane matches were found for word " + w.getWordAsString());
                        return false;
                    }
                }
            }
        }
        //uh not sure if we'll ever get here.
        return true;
    }

    /*
     * returns true IF the match for a word (w) contains known letters (i.e. belonging to letters)
     * in for spots in our word that are unknown.
     * If that's the case, it means the match is  not a valid one and should be discarded.
     */

    private static boolean matchContainsKnownLetters(Word w, String match, ArrayList<LetterGridSquare> letters) {
        ArrayList<LetterGridSquare> addedLetters = new ArrayList<LetterGridSquare>();
        for (int l = 0; l < w.getLength(); l++) {
            //Log.i("SOLVER", "Checking letter " + w.getLetters()[l] + " solved: " + w.getLetters()[l].isSolved());
            if (!w.getLetters()[l].isSolved()) {
                if(alreadyHasLetter(letters, match.substring(l, l + 1))) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Calls our DBHelper and gets the matches of word size for a given word.
     */

    private static ArrayList<String> getMatches(Word w, DBHelper dbh) {
        ArrayList<String> toRet = new ArrayList<String>();
        try {
            toRet = dbh.getArrayOfMatches(w.getWordAsString());
        } catch (Exception e) {
            Log.e("SOLVER", "ERROR\nERROR\nERROR\nTrying to get matches for word "+w.getWordAsString()+"\n" + e.getMessage());
        }
        return toRet;
    }

    /*
     * Checks that all unsolved words in our word list have at least one match.
     * If not it means that the letters we added do not make sense and that we need to roll back
     * those letters.
     */

    private static boolean checkNewWords(ArrayList<Word> words, DBHelper dbh) {
        String [] matches = null;
        for(int i = 0; i<words.size(); i++) {
            try {
                matches = dbh.matchWord(words.get(i).getWordAsString());
            } catch (Exception e) {
                Log.e("SOLVER", "ERROR\nERROR\nERROR\nTrying to get matches for word "+words.get(i).getWordAsString()+"\n" + e.getMessage());
                return false;
            }

            //check that we have received matches else return false
            if(matches == null) {
                //Log.i("...", words.get(i).getWordAsString() + "has no matches");
                return false;
            }
            if(matches.length == 0) {
                //Log.i("...", words.get(i).getWordAsString() + "has no matches");
                return false;
            }
        }
        return true;
    }

    /*
     * This method checks that a match "makes sense". Essentially it is checking for duplicate
     * letters in the word and if some are found we are checking to see if that is also the case in
     * the match.
     * @todo you can probably get around this entirely by using a regex to do the search for matches
     * in the first place
     */

    private static boolean isInsaneMatch(Word word, String match) {
        boolean toRet = false;
        for(int i=0;i<word.getLetters().length; i++) {
            WordSquare letter = word.getLetters()[i];
            if(!letter.isSolved()) {
                //Log.v("LETTER MATCH", "seeing if " + String.valueOf(letter.getLetterNumber()) + " has duplicates in " + word.getWordAsString() + " letter isSolved: "+letter.isSolved());
                int numToFind = letter.getLetterNumber();
                ArrayList<Integer> dupePositions = new ArrayList<Integer>();
                String strDupes = "(";
                for(int j=i+1; j<word.getLetters().length; j++) {
                    if(word.getLetters()[j].getLetterNumber()==numToFind) {
                        dupePositions.add(new Integer(j));
                        strDupes+=j+" ";
                    }
                }
                strDupes +=")";

                if(dupePositions.size()>0) {
                    Log.v("LETTER MATCH", "Found a duplicate! in word " + word.getWordAsString() + " --> " + letter.getLetterNumber() + " in positions " + i + " and " + dupePositions.get(0) + " at least");
                    //check the match word for same format
                    for(int m = 0; m<dupePositions.size(); m++) {
                        if(!match.substring(i, i+1).equals(match.substring(dupePositions.get(m).intValue(), dupePositions.get(m).intValue()+1))) {
                            Log.v("LETTER MATCH", match + " and " + word.getNumbersAsString() + " dupes at " +strDupes + " a sane match because " + match.substring(i, i+1) + " != " + match.substring(dupePositions.get(m).intValue(), dupePositions.get(m).intValue()+1));
                            return true;
                        }
                    }
                }

            };
        }
        return toRet;
    }

    /*
     * checks to see if a letter belongs to our list of "known" letters
     */

    private static boolean alreadyHasLetter(ArrayList<LetterGridSquare> letters, String matchLetter) {
        //String toDisp = "*\t\tSeeing if " + matchLetter+ " is a new letter or already solved";
        for(int i=0; i<letters.size(); i++) {
            if(letters.get(i).getLetter().equals(matchLetter)) {
                //toDisp += "\nwe seem to have already solved letter " + matchLetter + "we are skipping it";
                return true;
            }
        }
        //Log.i("SOLVER", "*\t\t" +matchLetter+ " is a new letter, w will add it.");
        //toDisp+= "*\t\t\" +matchLetter+  is a new letter, w will add it";
        //Log.i("SOLVER", toDisp);
        return false;
    }

    /*
     * This will take the letters we found in a match and add them to our global letter grid
     */
    private static ArrayList<LetterGridSquare> addLettersFromArrayList(ArrayList<LetterGridSquare> letters, ArrayList<LetterGridSquare> toAdd) {
        //letters.addAll(toAdd);
        for (int l = 0; l<toAdd.size(); l++) {
            LetterGridSquare letterToAdd = toAdd.get(l);
            for(int i = 0; i<letters.size(); i++) {
                if(letters.get(i).getNumber() == letterToAdd.getNumber()) letters.get(i).setLetter(letterToAdd.getLetter());
            }
        }
        return letters;
    }

    /*
     * This will take the letters we found in a match and REMOVE them from our global letter grid
     */
    private static ArrayList<LetterGridSquare> removeLettersFromArrayList(ArrayList<LetterGridSquare> letters, ArrayList<LetterGridSquare> toAdd) {
        for (int l = 0; l<toAdd.size(); l++) {
            LetterGridSquare letterToAdd = toAdd.get(l);
            for(int i = 0; i<letters.size(); i++) {
                if(letters.get(i).getNumber() == letterToAdd.getNumber()) letters.get(i).setLetter("_");
            }
        }
        return letters;
    }


    /*
     * Updates a list of words with new letters we just found
     */
    private static ArrayList<Word> updateWordsWithNewLetters(ArrayList<Word> words, ArrayList<LetterGridSquare> lettersToAdd) {
        for (int t = 0; t < lettersToAdd.size(); t++) {
            //Log.i("SOLVER", " \tLetters to add " +t+" of " + lettersToAdd.size()+": "+lettersToAdd.get(t).getLetter());
        }

        for (int l = 0; l < lettersToAdd.size(); l++) {
            //cycling through new letters
            for (int i = 0; i < words.size(); i++) {
                if (!words.get(i).isSolved()) {
                    Word word = words.get(i);
                    //Log.i("SOLVER", "Updating word " + word.getWordAsString());
                    //if word isnt solved see if it has one of the new letters
                    for (int j = 0; j < word.getLetters().length; j++) {
                        if (word.getLetters()[j].getLetterNumber() == lettersToAdd.get(l).getNumber()) {
                            //Log.i("SOLVER", "Found a letter square to update " + word.getLetters()[j].getLetterNumber());
                            //Log.i("SOLVER", "Found a letter square to update. Updating with " + lettersToAdd.get(l).getLetter());

                            word.getLetters()[j].setLetter(lettersToAdd.get(l).getLetter());
                            word.getLetters()[j].setSolved(true);
                        }
                    }
                    //Log.i("SOLVER", "Updated word " + word.getWordAsString());
                }
            }
        }
        return words;
    }

    /*
     * Updates a list of words by removing letters we had found in a previous iteration
     */
    private static ArrayList<Word> removeNewLettersFromWords(ArrayList<Word> words, ArrayList<LetterGridSquare> lettersToAdd) {
        for (int l = 0; l < lettersToAdd.size(); l++) {
            //cycling through new letters
            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                //see if it has one of the new letters
                for (int j = 0; j < word.getLetters().length; j++) {
                    if (word.getLetters()[j].getLetterNumber() == lettersToAdd.get(l).getNumber()) {
                        word.getLetters()[j].setLetter("_");
                        word.getLetters()[j].setSolved(false);
                    }
                }
            }
        }
        return words;
    }

    /*
     * displays our list of words
     */
    public static void displayWords(ArrayList<Word> words) {
        String toDisp = ".\n"+"***********************************************************************************"+"\n                                      WORDS"+"\n";
        for (int i = 0; i < words.size(); i++) {
            toDisp+=" \t"+words.get(i).getWordAsString()+" solved: " + words.get(i).isSolved() + "\n";
        }
        toDisp+="***********************************************************************************";
        Log.i("SOLVER" , toDisp);
    }

    /*
     * displays our list of letters
     */

    public static void displayLetters(ArrayList<LetterGridSquare> letters) {
        String toDisp = ".\n"+"***********************************************************************************"+"\n                                      LETTERS"+"\n";
        for (int i = 0; i < letters.size(); i++) {
            toDisp+=" \t"+letters.get(i).getNumber() +": "+letters.get(i).getLetter() + "\n";
        }
        toDisp+="***********************************************************************************";

        Log.i("SOLVER" , toDisp);
    }

}
