package com.example.danielluzgarten.androidhomework;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView mylistview;
    ArrayList<app> apps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mylistview = (ListView) findViewById(R.id.myListView);
        apps = new ArrayList<>();

        app wh1 = new app("Home Work 1",new Intent(getApplicationContext(), HomeWork2first.class));
        app wh2 = new app("ConstraintLayout 1",new Intent(getApplicationContext(),ConstraintLayout1.class));
        app wh3 = new app("ConstraintLayout 2",new Intent(getApplicationContext(),ConstrianLayout2.class));
        app wh4 = new app("ConstraintLayout 3",new Intent(getApplicationContext(),ConstrainLayout3.class));
        app wh5 = new app("My Birthdays",new Intent(getApplicationContext(),MyBirthdays.class));



        apps.add(wh1);
        apps.add(wh2);
        apps.add(wh3);
        apps.add(wh4);
        apps.add(wh5);


        ArrayAdapter<app> arrayadapter = new ArrayAdapter<app>(this, android.R.layout.simple_list_item_1,apps);
        mylistview.setAdapter(arrayadapter);
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                startActivity(apps.get(i).getAppIntent());

            }
        });
    }

    public void test(View view) {
        Intent intent1 = new Intent(getApplicationContext(), HomeWork2first.class);
        startActivity(intent1);
    }
}
class app {
    String name;

    public app(String name, Intent appIntent) {
        this.name = name;
        this.appIntent = appIntent;
    }

    Intent appIntent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Intent getAppIntent() {
        return appIntent;
    }

    public void setAppIntent(Intent appIntent) {
        this.appIntent = appIntent;
    }

    @Override
    public String toString() {
        return  name ;
    }
}