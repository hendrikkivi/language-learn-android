package com.gmail.kivihendrik.languagelearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class practiceActivity extends AppCompatActivity {

    private Word currentWord = null;
    private String fromLanguage = null;
    private String toLanguage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        Intent intent = getIntent();
        fromLanguage = intent.getStringExtra("FROM_LANGUAGE");
        toLanguage = intent.getStringExtra("TO_LANGUAGE");

        showLanguageInfo();
        showWord();
    }

    public void showLanguageInfo() {
        //TextView textView = (TextView) findViewById(R.id.tvLanguageInfo);
        //textView.setText(fromLanguage + " -> " + toLanguage);
        EditText etAnswer = (EditText) findViewById(R.id.etAnswer);
        etAnswer.setHint(getResources().getString(R.string.editAnswer) + " (" + toLanguage + ")");
    }
    public void showWord() {
        currentWord = getWord();
        if (currentWord == null) {
            finish();
            return;
        }
        String question = getQuestion(currentWord);
        TextView textView = (TextView) findViewById(R.id.tvQuestion);
        textView.setText(question + " (" + fromLanguage + ")");
    }
    public String getQuestion(Word word) {
        String question = null;
        if (currentWord.getWordLanguage().equals(fromLanguage)) {
            question = currentWord.getForeignWord();
        } else {
            question = currentWord.getTranslation();
        }
        return question;
    }
    public Word getWord() {
        List<Word> words = getWordsByLanguage(fromLanguage, toLanguage);
        if (words == null || words.isEmpty()) {
            return null;
        }
        int random = getRandomNumber(words.size());
        Word randomWord = words.get(random);
        return randomWord;
    }
    public int getRandomNumber(int totalNumbers) {
        Random rand = new Random();
        int random = rand.nextInt(totalNumbers);
        return random;
    }
    public List<Word> getWordsByLanguage(String fromLanguage, String toLanguage) {
        DatabaseHandler db = new DatabaseHandler(this);
        List<Word> words = db.getAllWords();
        db.close();
        List<Word> wordsInLanguage = new ArrayList<Word>();
        for (Word word : words) {
            if (isCorrectLanguage(word, fromLanguage, toLanguage)) {
                wordsInLanguage.add(word);
            }
        }
        return wordsInLanguage;
    }
    public boolean isCorrectLanguage(Word word, String lang1, String lang2) {
        String wordLanguage = word.getWordLanguage();
        String translationLanguage = word.getTranslationLanguage();
        if (wordLanguage.equals(lang1) && translationLanguage.equals(lang2) ||
                wordLanguage.equals(lang2) && translationLanguage.equals(lang1)) {
            return true;
        }
        return false;
    }

    public void sendAnswer(View view) {
        EditText etAnswer = (EditText) findViewById(R.id.etAnswer);
        String answer = etAnswer.getText().toString();
        TextView textView = (TextView) findViewById(R.id.tvFeedback);
        String feedBack = getFeedBack(answer);
        textView.setText(feedBack);
        etAnswer.setText(null);
        showWord();

    }
    public String getFeedBack(String answer) {
        String feedBack = answer + " ";
        if (isCorrectAnswer(answer)) {
            feedBack += getResources().getString(R.string.correctTranslation);
        } else {
            feedBack += getResources().getString(R.string.wrongTranslation)
                    + " " + getCorrectAnswer();
        }
        return feedBack;
    }
    public boolean isCorrectAnswer(String answer) {
        if (answer.equals(getCorrectAnswer())) {
            return true;
        }
        return false;
    }
    public String getCorrectAnswer() {
        String correctAnswer = null;
        if (toLanguage.equals(currentWord.getWordLanguage())) {
            correctAnswer = currentWord.getForeignWord();
        } else if (toLanguage.equals(currentWord.getTranslationLanguage())){
            correctAnswer = currentWord.getTranslation();
        } else {
            //Log.w("Answer", "No correct answer");
        }
        return correctAnswer;
    }

}
