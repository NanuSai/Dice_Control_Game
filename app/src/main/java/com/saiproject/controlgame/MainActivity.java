package com.saiproject.controlgame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    private static final SecureRandom  secureRandomNumbers = new SecureRandom(); // Constant class object accessible only within the class

    private enum Status { NOTSTARTEDYET,PROCEED,WON,LOST};

    private static final int TIGER_CLAWS = 2;
    private static final int TREE = 3;
    private static final int SEVEN = 7;
    private static final int WE_LEVEN = 11;
    private static final  int PANTHER = 12;


    TextView txtCalculations;
    ImageView imgDice;

    Button btnRestartGame;
    String oldTxtCalculationsvalue= "";
    boolean firstTime = true;
    Status gameStatus = Status.NOTSTARTEDYET; // NOTSTARTEDYET == 0
    int points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //Start

        txtCalculations = findViewById(R.id.txtCalculations);
        imgDice = findViewById(R.id.imgDice);
        btnRestartGame = findViewById(R.id.btnRestartTheGame);
        final TextView txtGameStatus = findViewById(R.id.txtGameStatus);



        makeBtnRestartGameInvisible();
        txtGameStatus.setText("");
        txtCalculations.setText("");


        imgDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameStatus == Status.NOTSTARTEDYET){

                    int diceSum = TollDice();
                    oldTxtCalculationsvalue = txtCalculations.getText().toString();
                    points = 0;

                    switch(diceSum){

                     //You win if you either get SEVEN or ELEVEN
                        case SEVEN:
                        case WE_LEVEN:
                                gameStatus = Status.WON;
                                txtGameStatus.setText("You Won!");
                                makeImgDiceInvisible();     //Image of dice is gone
                                makeBtnRestartGameVisible();       //Restart??
                                break;

                     //You lose if you get TIGER_CLAWS,TREE,PANTHER
                        case TIGER_CLAWS:
                        case TREE:
                        case PANTHER:
                            gameStatus = Status.LOST;
                            txtGameStatus.setText("You lose!");
                            makeImgDiceInvisible();            //Dice image invisible
                            makeBtnRestartGameVisible();        //Restart !
                            break;

                     //Proceed for below cases
                        case 4:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10: //If dice sum is 4/5/6/8/9/10, then diceSum is assigned to points , oldTextvalue stores point.
                            gameStatus = Status.PROCEED;
                            points = diceSum;
                            txtCalculations.setText(oldTxtCalculationsvalue + "Your point is "+ points + "\n"); //At first role oldTxtvalue = "You rolled 3 + 3 = 6\n"
                            txtGameStatus.setText("Continue playing ");
                            oldTxtCalculationsvalue = "Your point is " + points + "\n";
                            break;


                    }

                    return;
                }




                if(gameStatus == Status.PROCEED){

                    int diceSum = TollDice();

                    // After entering proceed case if your diceSum in this toll is same as your previous toll(points), you win !
                    if(diceSum == points) {

                        gameStatus = Status.WON;
                        txtGameStatus.setText("You Won!");
                        makeImgDiceInvisible();
                        makeBtnRestartGameVisible();
                    }

                    //Hit 7 in your new toll and you are out !
                    else if(diceSum ==SEVEN){

                        gameStatus = Status.LOST;
                        txtGameStatus.setText("You lost!");
                        makeImgDiceInvisible();
                        makeBtnRestartGameVisible();
                    }




                }





            }
        });  //Here ends On click image







        btnRestartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                gameStatus = Status.NOTSTARTEDYET;
                txtGameStatus.setText("");
                txtCalculations.setText("");
                oldTxtCalculationsvalue  = "";
                makeImgDiceVisible();
                makeBtnRestartGameInvisible();









            }
        });





    }



    private void makeImgDiceInvisible(){
        imgDice.setVisibility(View.INVISIBLE);
    }

    private void makeImgDiceVisible(){
        imgDice.setVisibility(View.VISIBLE);
    }

    private void makeBtnRestartGameInvisible(){
        btnRestartGame.setVisibility(View.INVISIBLE);
    }

    private void makeBtnRestartGameVisible(){
        btnRestartGame.setVisibility(View.VISIBLE);
    }



    private int TollDice(){

        int randDie1 = 1 + secureRandomNumbers.nextInt(6);
        int randDie2 = 1 + secureRandomNumbers.nextInt(6);

        int sum = randDie1 + randDie2;

        txtCalculations.setText(String.format(oldTxtCalculationsvalue + "You rolled %d + %d = %d%n", randDie1,randDie2,sum));
        return sum;


    }

}
