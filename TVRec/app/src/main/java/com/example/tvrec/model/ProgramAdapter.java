package com.example.tvrec.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tvrec.R;

import java.util.ArrayList;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>{

    private ArrayList<Program> results;
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
        programViewHolder.approvedButton.setOnClickListener(view -> {
            program.getTags().forEach(tag -> {
                System.out.println(tag.getWord() + " " + tag.getWage());
            });
        });
        programViewHolder.rejectButton.setOnClickListener(view -> {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++reject");
        });


    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ProgramViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView channel;
        public Button approvedButton;
        public Button rejectButton;
        public ProgramViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            channel = v.findViewById(R.id.channel);
            approvedButton = v.findViewById(R.id.approveButton);
            rejectButton = v.findViewById(R.id.rejectButton);

        }
    }


    public ProgramAdapter(ArrayList<Program> results) {
        this.results = results;
    }

}
