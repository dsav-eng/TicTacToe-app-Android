package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.*;
import android.widget.*;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Button> listOfBtns = new ArrayList<Button>();
    private final int SIZE = 3;

    private int movesCount = 0;
    private String player = "X";
    private int pointsPlayerX = 0;
    private int pointsPlayerO = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNewGame = findViewById(getResourceID("btn_new_game"));
        Button btnReset = findViewById(getResourceID("btn_reset"));

        createLayout(getResourceID("linear_layout1"));
        createLayout(getResourceID("linear_layout2"));
        createLayout(getResourceID("linear_layout3"));

        //On-click function for the NewGame btn
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });

        //On-click for the RestScore btn
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPoints();
            }
        });
    }

    private int getResourceID(String id) {
        return getResources().getIdentifier(id, "id", this.getPackageName());
    }

    private void createLayout(int id) {
        LinearLayout linearLayout = (LinearLayout) findViewById(id);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1);

        for (int i = 0; i < SIZE ; i++) {
            Button button = new Button(this);
            button.setText("");
            button.setFreezesText(true);
            linearLayout.addView(button, params);
            button.setOnClickListener(this);
            listOfBtns.add(button);

            //add styling to Layout and buttons:
            linearLayout.setBackgroundColor(Color.rgb(189, 189, 189));

            button.setTextColor(Color.rgb(255,242,223));
            button.getBackground().setColorFilter(Color.rgb(45, 45, 48), PorterDuff.Mode.MULTIPLY);
            button.setTextSize(60);
        }
    }

    @Override
    public void onClick(View v) {
        if (!validPosition(v)) {
            Toast.makeText(this, "Invalid Position, Try Another Field", Toast.LENGTH_SHORT).show();

        } else {
            if (player.equals("X")) {
                ((Button) v).setText("X");
            } else {
                ((Button) v).setText("O");
            }
            movesCount++;

            if (WonGame(player)) {
                Toast toast1 = Toast.makeText(this, "Player "+ player + " wins!", Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.FILL_HORIZONTAL,0,0);
                toast1.show();

                if (player.equals("X")) pointsPlayerX++;
                else pointsPlayerO++;

                updatePlayerScore();
            }

            if (Draw()) {
                Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
            }

            //Switch turns
            if (player.equals("X")) {
                player = "O";
            } else player = "X";
        }
    }

    private boolean validPosition(View v) {
        if (((Button) v).getText().equals("")) return true;
        else return false;
    }
    private boolean WonDiagonal (String player) {

        int count = 0;
        for (int i = 0; i < SIZE * SIZE; i = i + (SIZE+1)) {
            if ((listOfBtns.get(i).getText().toString()).equals(player)) {
                count++;
            }
            if (count == SIZE) return true;
        }

        count = 0;
        for (int i = (SIZE - 1); i <= (SIZE * SIZE - SIZE); i = i + (SIZE-1)) {
            if ((listOfBtns.get(i).getText().toString()).equals(player)) {
                count++;
            }
            if (count == SIZE) return true;
        }

        return false;
    }

      private boolean WonStraigtLines (String player) {
        //Check vertical lines:
        for(int i = 0; i < SIZE; i ++) {
            int count = 0;
            for (int j = i; j <= (i +(SIZE * (SIZE - 1))); j = j + SIZE) {
                if ((listOfBtns.get(j).getText().toString()).equals(player)) {
                    count++;
                }
            }
            if (count == SIZE) return true;

        }
        //Check horizontal lines:
        for(int i = 0; i <= (SIZE * (SIZE - 1)); i = i + SIZE) {
            int count = 0;
            for (int j = i; j < (i + SIZE); j++) {
                if ((listOfBtns.get(j).getText().toString()).equals(player)) {
                    count++;
                }
            }
            if (count == SIZE) return true;

        }
        return false;
      }

    private boolean WonGame(String player) {
       if(WonDiagonal(player) || WonStraigtLines(player)){
          for (Button button : listOfBtns) {
            button.setEnabled(false);
          }
          return true;
       }
        return false;
    }

    private boolean Draw(){
        if (this.movesCount == SIZE * 3) return true;
        else return false;
    }

    private void newGame() {
        for (Button button : listOfBtns) {
            button.setText("");
            button.setEnabled(true);
            this.movesCount = 0;
        }
        player = "X";
    }

    private void updatePlayerScore(){
      TextView textViewPointsP1 = (TextView) findViewById(R.id.text_view_p1);
      TextView textViewPointsP2 = (TextView) findViewById(R.id.text_view_p2);

      textViewPointsP1.setText("Player \"X\": " + pointsPlayerX);
      textViewPointsP2.setText("Player \"O\": " + pointsPlayerO);
    }

    private void resetPoints() {
        pointsPlayerX = 0;
        pointsPlayerO = 0;
        updatePlayerScore();

        newGame();
    }
}