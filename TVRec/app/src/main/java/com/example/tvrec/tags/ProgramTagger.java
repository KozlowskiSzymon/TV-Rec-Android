package com.example.tvrec.tags;

import android.content.Context;

import com.example.tvrec.model.Program;
import com.example.tvrec.model.Tag;
import com.example.tvrec.utils.TagsHandler;

import java.util.ArrayList;

public class ProgramTagger {

    private ArrayList<Tag> keyWordsList;
    TagsHandler tagsHandler;

    public ProgramTagger(Context context) {
        tagsHandler = new TagsHandler(context);
        keyWordsList = tagsHandler.readGlobalTagsFromPrefs();
    }

    private ArrayList<Tag> getTagsFromDescription(String description){
        ArrayList<Tag> textTags = new ArrayList<>();
        for (int i = 0; i < keyWordsList.size(); i++){
            if (description.contains(keyWordsList.get(i).getWord()))
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




