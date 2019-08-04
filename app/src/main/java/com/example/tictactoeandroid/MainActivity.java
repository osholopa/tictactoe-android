package com.example.tictactoeandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean playerOne;
    private boolean playerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.playerOne = true;
        this.playerTwo = false;

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
        for(int i =0, c = 0, r = 0; i < total; i++, c++)
        {
            if(c == column)
            {
                c = 0;
                r++;
            }

            LayoutInflater.from(this).inflate(R.layout.cell_button, gridLayout , true);


        }

    }

    public void initializePlayerScreen() {
        TextView playerScreen = findViewById(R.id.playerTurnView);
        playerScreen.setText(R.string.playerOne);
    }

    public void buttonPress(View v) {
        if(!(v.isSelected())) {
            v.setSelected(true);
            Button button = v.findViewById(R.id.cellButton);
            if(this.playerOne) {
                button.setText(R.string.X);
            } else {
                button.setText(R.string.O);
            }

        }

        changeTurns();
    }

    public void changeTurns() {
        //Set Clicked button to have either X / O
        if(playerOne) {
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

    public void reset(View v) {
        if(playerTwo) {
            changeTurns();
        }
        createGrid();
    }

}
