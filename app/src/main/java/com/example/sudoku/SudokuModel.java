package com.example.sudoku;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SudokuModel extends AppCompatActivity {

    static int[][] sudoku = new int[9][9];
    static int[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sudoku = generateVoidSudoku();
        showSudokuMatrix(sudoku);


    }


    public int[][] generateVoidSudoku(){
        for(int row = 0; row  < 9; row ++){
            for(int col = 0; col  < 9; col ++){
                sudoku[row][col] = 0;
            }
        }
        return sudoku;
    }

    public void showSudokuMatrix(int[][] sudoku){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getVal(int row, int col){
        return sudoku[row][col];
    }
    public void setVal(int row, int col, int value) {
        sudoku[row][col] = value;
    }
    public static boolean checkRow(int rowNum){
        int[] rowValues = getRowValues(rowNum);
        boolean valid = true;
        for(int row = 0; row  < 9-1; row++){
            if(rowValues[row] == rowValues[rowNum + 1]){
                System.out.println("No valido!");
                valid = false;
                return valid;
            }
        }
        return valid;
    }
    public static boolean checkCol(int colNum){
        int[] colValues = getColValues(colNum);
        boolean valid = true;
        for(int col = 0; col  < 9-1; col++){
            if(colValues[col] == colValues[col + 1]){
                System.out.println("No valido!");
                valid = false;
                return valid;
            }
        }
        return valid;
    }

    public static boolean checkBlock(int row, int col){
        int j = row / 3 * 3;
        int k = col / 3 * 3;

        for (int i = 0; i < 8; i++) {
            if (sudoku[j + i / 3][k + i % 3] == 0) {
                continue;
            }
            for (int m = i + 1; m < 9; m++) {
                if (sudoku[j + i / 3][k + i % 3] == sudoku[j + m / 3][k + m % 3]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[][] generateShuDu(){
        for (int i = 0; i < 9; i++) {
            int time = 0;
            for (int j = 0; j < 9; j++) {
                sudoku[i][j] = generateNum(time);
                // Si el valor de retorno es 0, significa que está atascado y devuelto para su procesamiento
                // El principio del procesamiento de devolución es: si no es la primera columna, volverá primero a la columna anterior; de lo contrario, volverá a la última columna de la línea anterior.
                if (sudoku[i][j] == 0) {
                    // no la primera columna, retrocede una columna
                    if (j > 0) {
                        j -= 2;
                        continue;
                    } else {// es la primera columna, luego retrocede a la última columna de la línea anterior
                        i--;
                        j = 8;
                        continue;
                    }
                }
                // Llenado exitosamente
                if (isCorret(i, j)) {
                    // Inicializa el tiempo para prepararte para el próximo llenado
                    time = 0;
                } else {// continuar llenando
                    // Incrementa el número en 1
                    time++;
                    // continúa llenando la celda actual
                    j--;
                }
            }
        }
        return sudoku;
    }

    private static Random r=new Random();
    private static int generateNum(int time) {
        // First of all we try yo fill the matrix with random numbers
        if (time == 0) {
            for (int i = 0; i < 9; i++) {
                num[i] = i + 1;
            }
        }
        if (time == 9) {
            return 0;
        }
        int ranNum=r.nextInt(9 - time);
        int temp = num[8 - time];
        num[8 - time] = num[ranNum];
        num[ranNum] = temp;
        return num[8 - time];
    }

    private static boolean isCorret(int row, int col) {
        return (checkRow(row) & checkCol(col) & checkBlock(row, col));
    }

    public static int[] getRowValues(int rowNum){
        int[] rowValue = new int[9];
        for(int row = 0; row  < 9; row++){
            if(row == rowNum){
                for(int col = 0; col  < 9; col++) {
                    rowValue[row] = sudoku[rowNum][col];
                }
            }
        }
        return rowValue;
    }

    public static int[] getColValues(int colNum){
        int[] rowValue = new int[9];
        for(int col = 0; col  < 9; col++){
            if(col == colNum){
                for(int row = 0; row  < 9; row++) {
                    rowValue[col] = sudoku[row][colNum];
                }
            }
        }
        return rowValue;
    }
    public int[][] getBlockValues(int[][] block){
        int[][] blockValues = new int[3][3];

        return blockValues;
    }

}
