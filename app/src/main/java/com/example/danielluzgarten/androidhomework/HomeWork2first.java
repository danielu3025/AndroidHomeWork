package com.example.danielluzgarten.androidhomework;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.animation;

public class HomeWork2first extends AppCompatActivity {

    EditText num1input;
    EditText num2input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work2first);
        num1input = (EditText)findViewById(R.id.n1);
        num2input = (EditText)findViewById(R.id.n2);
        //call to btCalcOnPress if user press on "enter" in the keyboard
        num1input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            btCalcFunction(view);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        num2input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            btCalcFunction(view);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }

    //close keybord if user press on other place on the screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }


    public void btCalcFunction(View view) {
        if(String.valueOf(num1input.getText()).matches("")||String.valueOf(num2input.getText()).matches("")){
            Toast.makeText(this,"one or more fields are blank",Toast.LENGTH_LONG).show();
        }
        else {
            Intent calcIntent = new Intent(getApplicationContext(), HomeWork2secund.class);
            calcIntent.putExtra("number1", String.valueOf(num1input.getText()));
            calcIntent.putExtra("number2", String.valueOf(num2input.getText()));
            startActivity(calcIntent);
        }

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

