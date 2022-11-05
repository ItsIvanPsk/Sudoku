package com.example.sudoku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Duration;
import java.util.ArrayList;

import kotlin.Unit;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int cols = 9;
    int rows = 9;
    TableLayout table;
    static ArrayList<String> spinnerArray = new ArrayList<String>();
    static  Spinner[][] spinners = new Spinner[9][9];
    SudokuModel sm = new SudokuModel();
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = findViewById(R.id.sudoku__table);
        Button btn = findViewById(R.id.sudoku__button_regen);

        fillNumbers();
        sm.genStartSudoku();
        createTable();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("pressed!");

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Sudoku");
                alertDialog.setMessage("Do you wanna generate another sudoku?");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        refreshSpinners();
                        sm.genStartSudoku();
                        refrescaGUI();
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });

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
                spinner.setTag("bug init");
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
        refrescaGUI();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getTag().equals("bug init")) {
            adapterView.setTag("okay, no more bug");
        }
        else {
            if (!(adapterView.getSelectedItem().toString() == " ")){
                if ( sm.setVal(
                        (int) adapterView.getTag(R.id.row),
                        (int) adapterView.getTag(R.id.col),
                        Integer.parseInt(adapterView.getSelectedItem().toString())
                )){
                    Toast.makeText(getApplicationContext(), "Correct number", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect number", Toast.LENGTH_LONG).show();
                }
                refrescaGUI();
            } else { adapterView.setSelection(0); }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void refrescaGUI(){
        for(int i = 0; i < 9;i++){
            for(int j = 0; j < 9;j++){
                spinners[i][j].setSelection(sm.getVal(i, j));
                if(sm.getVal(i, j) != 0) {
                    spinners[i][j].setEnabled(false);
                    spinners[i][j].setBackgroundColor(Color.LTGRAY);
                } else {
                    spinners[i][j].setEnabled(true);
                }
            }
        }
    }

    public void refreshSpinners(){
        for(int i = 0; i < 9;i++){
            for(int j = 0; j < 9;j++){
                spinners[i][j].setBackgroundColor(Color.LTGRAY);
                spinners[i][j].setBackgroundResource(R.drawable.customborder);
                spinners[i][j].setEnabled(true);
            }
        }
    }

}