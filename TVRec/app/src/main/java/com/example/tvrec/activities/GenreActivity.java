package com.example.tvrec.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tvrec.R;
import com.example.tvrec.filmweb.FilmwebAPI;
import com.example.tvrec.model.Program;
import com.example.tvrec.tags.ProgramTagger;
import com.example.tvrec.tags.TagsComparer;
import com.example.tvrec.tvguide.TvGuidScrapper;

import java.io.Serializable;
import java.util.ArrayList;

public class GenreActivity extends AppCompatActivity{

    private String[] tvGuidUrls = {"https://www.telemagazyn.pl/",
            "https://www.telemagazyn.pl/?od=tv_puls#programTV",
            "https://www.telemagazyn.pl/?od=ttv#programTV",
            "https://www.telemagazyn.pl/?od=tvp_abc#programTV",
            "https://www.telemagazyn.pl/?od=zoom_tv#programTV",
            "https://www.telemagazyn.pl/?od=4fun_dance#programTV",
            "https://www.telemagazyn.pl/?od=epic_drama#programTV",
            "https://www.telemagazyn.pl/?od=canal_1#programTV",
            "https://www.telemagazyn.pl/?od=canal_discovery#programTV",
            "https://www.telemagazyn.pl/?od=hbo3#programTV",
            "https://www.telemagazyn.pl/?od=filmbox_extra#programTV",
            "https://www.telemagazyn.pl/?od=axn_white#programTV",
            "https://www.telemagazyn.pl/?od=cbs_europa#programTV",
            "https://www.telemagazyn.pl/?od=tvn_24#programTV",
            "https://www.telemagazyn.pl/?od=superstacja#programTV",
            "https://www.telemagazyn.pl/?od=polsat_sport#programTV",
            "https://www.telemagazyn.pl/?od=extreme_sports#programTV",
            "https://www.telemagazyn.pl/?od=polsat_doku#programTV",
            "https://www.telemagazyn.pl/?od=investigation_discovery#programTV",
            "https://www.telemagazyn.pl/?od=planete#programTV"};
    private String genre = "";
    ProgramTagger programTagger;
    ArrayList<Program> programs;
    TagsComparer tagsComparer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        Button comedyButton = findViewById(R.id.comedyButton);
        programTagger = new ProgramTagger(this);
        tagsComparer = new TagsComparer(this);
        comedyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "Komedia";
                buttonHandler();
            }
        });

        Button dramaButton = findViewById(R.id.dramaButton);
        dramaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "Dramat";
                buttonHandler();
            }
        });
        Button westernButton = findViewById(R.id.westernButton);
        westernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "Western";
                buttonHandler();
            }
        });

        Button horrorButton = findViewById(R.id.horrorButton);
        horrorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "Horror";
                buttonHandler();
            }
        });
        Button actionButton = findViewById(R.id.actionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "akcji";
                buttonHandler();
            }
        });

        Button documentButton = findViewById(R.id.documentButton);
        documentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "dokumentalny";
                buttonHandler();
            }
        });
    }

    private void buttonHandler(){
        programs = getProgramsFromGenre();
        programTagger.tagProgramsDescriptions(programs);
        tagsComparer.setInRecommendedOrder(programs);
        Intent intent = new Intent(this, ResultsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("RESULTS", programs);
        intent.putExtras(bundle);
        startActivity(intent);
        onPause();
    }

    private ArrayList<Program> getProgramsFromGenre(){
        Thread[] threads = new Thread[tvGuidUrls.length];
        ArrayList <Program> results = new ArrayList<>();
        int i = 0;
        for(String url: tvGuidUrls){
            threads[i++] = new Thread(new TvGuidScrapper(genre , url, results));
            threads[i - 1].start();
        }
        for (Thread thr: threads)
            try {
                thr.join();
            }catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        return results;
    }
}
