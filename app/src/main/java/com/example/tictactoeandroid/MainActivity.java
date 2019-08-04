package com.example.tictactoeandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private boolean playerTwo;
    private Integer[] values = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private String winner;
    private final String TAG = "MainActivity";
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.playerOne = true;
        this.playerTwo = false;
        this.winner = "None";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createGrid();
        initializePlayerScreen();
    }

    private void createGrid() {
        GridLayout gridLayout = findViewById(R.id.buttonGrid);
        gridLayout.removeAllViews();
        int total = 16;
        int column = 4;
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
                 values[i] = 1;
            } else if (buttontext.contains("O")) {
                values[i] = 2;
            } else {
                values[i] = 0;
            }

            i++;
        }



    }

    public void iteratePossibleWinnings() {
        checkRow(0,1,2,3);
        checkRow(4,5,6,7);
        checkRow(8,9,10,11);
        checkRow(12,13,14,15);

        checkRow(0,4,8,12);
        checkRow(1,5,9,13);
        checkRow(2,6,10,14);
        checkRow(3,7,11,15);

        checkRow(3,6,9,12);
        checkRow(0,5,10,15);
    }

    public void checkRow(int a, int b, int c, int d) {

        for(int i = 0; i < 4; i++) {
            if (values[a] == 1 && values[b] == 1 && values[c] == 1 && values[d] == 1) {
                winner = "Player1";
            } else if (values[a] == 2 && values[b] == 2 && values[c] == 2 && values[d] == 2) {
                winner = "Player2";
            }
        }

    }

    public void checkIfWon() {
        markButtonValues();
        iteratePossibleWinnings();

        if (this.winner.contains("Player1")) {
            //Player 1 wins
            System.out.println("Player 1 wins");
            announceWinner();

        } else if (this.winner.contains("Player2")) {
            //Player 2 wins
            System.out.println("Player 2 wins");
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
        //Set Clicked button to have either X / O
        if (playerOne) {
            this.playerOne = false;
            this.playerTwo = true;
            TextView playerScreen = findViewById(R.id.playerTurnView);
            playerScreen.setText(R.string.playerTwo);

        } else {
            this.playerOne = true;
            this.playerTwo = false;
            TextView playerScreen = findViewById(R.id.playerTurnView);
            playerScreen.setText(R.string.playerOne);
        }


    }

    public void announceWinner() {
        Intent intent = new Intent(this, DisplayWinnerActivity.class);
        String message = winner + " wins!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
