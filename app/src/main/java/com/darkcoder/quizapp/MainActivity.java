package com.darkcoder.quizapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionTextView, qnTextView;
    private Button[] optionButtons;
    private int score, totalQuestions, currentQuestionIndex;
    private String selectedAnswer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView totalQuestionTextView = findViewById(R.id.topView);
        questionTextView = findViewById(R.id.questionText);
        qnTextView = findViewById(R.id.qN);
        optionButtons = new Button[] {findViewById(R.id.option_a), findViewById(R.id.option_b), findViewById(R.id.option_c), findViewById(R.id.option_d)};
        Button submitButton = findViewById(R.id.submitButton);
        for (Button button : optionButtons) {
            button.setOnClickListener(this);
        }
        submitButton.setOnClickListener(this);
        totalQuestions = QuizActivity.question.length;
        totalQuestionTextView.setText("Total Questions: " + totalQuestions);

        loadNewQuestion();
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestions) {
            finishQuiz();
            return;
        }
        questionTextView.setText(QuizActivity.question[currentQuestionIndex]);
        qnTextView.setText(QuizActivity.qN[currentQuestionIndex]);
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(QuizActivity.choices[currentQuestionIndex][i]);
        }
        selectedAnswer = "";
    }

    private void finishQuiz() {
        String passStatus = score >= totalQuestions * 0.6 ? "Congratulations! You passed" : "Unfortunately, you did not passed";
        new AlertDialog.Builder(this).setTitle(passStatus).setMessage("Your score is " + score + " out of " + totalQuestions).setPositiveButton("Restart", (dialog, i) -> restartQuiz()).setCancelable(false).show();
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        // Reset answer buttons
        for (Button button : optionButtons) {
            button.setBackgroundColor(Color.WHITE);
        }
        if (view.getId() == R.id.submitButton) {
            if (!selectedAnswer.isEmpty()) {
                if (selectedAnswer.equals(QuizActivity.correctAnswers[currentQuestionIndex])) {
                    score++;
                }
                currentQuestionIndex++;
                loadNewQuestion();
            }
        } else {
            Button clickedButton = (Button) view;
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
        }
    }
}