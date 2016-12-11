package com.example.danielluzgarten.androidhomework;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ConstrainLayout3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constrain_layout3);
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
