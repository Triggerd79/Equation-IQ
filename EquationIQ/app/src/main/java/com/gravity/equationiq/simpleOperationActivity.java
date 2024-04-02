package com.gravity.equationiq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class simpleOperationActivity extends AppCompatActivity {

    int score = 0;
    String equation = "", playerAnswer = "";
    int n1, n2;
    float answer;

    TextView eqTextView, scoreTextView;
    Button submitBtn;
    RadioGroup radioGroup;
    RadioButton b1, b2, b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_simple_operation);

        eqTextView = findViewById(R.id.EqTextView);
        scoreTextView = findViewById(R.id.score);
        submitBtn = findViewById(R.id.submitBtn);
        radioGroup = findViewById(R.id.radio_group);
        b1 = findViewById(R.id.radio_button1);
        b2 = findViewById(R.id.radio_button2);
        b3 = findViewById(R.id.radio_button3);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_button1:
                    playerAnswer = b1.getText().toString();
                    break;
                case R.id.radio_button2 :
                    playerAnswer = b2.getText().toString();
                    break;
                case R.id.radio_button3:
                    playerAnswer = b3.getText().toString();
                    break;
                default:
                    playerAnswer = "";
            }
        });

        submitBtn.setOnClickListener(View -> {
            if (playerAnswer.equals("")) {
                Intent intent = new Intent(simpleOperationActivity.this, GameOverActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }

            String formattedAnswer = removeTrailingZeros(answer);
            if(formattedAnswer.equals(playerAnswer)){
                score++; // increment the score
                setScore(); // set the score to the text view
                loadQuestion(); // load the next question
            }else{
                Intent intent = new Intent(simpleOperationActivity.this, GameOverActivity.class);
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
        // get no. of digits
        int noOfDigits = getNoOfDigits();

        // get two random number
        n1 = generateRandomNumber(noOfDigits);
        n2 = generateRandomNumber(noOfDigits);

        // get the operator
        String operator = getRandomOperator();

        // make the equation
        equation = " " + Math.max(n1, n2) + " " + operator + " " + Math.min(n1, n2) + "";

        // set equation
        setEquation();

        // calculate answer
        answer = calculate(operator);

        // set the options
        setOptions();
    }

    private int getNoOfDigits() {
        return ((score / 10) + 1);
    }

    public int generateRandomNumber(int numDigits) {
        // Handle invalid input (negative or zero digits)
        if (numDigits <= 0) {
            numDigits = 1;
        }

        // Set the upper bound for the random number
        int upperBound = (int) Math.pow(10, numDigits) - 1;
        // Generate and return a random number within the upper bound
        return (int) (Math.random() * upperBound) + 1;
    }

    public String getRandomOperator() {
        // Define the operators as an array of strings
        String[] operators = {"+", "-", "*", "/"};

        // Generate a random integer between 0 and the length of the operators array minus 1
        int randomIndex = (int) (Math.random() * operators.length);

        // Select the operator at the random index and return it
        return operators[randomIndex];
    }

    private void setEquation() {
        eqTextView.setText(equation);
    }

    private float calculate(String operator) {
        switch (operator) {
            case "+":
                return (n1 + n2);
            case "-":
                return Math.max(n1, n2) - Math.min(n1, n2);
            case "*":
                return Math.max(n1, n2) * Math.min(n1, n2);
            case "/":
                float ans = (float)(Math.max((float)n1, (float)n2) / Math.min((float)n1, (float)n2));
                int temp  =  (int)(ans * 1000);
                ans = (float) (temp/1000.0);
                return ans;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private void setOptions() {
        int randomIndex = (int) ((Math.random() * 3) + 1);
        String mainAnswer = removeTrailingZeros(answer);
        String subAnswer1 = removeTrailingZeros((answer - randomIndex));
        String subAnswer2 = removeTrailingZeros((answer + randomIndex));
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

    private static String removeTrailingZeros(float number) {
        String formattedNumber = String.valueOf(number);
        // Check if the number contains a decimal point
        if (formattedNumber.contains(".")) {
            // Remove trailing zeros after the decimal point
            formattedNumber = formattedNumber.replaceAll("0*$", "");
            // Remove the decimal point if there are no digits after it
            formattedNumber = formattedNumber.replaceAll("\\.$", "");
        }
        return formattedNumber;
    }
}