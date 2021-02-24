package com.munger.tp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    int etatDeLaCase = 0; //vide
    //difference entre les couleurs: Red, Yellow, Vide == 0, 1, 2
    int[] allCases = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] positionGagnantes = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    String jaiGagner = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playIn(View view) {
        if (jaiGagner.isEmpty()) {
            ImageView counter = (ImageView) view;
            int position = Integer.parseInt(counter.getTag().toString());
            allCases[position] = etatDeLaCase;
            counter.setTranslationY(-1500);
            caseGagnante(counter);
            counter.animate().translationYBy(1500).rotation(36000).setDuration(250);
            algoGagnant();
        }
    }

    private void caseGagnante(ImageView counter) {
        if (etatDeLaCase == 0) {
            counter.setImageResource(R.drawable.yellow);
            etatDeLaCase = 1;
        } else {
            counter.setImageResource(R.drawable.red);
            etatDeLaCase = 0;
        }
    }

    private void algoGagnant() {
        for (int[] laPositionGagnante : positionGagnantes) {
            if (haveAWinner(laPositionGagnante)) {
                if (etatDeLaCase == 1) {
                    jaiGagner = "Jaune à gagné";
                } else {
                    jaiGagner = "Rouge à gagné";
                }
                intializeButtonText(View.VISIBLE);
            }
        }
        if (matchNull()) {
            jaiGagner = "Match null";
            intializeButtonText(View.VISIBLE);
        }
    }

    private boolean matchNull() {
        int nbCaseRempli = 0;
        for (int i = 0; i < allCases.length; i++) {
            if (allCases[i] != 2) {
                nbCaseRempli++;
            }
        }
        if (nbCaseRempli == 9) {
            return true;
        } else {
            return false;
        }
    }

    private boolean haveAWinner(int[] laPositionGagnante) {
        return allCases[laPositionGagnante[0]] == allCases[laPositionGagnante[1]] && allCases[laPositionGagnante[1]] == allCases[laPositionGagnante[2]] && allCases[laPositionGagnante[0]] != 2;
    }

    private void intializeButtonText(int visibility) {
        TextView textView = findViewById(R.id.myText);
        textView.setText(jaiGagner);
        textView.setVisibility(visibility);

        Button button = findViewById(R.id.myButton);
        button.setVisibility(visibility);
    }

    public void rejouer(View view) {
        etatDeLaCase = 0;
        jaiGagner = "";
        intializeButtonText(View.INVISIBLE);
        resetGameField();
    }

    private void resetGameField() {
        GridLayout gridLayout = findViewById(R.id.tableau);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
        for (int i = 0; i < allCases.length; i++) {
            allCases[i] = 2;
        }
    }
}