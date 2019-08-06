package com.example.tictactoeandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private boolean playerOne;

    private ArrayList<Integer> values;
    private String winner;
    private boolean isAllPressed;
    private final String TAG = "MainActivity";
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.playerOne = true;
        this.isAllPressed = false;
        this.winner = "None";
        this.values = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        createGrid();
        initializePlayerScreen();
        initializeValues();
    }

    public void initializeValues() {
        for(int i = 0; i < 100; i++) {
            values.add(0);
        }
    }

    private void createGrid() {
        GridLayout gridLayout = findViewById(R.id.buttonGrid);
        gridLayout.removeAllViews();
        int total = 100;
        int column = 10;
        int row = total / column;
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row + 1);
        for (int i = 0, c = 0, r = 0; i < total; i++, c++) {
            if (c == column) {
                c = 0;
                r++;
            }

            LayoutInflater.from(this).inflate(R.layout.cell_button, gridLayout, true);


        }

    }

    public void markButtonValues() {


        GridLayout gridLayout = findViewById(R.id.buttonGrid);
        int count = gridLayout.getChildCount();
        int i = 0;
        while (i < count) {


            View child = (Button) gridLayout.getChildAt(i);
            String buttontext = ((Button) child).getText().toString();
            if (buttontext.contains("X")) {
                 values.set(i, 1);
            } else if (buttontext.contains("O")) {
                values.set(i, 2);
            } else {
                values.set(i, 0);
            }

            i++;
        }



    }

    public void iteratePossibleWinnings() {
        //horizontal line check
        for (int index = 0; index <= 90; index += 10) {

            for (int i = index; i < (index + 6); i++) {
                checkRow(i,i+1,i+2,i+3,i+4);
            }
        }

        //vertical line check
        for (int i = 0; i < 10; i++) {

            for (int index = i; index < 60; index += 10) {
                checkRow(index,index+10,index+20,index+30,index+40);
            }
        }
        //diagonal line check
        for (int i = 0; i < 6; i ++) {
            for (int index = i; index < 60; index += 10) {
                checkRow(index,index+11,index+22,index+33,index+44);

            }
        }
        for (int i = 90; i < 96; i ++) {
            for (int index = i; index > 40; index -= 10) {
                checkRow(index,index-9,index-18,index-27,index-36);
            }
        }

    }

    public void checkRow(int a, int b, int c, int d, int e) {

        for(int i = 0; i < 4; i++) {
            if (values.get(a) == 1 && values.get(b) == 1 && values.get(c) == 1 && values.get(d) == 1 && values.get(e) == 1) {
                winner = "Player 1 wins!";
            } else if (values.get(a) == 2 && values.get(b) == 2 && values.get(c) == 2 && values.get(d) == 2 && values.get(e) == 2) {
                winner = "Player 2 wins!";
            }
        }

    }

    public void checkIfAllPressed() {
        this.isAllPressed = true;
        GridLayout grid = findViewById(R.id.buttonGrid);
        int cells = grid.getChildCount();
        for(int i = 0; i < cells; i++) {
            View child = grid.getChildAt(i);
            if(!(child.isSelected())) {
                isAllPressed = false;
            }
        }

    }

    public void checkIfWon() {
        markButtonValues();
        iteratePossibleWinnings();
        checkIfAllPressed();

        if (this.winner.contains("Player 1 wins!")) {
            //Player 1 wins

            announceWinner();

        } else if (this.winner.contains("Player 2 wins!")) {
            //Player 2 wins

            announceWinner();
        } else if (isAllPressed == true && this.winner.contains("None")) {
            //In case of draw
            this.winner = "Draw!";
            announceWinner();
        }
    }

    public void initializePlayerScreen() {
        TextView playerScreen = findViewById(R.id.playerTurnView);
        playerScreen.setText(R.string.playerOne);
    }

    public void buttonPress(View v) {
        if (!(v.isSelected())) {
            v.setSelected(true);
            Button button = v.findViewById(R.id.cellButton);

            //Set Clicked button to have either X / O
            if (this.playerOne) {
                button.setText(R.string.X);

            } else {
                button.setText(R.string.O);
            }

        }

        checkIfWon();
        changeTurns();
    }

    public void changeTurns() {

        if (playerOne) {
            this.playerOne = false;

            TextView playerScreen = findViewById(R.id.playerTurnView);
            playerScreen.setText(R.string.playerTwo);

        } else {
            this.playerOne = true;

            TextView playerScreen = findViewById(R.id.playerTurnView);
            playerScreen.setText(R.string.playerOne);
        }


    }

    public void announceWinner() {
        Intent intent = new Intent(this, DisplayWinnerActivity.class);
        String message = winner;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
