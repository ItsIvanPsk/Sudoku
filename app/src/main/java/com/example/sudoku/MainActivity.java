package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int col = 9;
    int row = 9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout layout = new GridLayout(this);
        layout.setColumnCount(row);
        layout.setRowCount(col);

        for (int i = 0; i < row*col; i++){
            ArrayList<String> spinnerArray = new ArrayList<String>();
            spinnerArray.add(" ");
            spinnerArray.add("1");
            spinnerArray.add("2");
            spinnerArray.add("3");
            spinnerArray.add("4");
            spinnerArray.add("5");
            spinnerArray.add("6");
            spinnerArray.add("7");
            spinnerArray.add("8");
            spinnerArray.add("9");

            Spinner spinner = new Spinner(this);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            spinner.setAdapter(spinnerArrayAdapter);

            layout.addView(spinner);
        }

        setContentView(layout);

    }

    public void createVoidSpinner()
    {

        /*
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.numbers,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
         */
    }
}