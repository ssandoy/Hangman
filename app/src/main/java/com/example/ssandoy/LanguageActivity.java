package com.example.ssandoy.s236305;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LanguageActivity extends AppCompatActivity {

    private TextView langText;

    private Typeface myTypeface;

    private ImageButton noButton;
    private ImageButton enButton;
    private ImageButton returnArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSelector.loadLanguage(this);
        setContentView(R.layout.activity_language);

        noButton = (ImageButton) findViewById(R.id.noButton);
        enButton = (ImageButton) findViewById(R.id.enButton);
        returnArrow = (ImageButton) findViewById(R.id.returnArrow);
        langText = (TextView) findViewById(R.id.langText);
        createListeners();

        myTypeface = Typeface.createFromAsset(getAssets(), "fonts/chawp.ttf");
        langText.setTypeface(myTypeface);

    }

    public void createListeners() {
        enButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("en");

            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("no");
            }
        });

        returnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exit = new Intent(LanguageActivity.this,StartActivity.class);
                startActivity(exit);
                finish();
            }
        });
    }

    public void setLanguage(String lang) {

        LanguageSelector.changeLanguage(this, lang);
        Intent refresh = new Intent(this, StartActivity.class);
        startActivity(refresh);
        finish();
    }


}
