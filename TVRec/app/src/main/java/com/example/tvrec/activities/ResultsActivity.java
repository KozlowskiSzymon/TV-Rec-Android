package com.example.tvrec.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tvrec.R;
import com.example.tvrec.model.Program;
import com.example.tvrec.model.ProgramAdapter;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private ArrayList <Program> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle bundle = getIntent().getExtras();
        results = (ArrayList <Program>) bundle.getSerializable("RESULTS");
        RecyclerView layout = findViewById(R.id.resultsLayout);
        layout.setLayoutManager(new LinearLayoutManager(this));
        layout.setAdapter(new ProgramAdapter(results));

    }
}
