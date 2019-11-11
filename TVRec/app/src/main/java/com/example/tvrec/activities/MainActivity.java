package com.example.tvrec.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.tvrec.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button beginButton = findViewById(R.id.beginButton);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                begin();
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

    public void begin(){
        Intent i = new Intent(this, ChoiceActivity.class);
        startActivity(i);
        onPause();
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
}
