package com.example.tvrec.tags;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.tvrec.model.Program;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ProgramTagger {

    private static final String CHARSET = "UTF-8";
    private static final String KEY_WORDS = "keyWords.txt";
    private ArrayList<String> keyWordsList = new ArrayList<>();

    public ProgramTagger(Context context) {
        try {
            String word;
            Charset charSet = Charset.forName(CHARSET);
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(KEY_WORDS),charSet));
            while((word = reader.readLine()) != null)
                keyWordsList.add(word);
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getTagsFromDescription(String description){
        ArrayList<String> textTags = new ArrayList<>();
        for (int i = 0; i < keyWordsList.size(); i++){
            if (description.contains(keyWordsList.get(i)))
                textTags.add(keyWordsList.get(i));
        }
        return textTags;
    }

    public void tagProgramsDescriptions(ArrayList<Program> programs){
        for (Program program: programs){
            program.setTags(getTagsFromDescription(program.getDescriptions()));
        }
    }
}




