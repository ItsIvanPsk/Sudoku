package com.example.sudoku;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SudokuModel extends AppCompatActivity {

    static int[][] sudoku = new int[9][9];
    static int[][] solvedSudoku = new int[9][9];
    private static int[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    public static int getVal(int row, int col){
        return sudoku[row][col];
    }

    public static void setVal(int row, int col, int value) {
        sudoku[row][col] = value;
    }

    public static void generateShuDu(){
        // generar números
        for (int i = 0; i < 9; i++) {
            // número de intentos de llenar
            int time = 0;
            // completa los números
            for (int j = 0; j < 9; j++) {
                // generar números
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
        // solvedSudoku = sudoku;
        Log.i("55555", "The sudoku has been generated!");
    }

    private static boolean isCorret(int row, int col) {
        return (checkRow(row) & checkLine(col) & checkNine(row, col));
    }

    private static boolean checkRow(int row) {
        for (int j = 0; j < 8; j++) {
            if (sudoku[row][j] == 0) {
                continue;
            }
            for (int k = j + 1; k < 9; k++) {
                if (sudoku[row][j] == sudoku[row][k]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkLine(int col) {
        for (int j = 0; j < 8; j++) {
            if (sudoku[j][col] == 0) {
                continue;
            }
            for (int k = j + 1; k < 9; k++) {
                if (sudoku[j][col] == sudoku[k][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkNine(int row, int col) {
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

    private static Random r=new Random();
    private static int generateNum(int time) {
        if (time == 0) {
            for (int i = 0; i < 9; i++) {
                num[i] = i + 1;
            }
        }
        if (time == 9) {
            return 0;
        }
        int ranNum=r.nextInt(9 - time);//j2me
        int temp = num[8 - time];
        num[8 - time] = num[ranNum];
        num[ranNum] = temp;
        return num[8 - time];
    }
}
