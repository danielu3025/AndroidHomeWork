package com.example.danielluzgarten.androidhomework;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.R.attr.format;
import static android.R.attr.name;

public class MyBirthdays extends AppCompatActivity {

    SQLiteDatabase myDB;
    static String dateInput;
    EditText nameInput;
    EditText commnetInput;
    ArrayList<Record> bdList;
    ListView bdListView;
    ArrayAdapter<Record> arrayadapter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_birthdays);

        try {

            myDB = this.openOrCreateDatabase("danielDb", MODE_PRIVATE, null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS birthDayTable (name VARCHAR, date  VARCHAR, nb INTEGER(3), comment VARCHAR, id INTEGER PRIMARY KEY)");
            // myDB.execSQL("INSERT INTO birthDayTable (name, date, nb) VALUES (';+"', '14/11/1990', 370)");

            nameInput = (EditText) findViewById(R.id.inpName);
            commnetInput = (EditText) findViewById(R.id.inpComment);
            bdList = new ArrayList<>();


        } catch (Exception e) {
            e.printStackTrace();
        }
        bdListView = (ListView) findViewById(R.id.bdlistView);
        arrayadapter = new ArrayAdapter<Record>(this, android.R.layout.simple_list_item_1, bdList);
        bdListView.setAdapter(arrayadapter);

        updateDb();


    }

    public void datedialogopener(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            month++;
            if (month > 12) {
                month = 1;
            }

            dateInput = String.format("%d/%d/%d", month, day, year);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)

    public void addNewRecord(View view) {
        SqlHaendler sqlh = new SqlHaendler();
        sqlh.run();

        Runnable userthred = (new Runnable() {

            public Runnable start() {
                run();
                return null;
            }

            @Override
            public void run() {
                try {
                    String name = String.valueOf(nameInput.getText());
                    String date = dateInput;
                    String comment = String.valueOf(commnetInput.getText());

                    if (date == null || comment == null || name == null || name.isEmpty() || date.isEmpty() || comment.isEmpty()) {

                    } else {
                        int days = calcDays(date);
                        String text = String.format("INSERT INTO birthDayTable (name, date, nb, comment) VALUES ('%s', '%s', %d, '%s')", name, date, days, comment);
                        myDB.execSQL(text);
                        updateDb();
                        nameInput.setText("");
                        commnetInput.setText("");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private int getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return (int) timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    private void sortBday() {
        Collections.sort(bdList, new Comparator<Record>() {
            @Override
            public int compare(Record r1, Record r2) {
                return r1.getNdDays() - r2.getNdDays();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int calcDays(String date) {
        int days = 0;
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        convertedDate.setYear(today.getYear());
        days = getDateDiff(today, convertedDate, TimeUnit.DAYS);
        if (days < 0) {
            convertedDate.setYear(today.getYear() + 1);
            days = getDateDiff(today, convertedDate, TimeUnit.DAYS);
        }
        return days;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateDb() {
        try {
            bdList.clear();
            Cursor c = myDB.rawQuery("SELECT * FROM birthDayTable", null);

            int nameIndex = c.getColumnIndex("name");
            int dateIndex = c.getColumnIndex("date");
            int commentIndex = c.getColumnIndex("comment");
            int nbIndex = c.getColumnIndex("nb");

            try {
                while (c.moveToNext()) {
                    Record nr = new Record(c.getString(nameIndex), c.getString(dateIndex), c.getString(commentIndex), c.getInt(nbIndex));
                    bdList.add(nr);
                }
            } finally {
                c.close();
            }
            myDB.execSQL("DELETE FROM birthDayTable WHERE name !='###$$$$'");
            for (Record record : bdList) {
                record.setNdDays(calcDays(record.getDate()));
                String text = String.format("INSERT INTO birthDayTable (name, date, nb, comment) VALUES ('%s', '%s', %d, '%s')", record.getName(), record.getDate(), record.ndDays, record.comment);
                myDB.execSQL(text);
            }
            sortBday();
            arrayadapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    class SqlHaendler implements Runnable {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {


        }
    }


    class Record {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getNdDays() {
            return ndDays;
        }

        public void setNdDays(int ndDays) {
            this.ndDays = ndDays;
        }

        public Record(String name, String date, String comment, int ndDays) {
            this.name = name;
            this.date = date;
            this.comment = comment;
            this.ndDays = ndDays;
        }

        @Override
        public String toString() {
            return name + " " + date;
        }

        String date;
        String comment;
        int ndDays;
    }
}

