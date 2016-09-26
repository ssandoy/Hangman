package com.example.ssandoy.s236305;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    private ImageButton returnButton;

    private Typeface myTypeface;
    private TextView rulesView;
    private TextView rulesHeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSelector.loadLanguage(this);
        setContentView(R.layout.activity_rules);
        returnButton = (ImageButton) findViewById(R.id.returnArrow);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exit = new Intent(RulesActivity.this,StartActivity.class);
                startActivity(exit);
                finish();
            }
        });
        myTypeface = Typeface.createFromAsset(getAssets(), "fonts/chawp.ttf");
        rulesView = (TextView)findViewById(R.id.rulesView);
        rulesView.setTypeface(myTypeface);
        rulesHeadline = (TextView) findViewById(R.id.rulesHeadline);
        rulesHeadline.setTypeface(myTypeface);
    }
}
