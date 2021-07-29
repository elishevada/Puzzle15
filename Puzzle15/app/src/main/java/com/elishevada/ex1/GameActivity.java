package com.elishevada.ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends AppCompatActivity {

    private GameBoard puzzle15=new GameBoard();
    private TextView[][] squersBoard;
    private final int BOARD_SIZE = 4;
    TextView movesTextView;
    private int countMoves = 0;
    TextView timerTextView;
    private boolean checkIfTimeRun;
    private long showTimeRunning;
    private long savingStopTime = 0;
    private boolean timeRuningatTheFirstTime;
    private MediaPlayer mp;
    private final int[][] board_matrix = {{R.id.t1, R.id.t2, R.id.t3, R.id.t4},
                                          {R.id.t5, R.id.t6, R.id.t7, R.id.t8},
                                          {R.id.t9, R.id.t10, R.id.t11, R.id.t12},
                                           {R.id.t13, R.id.t14, R.id.t15, R.id.t16}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button startNewGameBtn = (Button) this.findViewById(R.id.startNewGameBtn);
        startNewGameBtn.setOnClickListener(onClickListener);

        squersBoard = new TextView[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++) {
                //SETTING LISTENERS FOR ALL SQUERES OF THE BOARD
                squersBoard[i][j]=((TextView) this.findViewById(board_matrix[i][j]));
                squersBoard[i][j].setOnClickListener(onClickListener);
            }


        this.showTimeRunning = System.currentTimeMillis();
        this.timeRuningatTheFirstTime = true;
        timerTextView = (TextView) this.findViewById(R.id.textViewTime);
        movesTextView = (TextView) this.findViewById(R.id.textViewMoves);
        mp = MediaPlayer.create(this, R.raw.song);
        squeresBoardInit();
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            boolean checkIfWon;
            if(v.getId() == R.id.startNewGameBtn){
                startNewGame();
            }
            else {
                for(int row = 0; row < BOARD_SIZE; row++)
                    for(int col = 0; col < BOARD_SIZE; col++)
                        if(v.getId() == board_matrix[row][col]) {
                            if(puzzle15.checkMoveIfValid(row,col)) {
                                checkIfWon = puzzle15.checkBlackSquerePlace(row, col);
                                squeresBoardInit();
                                if(checkIfWon) {
                                    gameWon();
                                }
                            }
                        }
            }
        }
    };


    @Override
    public void onResume() {
        Log.d("mylog", ">> onresume()GameActivity");
        super.onResume();
        //get a pointer to the file
        SharedPreferences sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String state = sp.getString("status", null);
        if (state == null)
            return;
//in case we clicked the switch
        if (state.equals("on")) {
// Play the music
            mp.start();
            //loop to continue song
            mp.setLooping(true);
        }

        if(this.timeRuningatTheFirstTime){
            this.timeRuningatTheFirstTime = false;
            startNewGame();
        }
        else {
            this.showTimeRunning = System.currentTimeMillis() - this.savingStopTime;
            runTimer();
        }
    }

    @Override
    public void onDestroy() {
        Log.d("mylog", ">> ondestroy()MainActivity");
        super.onDestroy();
        if(mp.isPlaying())
            mp.pause();
         this.checkIfTimeRun = false;
    }

    @Override
    public void onPause() {
        Log.d("mylog", ">> onpause()MainActivity");
        super.onPause();
        if(mp.isPlaying())
            mp.pause();
        // timeruning pause
        this.checkIfTimeRun = false;
        this.savingStopTime = System.currentTimeMillis() - this.showTimeRunning;
     }




    private void squeresBoardInit(){
        String str;
        movesTextView.setText("Moves: " + String.format("%04d", this.countMoves));
        this.countMoves++;
        for (int row = 0; row <= BOARD_SIZE-1; row++) {
            for (int col = 0; col <= BOARD_SIZE - 1; col++) {
                str = puzzle15.puzzle15[row][col];
                squersBoard[row][col].setText(str);
                if (!str.equals(""))
                    squersBoard[row][col].setBackgroundColor(Color.RED);
                if (str.equals(""))
                    squersBoard[row][col].setBackgroundColor(Color.WHITE);
            }
        }
    }

    private void startNewGame(){
        for (int row = 0; row <= BOARD_SIZE-1; row++)
            for (int col = 0; col <= BOARD_SIZE-1; col++)
                squersBoard[row][col].setClickable(true);
       this.countMoves = 0;
        this.showTimeRunning = System.currentTimeMillis();
        puzzle15 = new GameBoard();
        runTimer();
        squeresBoardInit();
    }

    private void runTimer(){
        this.checkIfTimeRun = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (checkIfTimeRun){
                    SystemClock.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long millis = System.currentTimeMillis() - showTimeRunning;
                            int seconds = (int) (millis / 1000);
                            int minutes = seconds / 60;
                            seconds = seconds % 60;
                            if(checkIfTimeRun)
                                timerTextView.setText("Time: " + String.format("%02d:%02d", minutes, seconds));
                        }
                    });
                }
            }
        }).start();
    }


    private void clickableSqueres(){
        for (int row = 0; row < BOARD_SIZE; row++)
            for (int col = 0; col < BOARD_SIZE; col++)
                squersBoard[row][col].setClickable(false);
    }


    public void gameWon(){
        clickableSqueres();
        this.checkIfTimeRun = false;
        Toast.makeText(this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
    }


}