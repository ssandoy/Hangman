package com.example.ssandoy.s236305;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {


    private TextView gameTitle;
    private Typeface myTypeface;

    private Button playButton;
    private Button rulesButton;
    private Button langButton;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSelector.loadLanguage(this);
        setContentView(R.layout.activity_start_window);

        gameTitle = (TextView) findViewById(R.id.gametitle);
        playButton = (Button) findViewById(R.id.playButton);
        rulesButton = (Button) findViewById(R.id.rulesButton);
        langButton = (Button) findViewById(R.id.langButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        myTypeface = Typeface.createFromAsset(getAssets(), "fonts/chawp.ttf");
        gameTitle.setTypeface(myTypeface);
        createListeners();
        setTypefaces();
    }



    public void createListeners() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, RulesActivity.class);
                startActivity(intent);
            }
        });
        langButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, LanguageActivity.class);
                startActivity(intent);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }


    public void setTypefaces() {
        playButton.setTypeface(myTypeface);
        rulesButton.setTypeface(myTypeface);
        langButton.setTypeface(myTypeface);
        exitButton.setTypeface(myTypeface);
    }


}
