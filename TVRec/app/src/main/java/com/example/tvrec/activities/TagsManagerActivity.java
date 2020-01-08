package com.example.tvrec.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvrec.R;
import com.example.tvrec.utils.TagsHandler;
import com.google.android.material.textfield.TextInputEditText;

public class TagsManagerActivity extends AppCompatActivity {

    private TagsHandler tagsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_manager);
        ActionBar supBar = this.getSupportActionBar();
        supBar.setDisplayHomeAsUpEnabled(true);
        tagsHandler = new TagsHandler(this);
        final TextInputEditText userInput = findViewById(R.id.userTagInput);
        final TextInputEditText globalInput = findViewById(R.id.globalTagInput);
        Button userAddButton = findViewById(R.id.userTagAddButton);
        Button globalAddButton = findViewById(R.id.globalTagAddButton);
        userAddButton.setOnClickListener(view -> {
            tagsHandler.addUserTag(userInput.getText().toString());
            userInput.setText("");
        });

        globalAddButton.setOnClickListener(view -> {
            tagsHandler.addGlobalTag(globalInput.getText().toString());
            globalInput.setText("");
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}
