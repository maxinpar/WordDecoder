package boucoiran.fr.worddecoder.DBUtils;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import boucoiran.fr.worddecoder.R;

public class DBHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 10;
    private final static String DATABASE_NAME = "WORDDECODER.db";
    private final static String TEXT_TYPE = " Text";
    private final static String TAG = "DBHelper";
    private Context context;

    /*
     * Variables below used to check that our word list imports have worked as expected.
     * variables are the size of each list, eg l3 = how many 3words-letter words we have in the list
     */

    private final static long l3 = 2130;
    private final static long l4 = 7186;
    private final static long l5 = 15918;
    private final static long l6 = 29874;
    private final static long l7 = 41998;
    private final static long l8 = 51627;
    private final static long l9 = 53402;
    private final static long l10 = 45872;
    private final static long l11= 37539;
    private final static long l12 = 29126;
    private final static long l13 = 20944;
    private final static long l14 = 14148;
    private final static long l15 = 8846;
    private final static long l16 = 5182;
    private final static long l17 = 2967;
    private final static long l18 = 1471;
    private final static long l19 = 760;
    private final static long l20 = 359;


    /**********************************************************************************************
     * DROP TABLE queries are below.                                                              *
     * They use constants fro table and column names from the GolfPracticeContract class          *
     **********************************************************************************************/

    private static final String SQL_DELETE_WORDS3_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words3Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS4_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words4Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS5_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words5Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS6_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words6Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS7_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words7Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS8_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words8Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS9_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words9Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS10_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words10Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS11_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words11Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS12_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words12Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS13_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words13Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS14_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words14Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS15_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words15Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS16_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words16Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS17_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words17Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS18_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words18Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS19_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words19Entry.TABLE_NAME;
    private static final String SQL_DELETE_WORDS20_ENTRY = "DROP TABLE IF EXISTS " + WordsContract.Words20Entry.TABLE_NAME;

    /**********************************************************************************************
     * CREATE TABLE queries are below.                                                            *
     * They use constants from table and column names from the GolfPracticeContract class          *
     **********************************************************************************************/

    private final static String SQL_CREATE_WORDS3_TABLE = "CREATE TABLE " + WordsContract.Words3Entry.TABLE_NAME + " ("
            + WordsContract.Words3Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words3Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS4_TABLE = "CREATE TABLE " + WordsContract.Words4Entry.TABLE_NAME + " ("
            + WordsContract.Words4Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words4Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS5_TABLE = "CREATE TABLE " + WordsContract.Words5Entry.TABLE_NAME + " ("
            + WordsContract.Words5Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words5Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS6_TABLE = "CREATE TABLE " + WordsContract.Words6Entry.TABLE_NAME + " ("
            + WordsContract.Words6Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words6Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS7_TABLE = "CREATE TABLE " + WordsContract.Words7Entry.TABLE_NAME + " ("
            + WordsContract.Words7Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words7Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS8_TABLE = "CREATE TABLE " + WordsContract.Words8Entry.TABLE_NAME + " ("
            + WordsContract.Words8Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words8Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS9_TABLE = "CREATE TABLE " + WordsContract.Words9Entry.TABLE_NAME + " ("
            + WordsContract.Words9Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words9Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS10_TABLE = "CREATE TABLE " + WordsContract.Words10Entry.TABLE_NAME + " ("
            + WordsContract.Words10Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words10Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS11_TABLE = "CREATE TABLE " + WordsContract.Words11Entry.TABLE_NAME + " ("
            + WordsContract.Words11Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words11Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS12_TABLE = "CREATE TABLE " + WordsContract.Words12Entry.TABLE_NAME + " ("
            + WordsContract.Words12Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words12Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS13_TABLE = "CREATE TABLE " + WordsContract.Words13Entry.TABLE_NAME + " ("
            + WordsContract.Words13Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words13Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS14_TABLE = "CREATE TABLE " + WordsContract.Words14Entry.TABLE_NAME + " ("
            + WordsContract.Words14Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words14Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS15_TABLE = "CREATE TABLE " + WordsContract.Words15Entry.TABLE_NAME + " ("
            + WordsContract.Words15Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words15Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS16_TABLE = "CREATE TABLE " + WordsContract.Words16Entry.TABLE_NAME + " ("
            + WordsContract.Words16Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words16Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS17_TABLE = "CREATE TABLE " + WordsContract.Words17Entry.TABLE_NAME + " ("
            + WordsContract.Words17Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words17Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS18_TABLE = "CREATE TABLE " + WordsContract.Words18Entry.TABLE_NAME + " ("
            + WordsContract.Words18Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words18Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS19_TABLE = "CREATE TABLE " + WordsContract.Words19Entry.TABLE_NAME + " ("
            + WordsContract.Words19Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words19Entry.COLUMN_WORD + TEXT_TYPE + ")";
    private final static String SQL_CREATE_WORDS20_TABLE = "CREATE TABLE " + WordsContract.Words20Entry.TABLE_NAME + " ("
            + WordsContract.Words20Entry._ID + " INTEGER PRIMARY KEY, "
            + WordsContract.Words20Entry.COLUMN_WORD + TEXT_TYPE + ")";
    


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        checkWordLists();
    }

    private void checkWordLists() {
        //Log.i(TAG, "Checking Words List");
        SQLiteDatabase db = this.getWritableDatabase();
        //Log.i(TAG, "Checking data integrity: 3-letter words, expectig "+l3 + ". Count(*) gives us: "+DatabaseUtils.queryNumEntries(db, WordsContract.Words3Entry.TABLE_NAME,""));
        if (l3!=DatabaseUtils.queryNumEntries(db, WordsContract.Words3Entry.TABLE_NAME,"")) importWordList("3", R.raw.words3);
        if (l4!=DatabaseUtils.queryNumEntries(db, WordsContract.Words4Entry.TABLE_NAME,"")) importWordList("4", R.raw.words4);
        if (l5!=DatabaseUtils.queryNumEntries(db, WordsContract.Words5Entry.TABLE_NAME,"")) importWordList("5", R.raw.words5);
        if (l6!=DatabaseUtils.queryNumEntries(db, WordsContract.Words6Entry.TABLE_NAME,"")) importWordList("6", R.raw.words6);
        if (l7!=DatabaseUtils.queryNumEntries(db, WordsContract.Words7Entry.TABLE_NAME,"")) importWordList("7", R.raw.words7);
        if (l8!=DatabaseUtils.queryNumEntries(db, WordsContract.Words8Entry.TABLE_NAME,"")) importWordList("8", R.raw.words8);
        if (l9!=DatabaseUtils.queryNumEntries(db, WordsContract.Words9Entry.TABLE_NAME,"")) importWordList("9", R.raw.words9);
        if (l10!=DatabaseUtils.queryNumEntries(db, WordsContract.Words10Entry.TABLE_NAME,"")) importWordList("10", R.raw.words10);
        if (l11!=DatabaseUtils.queryNumEntries(db, WordsContract.Words11Entry.TABLE_NAME,"")) importWordList("11", R.raw.words11);
        if (l12!=DatabaseUtils.queryNumEntries(db, WordsContract.Words12Entry.TABLE_NAME,"")) importWordList("12", R.raw.words12);
        if (l13!=DatabaseUtils.queryNumEntries(db, WordsContract.Words13Entry.TABLE_NAME,"")) importWordList("13", R.raw.words13);
        if (l14!=DatabaseUtils.queryNumEntries(db, WordsContract.Words14Entry.TABLE_NAME,"")) importWordList("14", R.raw.words14);
        if (l15!=DatabaseUtils.queryNumEntries(db, WordsContract.Words15Entry.TABLE_NAME,"")) importWordList("15", R.raw.words15);
        if (l16!=DatabaseUtils.queryNumEntries(db, WordsContract.Words16Entry.TABLE_NAME,"")) importWordList("16", R.raw.words16);
        if (l17!=DatabaseUtils.queryNumEntries(db, WordsContract.Words17Entry.TABLE_NAME,"")) importWordList("17", R.raw.words17);
        if (l18!=DatabaseUtils.queryNumEntries(db, WordsContract.Words18Entry.TABLE_NAME,"")) importWordList("18", R.raw.words18);
        if (l19!=DatabaseUtils.queryNumEntries(db, WordsContract.Words19Entry.TABLE_NAME,"")) importWordList("19", R.raw.words19);
        if (l20!=DatabaseUtils.queryNumEntries(db, WordsContract.Words20Entry.TABLE_NAME,"")) importWordList("20", R.raw.words20);
    }

    private void importWordList(String l, int fileID) {
        Log.i(TAG, "Importing Words List " + l);
        String delQuery = "DROP TABLE IF EXISTS words_" + l;
        String createQuery = "CREATE TABLE words_" + l + " ("
                + WordsContract.Words3Entry._ID + " INTEGER PRIMARY KEY, "
                + "word" + TEXT_TYPE + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(delQuery);
        db.execSQL(createQuery);
        importFromFile(l, fileID);
    }

    private void importFromFile(String l, int fileID) {

        Log.i(TAG, "Importing Words List " + l);
        SQLiteDatabase db = this.getWritableDatabase();
        final Resources resources = context.getResources();

        InputStream inputStream = resources.openRawResource(fileID);
        try {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        String tableName = "words_"+l;
        String columns = WordsContract.Words5Entry.COLUMN_WORD;
        String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
        String str2 = ");";


        db.beginTransaction();

            int i = 0;
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                //Log.i(TAG, "word to import: "+str[0]);

                sb.append("'" + str[0] + "'");
                sb.append(str2);
                //Log.i("DBHelper", "our insert string: " + sb.toString());
                db.execSQL(sb.toString());
                i++;
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            Log.i(TAG, "Completed " + i + " imports for table " + l);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        /* #TODO update this to actually update and not replace */
        db.execSQL(SQL_DELETE_WORDS3_ENTRY);
        db.execSQL(SQL_DELETE_WORDS4_ENTRY);
        db.execSQL(SQL_DELETE_WORDS5_ENTRY);
        db.execSQL(SQL_DELETE_WORDS6_ENTRY);
        db.execSQL(SQL_DELETE_WORDS7_ENTRY);
        db.execSQL(SQL_DELETE_WORDS8_ENTRY);
        db.execSQL(SQL_DELETE_WORDS9_ENTRY);
        db.execSQL(SQL_DELETE_WORDS10_ENTRY);
        db.execSQL(SQL_DELETE_WORDS11_ENTRY);
        db.execSQL(SQL_DELETE_WORDS12_ENTRY);
        db.execSQL(SQL_DELETE_WORDS13_ENTRY);
        db.execSQL(SQL_DELETE_WORDS14_ENTRY);
        db.execSQL(SQL_DELETE_WORDS15_ENTRY);
        db.execSQL(SQL_DELETE_WORDS16_ENTRY);
        db.execSQL(SQL_DELETE_WORDS17_ENTRY);
        db.execSQL(SQL_DELETE_WORDS18_ENTRY);
        db.execSQL(SQL_DELETE_WORDS19_ENTRY);
        db.execSQL(SQL_DELETE_WORDS20_ENTRY);

        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBHelper", "onCreate");
        db.execSQL(SQL_CREATE_WORDS3_TABLE);
        db.execSQL(SQL_CREATE_WORDS4_TABLE);
        db.execSQL(SQL_CREATE_WORDS5_TABLE);
        db.execSQL(SQL_CREATE_WORDS6_TABLE);
        db.execSQL(SQL_CREATE_WORDS7_TABLE);
        db.execSQL(SQL_CREATE_WORDS8_TABLE);
        db.execSQL(SQL_CREATE_WORDS9_TABLE);
        db.execSQL(SQL_CREATE_WORDS10_TABLE);
        db.execSQL(SQL_CREATE_WORDS11_TABLE);
        db.execSQL(SQL_CREATE_WORDS12_TABLE);
        db.execSQL(SQL_CREATE_WORDS13_TABLE);
        db.execSQL(SQL_CREATE_WORDS14_TABLE);
        db.execSQL(SQL_CREATE_WORDS15_TABLE);
        db.execSQL(SQL_CREATE_WORDS16_TABLE);
        db.execSQL(SQL_CREATE_WORDS17_TABLE);
        db.execSQL(SQL_CREATE_WORDS18_TABLE);
        db.execSQL(SQL_CREATE_WORDS19_TABLE);
        db.execSQL(SQL_CREATE_WORDS20_TABLE);
    }


    public void populateWords2(Context context) throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
        final Resources resources = context.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.words5);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        String tableName = WordsContract.Words5Entry.TABLE_NAME;
        String columns = WordsContract.Words5Entry.COLUMN_WORD;
        String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
        String str2 = ");";


        db.beginTransaction();
        while ((line = buffer.readLine()) != null) {
            StringBuilder sb = new StringBuilder(str1);
            String[] str = line.split(",");
            sb.append("'" + str[0] + "'");
            sb.append(str2);
            //Log.i("DBHelper", "our insert string: " + sb.toString());

            db.execSQL(sb.toString());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }







    public String[] matchWord(String word) throws Exception{
        //Log.i("DB Helper", "Ok we are trying to match a word");
        ArrayList<String> toRet = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM words_" + word.length() + " WHERE word like '" + word+"'";

        //Log.i("DB Helper", "query:" + selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
          //  Log.i("DB Helper", "cursor NOT null :)");
            while(c.moveToNext()) {
                toRet.add(c.getString(c.getColumnIndex("word")));
            }
            c.close();
        } else {
            //Log.i("DB Helper", "cursor null :(");

            throw new Exception("Could not run word match query for word: " + word);
        }

        return toRet.toArray(new String[toRet.size()]);
    }

    public ArrayList<String> getArrayOfMatches(String word) throws Exception{
        //Log.i("DB Helper", "Ok we are trying to match a word");
        ArrayList<String> toRet = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM words_" + word.length() + " WHERE word like '" + word+"'";

        //Log.i("DB Helper", "query:" + selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            //  Log.i("DB Helper", "cursor NOT null :)");
            while(c.moveToNext()) {
                toRet.add(c.getString(c.getColumnIndex("word")));
            }
            c.close();
        } else {
            //Log.i("DB Helper", "cursor null :(");

            throw new Exception("Could not run word match query for word: " + word);
        }

        return toRet;
    }
}

