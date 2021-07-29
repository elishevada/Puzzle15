package com.elishevada.ex1;

import android.util.Log;

import java.util.Collections;
import java.util.Arrays;

public class GameBoard {
    public final int BOARD_SIZE = 4;
    //the empty squer
    public int emptySquereRow = BOARD_SIZE - 1;
    public int emptySquereCol = BOARD_SIZE - 1;
    public String[][] puzzle15 = new String[BOARD_SIZE][BOARD_SIZE];

//constructor
    public GameBoard() {
        initBoard();
    }


    public void initBoard() {
        //  put numbers 0 to 15.
        String[] arrayRandomValues = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        boolean ckeckIfValid = false;
        while (!ckeckIfValid) {
            Collections.shuffle(Arrays.asList(arrayRandomValues));
            for (int row = 0; row <= BOARD_SIZE - 1; row++) {
                for (int col = 0; col <= BOARD_SIZE - 1; col++) {
                    this.puzzle15[row][col] = arrayRandomValues[row * this.puzzle15.length + col];
                }
            }
            //  If the mixing was not a good mix.
            //   check if the board is valid
            //    every time its false it will go back and put values
            ckeckIfValid = checkIfShuffleValid() && !gameOverWin();
        }
    }


    public boolean checkIfShuffleValid() {
        String[] strArray = new String[BOARD_SIZE * BOARD_SIZE];
        for (int row = 0; row <= BOARD_SIZE - 1; row++)
            for (int col = 0; col <= BOARD_SIZE - 1; col++) {
                //where is no number is the empty one
                if (this.puzzle15[row][col].equals("")) {
                    this.emptySquereRow = row;
                    this.emptySquereCol = col;
                }
            }
        int p = 0;
        for (int row = 0; row <= BOARD_SIZE - 1; row++) {
            for (int col = 0; col <= BOARD_SIZE - 1; col++) {
                strArray[p] = this.puzzle15[row][col];
                p++;
            }
        }
        int countM = 0;
        for (int row = 0; row < BOARD_SIZE * BOARD_SIZE - 1; row++) {
            for (int col = row + 1; col < BOARD_SIZE * BOARD_SIZE; col++) {
                // count changes
                if (!strArray[row].equals("") && !strArray[col].equals("") && Integer.parseInt(strArray[row]) > Integer.parseInt(strArray[col]))
                    countM++;
            }
        }

        if ((BOARD_SIZE - this.emptySquereRow) % 2 == 1)
            // if count is even
            return !(countM %2 == 1);
        else
            return countM %2 == 1;
    }


    // win
    public boolean gameOverWin() {
        int number = 0;
        for (int row = 0; row <= BOARD_SIZE - 1; row++) {
            for (int col = 0; col <= BOARD_SIZE - 1; col++) {
                number++;
                //  empty squere is in the right place
                if (row == BOARD_SIZE - 1 && col == BOARD_SIZE - 1) {
                    return true;
                }
                if (!(puzzle15[row][col].equals(Integer.toString(number)))) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean checkMoveIfValid(int row, int col){
        return ((row + 1 == this.emptySquereRow || row - 1 == this.emptySquereRow) && col == this.emptySquereCol)
                || ((col + 1 == this.emptySquereCol || col - 1 == this.emptySquereCol) && row == this.emptySquereRow);
    }



    public boolean checkBlackSquerePlace(int row, int col){
        String squerText = this.puzzle15[row][col];
        this.puzzle15[row][col] = "";
        this.puzzle15[this.emptySquereRow][this.emptySquereCol] = squerText;
        this.emptySquereRow = row;
        this.emptySquereCol = col;
        return gameOverWin();
    }

}

