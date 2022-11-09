package com.example.sudoku;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class Sudoku extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int cols = 9;
    int rows = 9;
    TableLayout table;
    int start_hearth = 5;
    static ArrayList<ImageView> hearth_vr;
    static int actual_hearth = 5;

    static ArrayList<String> spinnerArray = new ArrayList<String>();
    static  Spinner[][] spinners = new Spinner[9][9];
    SudokuModel sm = new SudokuModel();
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        table = findViewById(R.id.sudoku__table);
        Button btn = findViewById(R.id.sudoku__button_regen);

        fillNumbers();
        sm.genStartSudoku();
        createTable();
        refrescaGUI();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("pressed!");

                AlertDialog alertDialog = new AlertDialog.Builder(Sudoku.this).create();
                alertDialog.setTitle("Sudoku");
                alertDialog.setMessage("Do you wanna go to the main menu?");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(Sudoku.this, MainActivity.class);
                        startActivity(myIntent);
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
            TableRow tr = new TableRow(Sudoku.this);
            for (col = 0; col < cols; col++) {
                Spinner spinner = new Spinner(Sudoku.this);
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
                    TableRow espacio = new TableRow(Sudoku.this);
                    TextView esp = new TextView(Sudoku.this);
                    espacio.addView(esp);
                    table.addView(espacio);
                }
                if(col % 3 == 0 && col != 0 && col != cols)
                {
                    TextView esp = new TextView(Sudoku.this);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getTag().equals("bug init")) {
            adapterView.setTag("bug resolved");
        }
        else {
            if (!(adapterView.getSelectedItem().toString().equals(" "))){
                if ( sm.setVal(
                        (int) adapterView.getTag(R.id.row),
                        (int) adapterView.getTag(R.id.col),
                        Integer.parseInt(adapterView.getSelectedItem().toString())
                ) ){
                    Toast.makeText(getApplicationContext(), "Correct number", Toast.LENGTH_LONG).show();
                    if ( sm.checkSudoku() ) {
                        /*
                        AlertDialog alertDialog = new AlertDialog.Builder(Sudoku.this).create();
                        alertDialog.setTitle("Sudoku");
                        alertDialog.setMessage("Congratulations!!\nPress the button to go to the main menu.");

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Go Main Menu", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent myIntent = new Intent(Sudoku.this, MainActivity.class);
                                startActivity(myIntent);
                            }
                        });

                        alertDialog.show();
                         */
                    }
                } else {

                    actual_hearth--;
                    if (actual_hearth == 0) {
                        AlertDialog alertDialog = new AlertDialog.Builder(Sudoku.this).create();
                        alertDialog.setTitle("Sudoku");
                        alertDialog.setMessage("You lose, return to the main menu and play again!");

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Go Main Menu", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent myIntent = new Intent(Sudoku.this, MainActivity.class);
                                startActivity(myIntent);
                            }
                        });

                        alertDialog.show();
                    } else {
                        System.out.println(actual_hearth);
                        switch (actual_hearth) {
                            case 1:
                                findViewById(R.id.hearth_2).setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                findViewById(R.id.hearth_3).setVisibility(View.INVISIBLE);
                                break;
                            case 3:
                                findViewById(R.id.hearth_4).setVisibility(View.INVISIBLE);
                                break;
                            case 4:
                                findViewById(R.id.hearth_5).setVisibility(View.INVISIBLE);
                                break;
                        }

                        AlertDialog alertDialog = new AlertDialog.Builder(Sudoku.this).create();
                        alertDialog.setTitle("Sudoku");
                        alertDialog.setMessage("Incorrect number!!");

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.cancel();
                            }
                        });
                        alertDialog.show();
                    }
                }
                refrescaGUI();
            } else {
                adapterView.setSelection(0);
                refrescaGUI();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void refrescaGUI(){
        for(int i = 0; i < 9;i++){
            for(int j = 0; j < 9;j++){
                if(spinners[i][j].getSelectedItem() != " "){
                    spinners[i][j].setSelection(sm.getVal(i, j));
                    spinners[i][j].setEnabled(false);
                } else {
                    spinners[i][j].setSelection(sm.getVal(i, j));
                    spinners[i][j].setEnabled(true);
                }
            }
        }
    }

    public void initialDisable() {
        
    }

}