package com.example.tvrec.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
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
            ed.apply();
        }
        setContentView(R.layout.activity_choice);
    }


}
