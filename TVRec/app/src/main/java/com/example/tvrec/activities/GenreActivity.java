package com.example.tvrec.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tvrec.R;

public class GenreActivity extends AppCompatActivity{

    private String genre = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        Button comedyButton = findViewById(R.id.comedyButton);
        comedyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "Komedia";
                buttonHandler(genre);
            }
        });

        Button dramaButton = findViewById(R.id.dramaButton);
        dramaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "Dramat";
                buttonHandler(genre);
            }
        });
        Button westernButton = findViewById(R.id.westernButton);
        westernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "Western";
                buttonHandler(genre);
            }
        });

        Button horrorButton = findViewById(R.id.horrorButton);
        horrorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "Horror";
                buttonHandler(genre);
            }
        });
        Button actionButton = findViewById(R.id.actionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "akcji";
                buttonHandler(genre);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.info_setting) {
            goToInfo();
        }
        if (id == R.id.tags_setting) {
            goToTagsManagement();
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToInfo(){
        Intent i = new Intent(this, InfoActivity.class);
        startActivity(i);
        onPause();
    }
    public void goToTagsManagement(){
        Intent i = new Intent(this, TagsManagerActivity.class);
        startActivity(i);
        onPause();
    }

    private void buttonHandler(String genre){
        Intent intent = new Intent(this, ResultsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("GENRE", genre);
        intent.putExtras(bundle);
        startActivity(intent);
        onPause();
    }

}
