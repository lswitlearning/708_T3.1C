package com.example.task3_1c;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    TextView progress;
    ProgressBar progressBar;
    TextView question;
    Button choice1Button;
    Button choice2Button;
    Button choice3Button;
    Button choice4Button;
    Button checkNextButton;

    int questionCounter = 0;
    int correctAnswers = 0;

    // Map to associate answer text with corresponding Button objects
    Map<String, Button> answerButtonMap = new HashMap<>();

    // Arrays for questions, choices, and answers
    String[] questions;
    String[] choices;
    String[] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize views
        progress = findViewById(R.id.progressText);
        progressBar = findViewById(R.id.progressBar);
        question = findViewById(R.id.question);
        choice1Button = findViewById(R.id.choice1Button);
        choice2Button = findViewById(R.id.choice2Button);
        choice3Button = findViewById(R.id.choice3Button);
        choice4Button = findViewById(R.id.choice4Button);
        checkNextButton = findViewById(R.id.checkNextButton);

        // Initialize answerButtonMap
        answerButtonMap.put("Choice1", choice1Button);
        answerButtonMap.put("Choice2", choice2Button);
        answerButtonMap.put("Choice3", choice3Button);
        answerButtonMap.put("Choice4", choice4Button);

        // Get questions, choices, and answers from resources
        Resources res = getResources();
        questions = res.getStringArray(R.array.questions);
        choices = res.getStringArray(R.array.choices);
        answers = res.getStringArray(R.array.answers);

        // Set initial progress
        updateProgress(questionCounter + 1);

        // Set initial question and choices
        setQuestionAndChoices(questionCounter);

        // Set click listener for choices
        choice1Button.setOnClickListener(view -> selectChoice(choice1Button));
        choice2Button.setOnClickListener(view -> selectChoice(choice2Button));
        choice3Button.setOnClickListener(view -> selectChoice(choice3Button));
        choice4Button.setOnClickListener(view -> selectChoice(choice4Button));

        // Set click listener for check/next button
        checkNextButton.setOnClickListener(view -> onCheckNextButtonClick());
    }

    // Update progress text and progress bar
    private void updateProgress(int questionsCompleted) {
        progress.setText(questionsCompleted + "/5");
        progressBar.setProgress(questionsCompleted);
    }

    // Set question and choices for the current question
    private void setQuestionAndChoices(int questionNumber) {

        // Reset button background colors
        for (Button button : answerButtonMap.values()) {
            button.setBackgroundResource(android.R.drawable.btn_default);
        }

        question.setText(questions[questionNumber]);

        // Set choices in arrays
        int startIndex = questionNumber * 4;
        choice1Button.setText(choices[startIndex]);
        choice2Button.setText(choices[startIndex + 1]);
        choice3Button.setText(choices[startIndex + 2]);
        choice4Button.setText(choices[startIndex + 3]);
    }

    // Method to handle choice selection
    private void selectChoice(Button selectedChoice) {
        // Enable all choices
        enableAllChoices();

        // Disable selected choice
        selectedChoice.setEnabled(false);

        // Enable check/next button
        checkNextButton.setEnabled(true);
    }

    // Method to handle check/next button click
    private void onCheckNextButtonClick() {
        if (checkNextButton.getText().toString().equals(getString(R.string.check))) {
            // Check the answer
            checkAnswer();

            // Update button text and disable choices
            checkNextButton.setText(getString(R.string.next));
            disableAllChoices();
        } else {
            // If it's not the last question, go to the next question
            if (questionCounter < 4) { // Assuming there are 5 questions (0-based indexing)

                // Increment question counter only when moving to the next question
                questionCounter++;

                // Go to next question
                goToNextQuestion();

                // Enable choices and reset button text
                enableAllChoices();
                checkNextButton.setText(getString(R.string.check));
                // Update progress
                updateProgress(questionCounter + 1);
            } else {
                // Navigate to final page
                goToFinalPage();


            }
        }
    }

   private void goToFinalPage() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("NAME", getIntent().getStringExtra("NAME")); // 将名字传递到结果页面
        intent.putExtra("SCORE", correctAnswers);
        startActivity(intent);
    }


    // Method to check the selected answer
    private void checkAnswer() {
        String selectedAnswer = null;

        // Determine selected answer
        for (Map.Entry<String, Button> entry : answerButtonMap.entrySet()) {
            if (!entry.getValue().isEnabled()) {
                selectedAnswer = entry.getKey();
                break;
            }
        }

        // Compare selected answer with correct answer
        if (selectedAnswer != null && selectedAnswer.equals(answers[questionCounter])) {
            // Correct answer
            Button selectedButton = answerButtonMap.get(selectedAnswer);
            if (selectedButton != null) {
                // Set selected button color to green
                setButtonBackgroundColor(selectedButton, R.color.green);
            }
            correctAnswers++;
        } else {
            // Incorrect answer
            if (selectedAnswer != null) {
                Button selectedButton = answerButtonMap.get(selectedAnswer);
                if (selectedButton != null) {
                    // Set selected button color to red
                    setButtonBackgroundColor(selectedButton, R.color.red);
                }
            }
            // Highlight correct answer
            highlightCorrectAnswer();

        }

        // Disable all choices
        disableAllChoices();



    }

    // Method to highlight correct answer
    private void highlightCorrectAnswer() {
        String correctAnswer = answers[questionCounter];

        Button correctButton = answerButtonMap.get(correctAnswer);
        if (correctButton != null) {
            // Set correct button color to green
            setButtonBackgroundColor(correctButton, R.color.green);
        }
    }



    // Method to enable all choices
    private void enableAllChoices() {
        for (Button button : answerButtonMap.values()) {
            button.setEnabled(true);
        }
    }

    // Method to disable all choices
    private void disableAllChoices() {
        for (Button button : answerButtonMap.values()) {
            button.setEnabled(false);
        }
    }

    // Method to set background color for a button
    private void setButtonBackgroundColor(Button button, int colorResId) {
        button.setBackgroundColor(ContextCompat.getColor(this, colorResId));
    }

    // Method to move to the next question
    private void goToNextQuestion() {
        if (questionCounter < 5) {
            setQuestionAndChoices(questionCounter);
            // Update progress only when moving to the next question
            updateProgress(questionCounter + 1);
        }
    }
}
