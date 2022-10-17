package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int cols = 9;
    int rows = 9;
    TableLayout table;
    static ArrayList<String> spinnerArray = new ArrayList<String>();
    Spinner[][] spinners = new Spinner[9][9];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table = findViewById(R.id.table);

        fillNumbers();
        createTable();

        SudokuModel sm = new SudokuModel();
        sm.generateVoidSudoku();

        Log.i("5", "MSG BEFORE");

    }

    public static void fillNumbers()
    {
        spinnerArray.clear();
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
    }

    public void createTable(){
        int row = 0;
        int col = 0;
        int block = 0;
        for (row = 0; row < rows; row++){
            TableRow tr = new TableRow(MainActivity.this);
            for (col = 0; col < cols; col++) {
                Spinner spinner = new Spinner(MainActivity.this);
                spinner.setBackgroundColor(Color.LTGRAY);
                spinner.setBackgroundResource(R.drawable.customborder);
                spinner.setOnItemSelectedListener(this);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerArray);
                spinner.setAdapter(spinnerArrayAdapter);
                spinner.setTag(R.id.row,row);
                spinner.setTag(R.id.col,col);
                spinner.setTag(R.id.block,block);
                if(row % 3 == 0 && col % 9 == 0)
                {
                    TableRow espacio = new TableRow(MainActivity.this);
                    TextView esp = new TextView(MainActivity.this);
                    espacio.addView(esp);
                    table.addView(espacio);
                }
                if(col % 3 == 0 && col != 0 && col != cols)
                {
                    TextView esp = new TextView(MainActivity.this);
                    esp.setText("     ");
                    tr.addView(esp);
                }
                spinners[row][col] = spinner;
                tr.addView(spinner);

            }
            table.addView(tr);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        String row = (String) view.getTag(0);
        String col = (String) view.getTag();
        String block = (String) view.getTag();
        System.out.println("ROW: " + row);

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

}