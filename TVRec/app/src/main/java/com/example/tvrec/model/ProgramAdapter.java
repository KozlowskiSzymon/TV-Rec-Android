package com.example.tvrec.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tvrec.R;

import java.util.ArrayList;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>{

    ArrayList<Program> results;
    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_row, parent, false);

        return new ProgramViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder programViewHolder, int position) {

        Program program = results.get(position);

        programViewHolder.title.setText(program.title);
        programViewHolder.channel.setText(program.channel);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ProgramViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView channel;
        public ProgramViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            channel = v.findViewById(R.id.channel);

        }
    }


    public ProgramAdapter(ArrayList<Program> results) {
        this.results = results;
    }

}
