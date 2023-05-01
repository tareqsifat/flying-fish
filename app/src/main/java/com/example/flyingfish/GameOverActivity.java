package com.example.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity
{
    private Button gameOverButton;
    private TextView Score;
    private String StringScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        StringScore = getIntent().getExtras().get("score").toString();

        gameOverButton = (Button) findViewById(R.id.gameOverButton);
        Score = (TextView) findViewById(R.id.Totalscore);
        gameOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mainintent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(Mainintent);
            }
        });

        Score.setText("score = "+ StringScore);
    }
}
