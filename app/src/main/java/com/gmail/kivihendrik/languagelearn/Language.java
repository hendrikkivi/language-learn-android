package com.gmail.kivihendrik.languagelearn;

public class Language {
    int id;
    String languageName;

    public Language() {

    }

    public Language(int id, String languageName) {
        this.id = id;
        this.languageName = languageName;
    }

    public Language(String languageName) {
        this.languageName = languageName;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getLanguageName() {
        return this.languageName;
    }

    public void setLanguageName(String languageName) { this.languageName = languageName; }

    @Override
    public String toString() {
        return this.languageName;
    }
}
