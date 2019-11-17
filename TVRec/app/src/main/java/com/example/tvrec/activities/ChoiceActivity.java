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
import com.example.tvrec.utils.TagsHandler;

public class ChoiceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        final Intent intent = new Intent(this, GenreActivity.class);

        if(pref.getBoolean("activity_executed", false)){
            startActivity(intent);
            finish();
        } else {
            TagsHandler.initLists(this);
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.apply();
        }
        setContentView(R.layout.activity_choice);
        Button btn = findViewById(R.id.firstButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                finish();
            }
        });
    }


}
