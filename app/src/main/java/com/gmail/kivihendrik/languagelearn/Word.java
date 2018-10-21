package com.gmail.kivihendrik.languagelearn;

public class Word {

    int id;
    String foreignWord;
    String translation;
    String wordLanguage;
    String translationLanguage;

    public Word() {

    }

    public Word(int id, String foreignWord, String translation, String wordLanguage, String translationLanguage) {
        this.id = id;
        this.foreignWord = foreignWord;
        this.translation = translation;
        this.wordLanguage = wordLanguage;
        this.translationLanguage = translationLanguage;
    }

    public Word(String foreignWord, String translation, String wordLanguage, String translationLanguage) {
        this.foreignWord = foreignWord;
        this.translation = translation;
        this.wordLanguage = wordLanguage;
        this.translationLanguage = translationLanguage;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getForeignWord() {
        return this.foreignWord;
    }

    public void setForeignWord(String foreignWord) {
        this.foreignWord = foreignWord;
    }

    public String getTranslation() {
        return this.translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getWordLanguage() {
        return this.wordLanguage;
    }

    public void setWordLanguage(String wordLanguage) {
        this.wordLanguage = wordLanguage;
    }

    public String getTranslationLanguage() {
        return this.translationLanguage;
    }

    public void setTranslationLanguage(String translationLanguage) {
        this.translationLanguage = translationLanguage;
    }
}
