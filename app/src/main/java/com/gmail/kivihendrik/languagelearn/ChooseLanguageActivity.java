package com.gmail.kivihendrik.languagelearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ChooseLanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        populateDropdowns();
    }

    public void startPractice(View view) {
        Intent intent = new Intent(this, practiceActivity.class);
        Spinner spinnerFromLanguage = (Spinner) findViewById(R.id.spinnerFromLanguage);
        Spinner spinnerToLanguage = (Spinner) findViewById(R.id.spinnerToLanguage);
        if (!isEmpty(spinnerFromLanguage) && !isEmpty(spinnerToLanguage)) {
            String fromLanguage = spinnerFromLanguage.getSelectedItem().toString();
            String toLanguage = spinnerToLanguage.getSelectedItem().toString();
            intent.putExtra("FROM_LANGUAGE", fromLanguage);
            intent.putExtra("TO_LANGUAGE", toLanguage);
            startActivity(intent);
        }
    }
    public boolean isEmpty(Spinner spinner) {
        if (spinner == null || spinner .getSelectedItem() == null) {
            return true;
        }
        return false;
    }
    public void populateDropdowns() {
        Spinner firstSpinner = (Spinner) findViewById(R.id.spinnerFromLanguage);
        Spinner secondSpinner = (Spinner) findViewById(R.id.spinnerToLanguage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstSpinner.setAdapter(adapter);
        secondSpinner.setAdapter(adapter);
    }
}
