package com.gmail.kivihendrik.languagelearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateDropdowns();
    }

    public void saveWord(View view) {
        EditText etForeignWord = (EditText)
                findViewById(R.id.etForeignWord);
        EditText etTranslation = (EditText)
                findViewById(R.id.etTranslation);
        Spinner spinnerWordLanguage = (Spinner)
                findViewById(R.id.spinnerWordLanguages);
        Spinner spinnerTranslationLanguage = (Spinner)
                findViewById(R.id.spinnerTranslationLanguages);
        String foreignWord = etForeignWord.getText().toString();
        String translation = etTranslation.getText().toString();
        String wordLanguage = spinnerWordLanguage.getSelectedItem().toString();
        String translationLanguage = spinnerTranslationLanguage.getSelectedItem().toString();

        if (isValidWord(etForeignWord) && isValidWord(etTranslation)) {
            Word newWord = new Word(foreignWord, translation, wordLanguage, translationLanguage);
            if (wordExists(newWord)) {
                Toast.makeText(this, R.string.errorExists, Toast.LENGTH_SHORT).show();
            } else {
                DatabaseHandler db = new DatabaseHandler(this);
                db.addWord(newWord);
                db.close();
                Toast.makeText(this, R.string.toastInsert, Toast.LENGTH_SHORT).show();
                etForeignWord.setText(null);
                etTranslation.setText(null);
                etForeignWord.requestFocus();
            }
        }
    }

    public boolean isValidWord(EditText editText) {
        String enteredText = editText.getText().toString();
        if (enteredText.equals("")) {
            editText.setError(getResources().getString(R.string.errorEmpty));
            return false;
        }
        return true;
    }

    public boolean wordExists(Word enteredWord) {
        List<Word> words = getWords();
        for (Word word : words) {
            // if same language and same words
            if (word.getWordLanguage().equals(enteredWord.getWordLanguage()) &&
                    word.getTranslationLanguage().equals(enteredWord.getTranslationLanguage())) {
                if (word.getForeignWord().equals(enteredWord.getForeignWord()) &&
                        word.getTranslation().equals(enteredWord.getTranslation())) {
                    return true;
                }
            } else if (word.getWordLanguage().equals(enteredWord.getTranslationLanguage()) &&
                    word.getTranslationLanguage().equals(enteredWord.getWordLanguage())) {
                if (word.getForeignWord().equals(enteredWord.getTranslation()) &&
                        word.getTranslation().equals(enteredWord.getForeignWord())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Word> getWords() {
        DatabaseHandler db = new DatabaseHandler(this);
        List<Word> words = db.getAllWords();
        db.close();
        return words;
    }

    public void choosePracticeLanguage(View view) {
        Intent intent = new Intent(this, ChooseLanguageActivity.class);
        startActivity(intent);
    }

    public void showWords(View view) {
        Intent intent = new Intent(this, ShowWordsActivity.class);
        startActivity(intent);
    }

    public void populateDropdowns() {
        Spinner firstSpinner = (Spinner) findViewById(R.id.spinnerWordLanguages);
        Spinner secondSpinner = (Spinner) findViewById(R.id.spinnerTranslationLanguages);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstSpinner.setAdapter(adapter);
        secondSpinner.setAdapter(adapter);
    }

}
