package com.gravity.equationiq;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    CardView simpleOprCard, oneVariableCard, quadEqCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        simpleOprCard = findViewById(R.id.simpleOperations);
        oneVariableCard = findViewById(R.id.oneVariableCard);
        quadEqCard = findViewById(R.id.quadEqCard);

        simpleOprCard.setOnClickListener(View -> {
            startActivity(new Intent(MainActivity.this, simpleOperationActivity.class));
        });

        oneVariableCard.setOnClickListener(View -> {
            startActivity(new Intent(MainActivity.this, LinearEquationActivity.class));
        });

        quadEqCard.setOnClickListener(View -> {
            startActivity(new Intent(MainActivity.this, QuadEquationActivity.class));
        });
    }
}