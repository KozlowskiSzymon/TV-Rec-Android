package com.example.tvrec.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvrec.R;
import com.example.tvrec.model.Program;
import com.example.tvrec.model.ProgramAdapter;
import com.example.tvrec.tags.ProgramTagger;
import com.example.tvrec.tags.TagsComparer;
import com.example.tvrec.tvguide.TvGuidScrapper;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private ArrayList <Program> results = new ArrayList<>();
    ProgramTagger programTagger;
    TagsComparer tagsComparer;
    ProgressBar progressBar;
    RecyclerView layout;
    FrameLayout view;
    ProgramAdapter programAdapter;
    Thread getProgramsData;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ActionBar supBar = this.getSupportActionBar();
        supBar.setDisplayHomeAsUpEnabled(true);
        programTagger = new ProgramTagger(this);
        tagsComparer = new TagsComparer(this);
        view = findViewById((R.id.resultsLayout));
        layout = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar1);
        layout.setVisibility(View.GONE);
        layout.setLayoutManager(new LinearLayoutManager(this));

        getProgramsData = new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = getIntent().getExtras();
                String genre = (String) bundle.getSerializable("GENRE");

                results = getProgramsFromGenre(genre);
                programTagger.tagProgramsDescriptions(results);
                tagsComparer.setInRecommendedOrder(results);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        programAdapter = new ProgramAdapter(results);
                        layout.setAdapter(programAdapter);
                        if(results.size() == 0){
                            TextView error = new TextView(layout.getContext());
                            error.setText("Brak programów spełniających kryteria wyszukiwania");
                            layout.addView(error);
                        }
                        progressBar.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        getProgramsData.start();

    }


    private ArrayList<Program> getProgramsFromGenre(String genre){
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
}
