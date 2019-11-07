package com.example.tvrec.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tvrec.R;

public class ChoiceActivity extends AppCompatActivity {

    private int onClickCounter = 0;
    Button firstChoice;
    Button secondChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this, GenreActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }
        setContentView(R.layout.activity_choice);

        firstChoice = findViewById(R.id.firstChoiceButton);
        firstChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageOnButtons(onClickCounter);
                onClickCounter++;
            }
        });

        secondChoice = findViewById(R.id.secondChoiceButton);
        secondChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageOnButtons(onClickCounter);
                onClickCounter++;
            }
        });
    }

    public void changeImageOnButtons(int onClickCounter){
        Intent i = new Intent(this, GenreActivity.class);
        if(onClickCounter == 0){
            firstChoice.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.num2, null));
            secondChoice.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.num2, null));
        }
        else{
            if(onClickCounter == 1){
                firstChoice.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.num3, null));
                secondChoice.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.num3, null));
            }
            else{
                if(onClickCounter == 2){
                    startActivity(i);
                    finish();
                }
            }

        }

    }
}
