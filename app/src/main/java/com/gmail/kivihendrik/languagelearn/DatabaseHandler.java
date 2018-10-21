package com.gmail.kivihendrik.languagelearn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "wordsDatabase";

    // table names
    private static final String TABLE_WORDS = "words";

    // WORDS table column names
    private static final String KEY_ID = "id";
    private static final String KEY_FOREIGN = "foreignWord";
    private static final String KEY_TRANSLATION = "translation";
    private static final String KEY_WORD_LANGUAGE = "wordLanguage";
    private static final String KEY_TRANSLATION_LANGUAGE = "translationLanguage";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table statements
    private static final String CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_WORDS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_FOREIGN + " TEXT,"
            + KEY_TRANSLATION + " TEXT,"
            + KEY_WORD_LANGUAGE + " TEXT,"
            + KEY_TRANSLATION_LANGUAGE + " TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        onCreate(db);
    }

    // Words CRUD
    public void addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FOREIGN, word.getForeignWord());
        values.put(KEY_TRANSLATION, word.getTranslation());
        values.put(KEY_WORD_LANGUAGE, word.getWordLanguage());
        values.put(KEY_TRANSLATION_LANGUAGE, word.getTranslationLanguage());

        db.insert(TABLE_WORDS, null, values);
        db.close();
    }

    public Word getWord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WORDS, new String[] { KEY_ID,
                        KEY_FOREIGN, KEY_TRANSLATION, KEY_WORD_LANGUAGE,
                        KEY_TRANSLATION_LANGUAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Word word = new Word(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return word;
    }

    public List<Word> getAllWords() {
        List<Word> wordList = new ArrayList<Word>();

        String selectQuery = "SELECT  * FROM " + TABLE_WORDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setID(Integer.parseInt(cursor.getString(0)));
                word.setForeignWord(cursor.getString(1));
                word.setTranslation(cursor.getString(2));
                word.setWordLanguage(cursor.getString(3));
                word.setTranslationLanguage(cursor.getString(4));
                wordList.add(word);
            } while (cursor.moveToNext());
        }
        db.close();
        return wordList;
    }

    public int getWordsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WORDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        db.close();
        return count;
    }

    public int updateWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FOREIGN, word.getForeignWord());
        values.put(KEY_TRANSLATION, word.getForeignWord());
        values.put(KEY_WORD_LANGUAGE, word.getWordLanguage());
        values.put(KEY_TRANSLATION_LANGUAGE, word.getTranslationLanguage());

        // updating row
        return db.update(TABLE_WORDS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(word.getID()) });
    }

    public void deleteWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORDS, KEY_ID + " = ?",
                new String[] { String.valueOf(word.getID()) });
        db.close();
    }
    public void deleteAllWords() {
        List<Word> words = getAllWords();
        for (Word word : words) {
            deleteWord(word);
        }
    }
}
