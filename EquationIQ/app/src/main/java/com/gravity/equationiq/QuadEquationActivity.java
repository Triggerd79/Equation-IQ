package com.gravity.equationiq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuadEquationActivity extends AppCompatActivity {

    int score = 0;
    String equation = "", playerAnswer = "";
    String answer;
    int a, b, c;

    TextView eqTextView, scoreTextView;

    Button submitBtn;

    RadioGroup radioGroup;
    RadioButton b1, b2, b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_quad_equation);

        eqTextView = findViewById(R.id.EqTextView2);
        scoreTextView = findViewById(R.id.score2);
        submitBtn = findViewById(R.id.submitBtn_2);
        radioGroup = findViewById(R.id.radio_group2);
        b1 = findViewById(R.id.radio_button1_2);
        b2 = findViewById(R.id.radio_button2_2);
        b3 = findViewById(R.id.radio_button3_2);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_button1_2:
                    playerAnswer = b1.getText().toString();
                    break;
                case R.id.radio_button2_2 :
                    playerAnswer = b2.getText().toString();
                    break;
                case R.id.radio_button3_2:
                    playerAnswer = b3.getText().toString();
                    break;
                default:
                    playerAnswer = "";
            }
        });

        submitBtn.setOnClickListener(View -> {
            if (playerAnswer.equals("")) {
                Intent intent = new Intent(QuadEquationActivity.this, GameOverActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }

            String formattedAnswer = String.valueOf(answer);
            if(formattedAnswer.equals(playerAnswer)){
                score++; // increment the score
                setScore(); // set the score to the text view
                loadQuestion(); // load the next question
            }else{
                Intent intent = new Intent(QuadEquationActivity.this, GameOverActivity.class);
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
        equation = generateQuadraticEquation();

        // set equation
        setEquation();

        // calculate roots
        double[] roots = calculate();

        // set the answer
        setAnswer(roots);

        // set the options
        setOptions(roots);
    }

    private void setAnswer(double[] roots) {
        answer = "Roots: " + roots[0] + " , " + roots[1];
    }

    private String generateQuadraticEquation() {
        // Generate random coefficients for a, b, and c
        a = (int) (Math.random() * 10) + 1;
        b = (int) (Math.random() * 10) + 1;

        // Ensure positive discriminant by squaring b
        int c = b * b;

        // Format the equation string
        return String.format(" %dx\u00B2 + %dx + %d = 0", a, b, c);
    }

    private void setEquation() {
        eqTextView.setText(equation);
    }

    private double[] calculate() {
        // Calculate the discriminant (b^2 - 4ac)
        double discriminant = b * b - 4 * a * c;

        // Determine the roots based on the discriminant
        double[] roots = new double[2];
        if (discriminant > 0) {
            // Two real and distinct roots
            roots[0] = (-b + Math.sqrt(discriminant)) / (2 * a);
            roots[1] = (-b - Math.sqrt(discriminant)) / (2 * a);
        } else if (discriminant == 0) {
            // Two real and equal roots
            roots[0] = roots[1] = (double)(-b / (2 * a));
        }
        return roots;
    }

    private void setOptions(double[] roots) {
        int randomIndex = (int) ((Math.random() * 3) + 1);

        String mainAnswer = "Roots: " + roots[0] + " , " + roots[1];
        String subAnswer1 = "Roots: " + (roots[0] - 1) + " , " + (roots[1] + randomIndex);
        String subAnswer2 = "Roots: " + (roots[0] + randomIndex) + " , " + (roots[1] -1);
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