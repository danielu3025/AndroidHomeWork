package com.example.danielluzgarten.androidhomework;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    String currentAnimation;
    TextView animationTextView;
    SharedPreferences animation;
    ListView animList ;
    ArrayList <String> animArray ;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        animationTextView = (TextView) findViewById(R.id.aniTextView);
        animation = this.getSharedPreferences("com.example.danielluzgarten.androidhomework", Context.MODE_PRIVATE);
        animationTextView.setText(animation.getString("animation-name",""));

        animList = (ListView) findViewById(R.id.animList);
        animArray = new ArrayList<>();
        animArray.add("Slide");
        animArray.add("Rotate");
        animArray.add("Back-in");

        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,animArray);
        animList.setAdapter(arrayadapter);
        animList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                currentAnimation = animArray.get(i);
                animationTextView.setText(currentAnimation);
                animation.edit().putString("animation-name",currentAnimation).apply();
            }
        });
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
