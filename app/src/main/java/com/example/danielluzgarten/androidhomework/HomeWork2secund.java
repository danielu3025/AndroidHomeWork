package com.example.danielluzgarten.androidhomework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeWork2secund extends AppCompatActivity {

    int x = 0;
    int y = 0;
    int z = 0;
    TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work2secund);
        Intent intent = getIntent();
        answer = (TextView) findViewById(R.id.txtViewResult);
        x = Integer.parseInt(intent.getStringExtra("number1"));
        y = Integer.parseInt(intent.getStringExtra("number2"));
        z = x + y;
        answer.setText(String.valueOf(z));
    }
}
