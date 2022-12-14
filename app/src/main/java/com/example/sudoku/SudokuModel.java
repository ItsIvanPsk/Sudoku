package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Random;

public class SudokuModel extends AppCompatActivity {

    static int[][] sudoku = new int[9][9];
    static int[][] solvedSudoku = new int[9][9];
    private static int[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    public static int getVal(int row, int col){
        return sudoku[row][col];
    }

    public static boolean setVal(int row, int col, int value) {
        int prev_value = sudoku[row][col];
        sudoku[row][col] = value;
        System.out.println("Sudoku: " + sudoku[row][col]);
        System.out.println("Solved Sudoku: " + solvedSudoku[row][col]);

        if (sudoku[row][col] != solvedSudoku[row][col]){
            sudoku[row][col] = prev_value;
            return false;
        } else {
            sudoku[row][col] = value;
            return true;
        }
    }

    public boolean checkSudoku(){
        boolean isFullyCorrect = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(!isCorret(i,j)){
                    isFullyCorrect = false;
                    break;
                }
            }
        }
        return isFullyCorrect;
    }

    public static int[][] generateSudoku(){
        sudoku = new int[9][9];
        for (int i = 0; i < 9; i++) {
            int time = 0;
            for (int j = 0; j < 9; j++) {
                sudoku[i][j] = generateNum(time);
                if (sudoku[i][j] == 0) {
                    if (j > 0) {
                        j -= 2;
                        continue;
                    } else {
                        i--;
                        j = 8;
                        continue;
                    }
                }
                if (isCorret(i, j)) {
                    time = 0;
                } else {
                    time++;
                    j--;
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                solvedSudoku[i][j] = sudoku[i][j];
            }
        }

        return sudoku;
    }

    public void genStartSudoku(){
        generateSudoku();
        int cantSolved = 80;

        for (int j = 0; j < cantSolved; j++) {
            int r = (int) (Math.random() * 9);
            int c = (int) (Math.random() * 9);
            sudoku[r][c] = 0;
        }
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
