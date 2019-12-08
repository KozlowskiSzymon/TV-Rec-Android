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
import com.example.tvrec.model.Tag;
import com.example.tvrec.utils.TagsHandler;

import java.util.ArrayList;
import java.util.List;

public class ChoiceActivity extends AppCompatActivity {

    List<Tag> tagList = new ArrayList<>();
    private TagsHandler tagsHandler;

    Tag tag1 = new Tag("tag1");
    Tag tag2 = new Tag("tag2");
    Tag tag3 = new Tag("tag3");
    Tag tag4 = new Tag("tag4");
    Tag tag5 = new Tag("tag5");
    Tag tag6 = new Tag("tag6");
    Tag tag7 = new Tag("tag7");
    Tag tag8 = new Tag("tag8");
    Tag tag9 = new Tag("tag9");
    Tag tag10 = new Tag("tag10");

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
        tagsHandler = new TagsHandler(this);
        tagList = tagsHandler.readUserTagsFromPrefs();
        setContentView(R.layout.activity_choice);

        Button choice1 = findViewById(R.id.choiceButton1);
        choice1.setOnClickListener(view -> {
            tagList.add(tag1);
        });
        Button choice2 = findViewById(R.id.choiceButton2);
        choice2.setOnClickListener(view -> {
            tagList.add(tag2);
        });
        Button choice3 = findViewById(R.id.choiceButton3);
        choice3.setOnClickListener(view -> {
            tagList.add(tag3);
        });
        Button choice4 = findViewById(R.id.choiceButton4);
        choice4.setOnClickListener(view -> {
            tagList.add(tag4);
        });
        Button choice5 = findViewById(R.id.choiceButton5);
        choice5.setOnClickListener(view -> {
            tagList.add(tag5);
        });
        Button choice6 = findViewById(R.id.choiceButton6);
        choice6.setOnClickListener(view -> {
            tagList.add(tag6);
        });
        Button choice7 = findViewById(R.id.choiceButton7);
        choice7.setOnClickListener(view -> {
            tagList.add(tag7);
        });
        Button choice8 = findViewById(R.id.choiceButton8);
        choice8.setOnClickListener(view -> {
            tagList.add(tag8);
        });
        Button choice9 = findViewById(R.id.choiceButton9);
        choice9.setOnClickListener(view -> {
            tagList.add(tag9);
        });
        Button choice10 = findViewById(R.id.choiceButton10);
        choice10.setOnClickListener(view -> {
            tagList.add(tag10);
        });

        Button btn = findViewById(R.id.readyButton);
        btn.setOnClickListener(view -> {
            tagsHandler.addUserTag(tagList);
            startActivity(intent);
            finish();
        });
    }


}
