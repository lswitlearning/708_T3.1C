package com.example.task3_1c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import java.text.MessageFormat;


public class ResultActivity extends AppCompatActivity {

    TextView congratulationsText;
    TextView finalScore;
    Button retakeButton;
    Button finishButton;

    int correctAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String name = getIntent().getStringExtra("NAME");

        // Set user's name and correct answers count
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("NAME");
            correctAnswers = extras.getInt("SCORE");
        }

        // Initialize views
        congratulationsText = findViewById(R.id.textView4);
        finalScore = findViewById(R.id.finalScore);
        retakeButton = findViewById(R.id.retake);
        finishButton = findViewById(R.id.finish);

        // Set congratulations message
        if (name != null) {
            congratulationsText.setText(MessageFormat.format("Congratulations {0}!", name));
        } else {
            congratulationsText.setText("Congratulations!");
        }

        // Set final score
        finalScore.setText(MessageFormat.format("{0}/5", correctAnswers));

        // Set click listener for retake button
        retakeButton.setOnClickListener(view -> openMainActivity());

        // Set click listener for finish button
        finishButton.setOnClickListener(view -> finishAffinity());
    }

    // Method to open main activity
    public void openMainActivity() {
        // intent name back to MainActivity
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        intent.putExtra("NAME", getIntent().getStringExtra("NAME"));
        startActivity(intent);
        finish();

    }
}

