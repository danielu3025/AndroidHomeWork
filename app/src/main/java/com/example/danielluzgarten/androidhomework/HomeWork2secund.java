package com.example.danielluzgarten.androidhomework;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();

        SharedPreferences animation = this.getSharedPreferences("com.example.danielluzgarten.androidhomework", Context.MODE_PRIVATE);
        String  animTitle =  animation.getString("animation-name","") ;

        if (animTitle.equals("Slide")){
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
        else if (animTitle.equals("Rotate")){
            overridePendingTransition(R.anim.rotate, R.anim.roteateout);
        }
        else if (animTitle.equals("Back-in")){
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
        else {
            return;
        }
    }
}
