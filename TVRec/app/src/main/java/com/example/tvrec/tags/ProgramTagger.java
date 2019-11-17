package com.example.tvrec.tags;

import android.content.Context;

import com.example.tvrec.model.Program;
import com.example.tvrec.utils.TagsHandler;

import java.util.ArrayList;

public class ProgramTagger {

    private ArrayList<String> keyWordsList;
    TagsHandler tagsHandler;

    public ProgramTagger(Context context) {
        tagsHandler = new TagsHandler(context);
        keyWordsList = tagsHandler.readGlobalTagsFromPrefs();

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




