package com.example.rockpaperscissors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_rock, button_paper, button_scissor;
// Zmienne przechowujące wybory użytkownika/komputera
    private static Selection user_selection = Selection.SCISSOR;
    private static Selection cpu_selection = Selection.ROCK;

    private static boolean user_winner = true;
    private static boolean match_draw = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Przypisanie method setOnClickListener do przycisków
        button_rock = findViewById(R.id.button_rock);
        button_paper = findViewById(R.id.button_paper);
        button_scissor = findViewById(R.id.button_scissor);

        button_rock.setOnClickListener(this);
        button_paper.setOnClickListener(this);
        button_scissor.setOnClickListener(this);
    }
    //
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_rock:
                user_selection = Selection.ROCK;
                break;

            case R.id.button_paper:
                user_selection = Selection.PAPER;
                break;

            case R.id.button_scissor:
                user_selection = Selection.SCISSOR;
                break;
        }

        proceed();
    }
    private void proceed() {
        cpu_selection = getRandomCPUSelection();
        checkWinner();
        showWinner();
    }
    //Metoda generująca losowa liczbe 1-3 która odpowiada kolejno kamień,papier,nożyce
    private Selection getRandomCPUSelection() {
        int random = new Random().nextInt(3);

        switch (random){
            case 0: return Selection.ROCK;
            case 1: return Selection.PAPER;
            case 2: return Selection.SCISSOR;
        }
        return Selection.ROCK;
    }
    //metoda sprawdza czy wygrał użytkowik, komputer lub czy był remis
    private void checkWinner() {

        if(user_selection == cpu_selection){
            match_draw = true;
            return;
        }
        if(user_selection == Selection.ROCK){
            if(cpu_selection == Selection.PAPER){
                user_winner = false;
                return;
            }
            else if(cpu_selection == Selection.SCISSOR){
                user_winner = true;
                return;
            }
        }
        if(user_selection == Selection.PAPER){
            if(cpu_selection == Selection.SCISSOR){
                user_winner = false;
                return;
            }
            else if(cpu_selection == Selection.ROCK){
                user_winner = true;
                return;
            }
        }
        if(user_selection == Selection.SCISSOR){
            if(cpu_selection == Selection.ROCK){
                user_winner = false;
                return;
            }
            else if(cpu_selection == Selection.PAPER){
                user_winner = true;
                return;
            }
        }

        Log.e("cpu selected",cpu_selection.toString());
        Log.e("user selected",user_selection.toString());
    }
    // Metoda wyświtlająca wynik gry
    private void showWinner() {

        String line1 = "USER selection : "+user_selection;
        String line2 = "CPU selection : "+cpu_selection;
        String result = getResultString();

        String message = line1 + "\n" + line2 + "\nResult: " + result;

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Result")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //Kasuje wyniki. Przygotowanie do następnej gry
                        resetValues();
                    }
                })
                .show();
    }
    private void resetValues() {
        user_selection = Selection.SCISSOR;
        cpu_selection = Selection.ROCK;

        user_winner = true;
        match_draw = false;
        Log.e(MainActivity.class.getName(), "values reset successful");
    }
    private String getResultString() {

        String result;
        if(match_draw == true){
            result = "Match draw! Let's go again";
        }
        else{
            if(user_winner)
                result = "You win!";
            else
                result = "You loose!!!";
        }

        return result;
    }
}