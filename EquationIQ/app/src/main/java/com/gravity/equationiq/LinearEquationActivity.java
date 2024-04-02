package com.gravity.equationiq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LinearEquationActivity extends AppCompatActivity {

    int score = 0;
    String equation = "", playerAnswer = "";
    int answer;
    int a, b, c;

    TextView eqTextView, scoreTextView;

    Button submitBtn;

    RadioGroup radioGroup;
    RadioButton b1, b2, b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_linear_equation);

        eqTextView = findViewById(R.id.EqTextView1);
        scoreTextView = findViewById(R.id.score1);
        submitBtn = findViewById(R.id.submitBtn_1);
        radioGroup = findViewById(R.id.radio_group1);
        b1 = findViewById(R.id.radio_button1_1);
        b2 = findViewById(R.id.radio_button2_1);
        b3 = findViewById(R.id.radio_button3_1);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_button1_1:
                    playerAnswer = b1.getText().toString();
                    break;
                case R.id.radio_button2_1 :
                    playerAnswer = b2.getText().toString();
                    break;
                case R.id.radio_button3_1:
                    playerAnswer = b3.getText().toString();
                    break;
                default:
                    playerAnswer = "";
            }
        });

        submitBtn.setOnClickListener(View -> {
            if (playerAnswer.equals("")) {
                Intent intent = new Intent(LinearEquationActivity.this, GameOverActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }

            String formattedAnswer = String.valueOf("x = " + (answer));
            if(formattedAnswer.equals(playerAnswer)){
                score++; // increment the score
                setScore(); // set the score to the text view
                loadQuestion(); // load the next question
            }else{
                Intent intent = new Intent(LinearEquationActivity.this, GameOverActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }

            // clear the selection
            radioGroup.clearCheck();
        });

        // load the question
        loadQuestion();
    }

    public void loadQuestion( ){
        // make the equation
        equation = generateLinearEquation();

        // set equation
        setEquation();

        // calculate answer
        answer = calculate();

        // set the options
        setOptions();
    }

    public String generateLinearEquation() {
        // Generate random coefficients for a, b, and c
        a = (int) (Math.random() * 10) + 1;
        b = (int) (Math.random() * 100) + 1;
        c = (int) (Math.random() * 10) + 1;

        // Ensure c is divisible by a to guarantee a whole number solution for x
        while (c % a != 0) {
            c++; // Increment c until it's divisible by a
        }

        // Format the equation string
        return String.format("%dx + %d = %d", a, b, c);
    }

    private void setEquation() {
        eqTextView.setText(equation);
    }

    private int calculate() {
        return (int)((c - b)/a);
    }

    private void setOptions() {
        int randomIndex = (int) ((Math.random() * 3) + 1);
        String mainAnswer = String.valueOf("x = " + answer);
        String subAnswer1 = String.valueOf("x = " + (answer - randomIndex));
        String subAnswer2 = String.valueOf("x = " + (answer + randomIndex));
        if (randomIndex == 1){
            b1.setText(mainAnswer);
            b2.setText(subAnswer1);
            b3.setText(subAnswer2);
        } else if (randomIndex == 2) {
            b1.setText(subAnswer1);
            b2.setText(mainAnswer);
            b3.setText(subAnswer2);
        } else if (randomIndex == 3) {
            b1.setText(subAnswer1);
            b2.setText(subAnswer2);
            b3.setText(mainAnswer);
        }else {
            b1.setText(mainAnswer);
            b2.setText(subAnswer1);
            b3.setText(subAnswer2);
        }
    }

    private void setScore() {
        String scoreTextViewString = "Score: " + score;
        scoreTextView.setText(scoreTextViewString);
    }
}