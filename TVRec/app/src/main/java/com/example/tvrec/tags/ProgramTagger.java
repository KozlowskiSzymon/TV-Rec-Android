package com.example.tvrec.tags;

import android.content.Context;

import com.example.tvrec.model.Program;
import com.example.tvrec.model.Tag;
import com.example.tvrec.utils.TagsHandler;

import java.util.ArrayList;

public class ProgramTagger {

    private ArrayList<Tag> keyWordsList;
    private TagsHandler tagsHandler;
    private AutomaticTagger automaticTagger;

    public ProgramTagger(Context context) {
        tagsHandler = new TagsHandler(context);
        keyWordsList = tagsHandler.readGlobalTagsFromPrefs();
        automaticTagger = new AutomaticTagger();
    }

    private ArrayList<Tag> getTagsFromDescription(String description){
        ArrayList<Tag> textTags = new ArrayList<>();
//        textTags = automaticTagger.tagAutomatically(description);
        //tagowanie z listy globalnej
        for (Tag tag: keyWordsList){
            if (description.contains(tag.getWord())) {
                    textTags.add(tag);
            }
        }


        return textTags;
    }

    public void tagProgramsDescriptions(ArrayList<Program> programs){
        for (Program program: programs){
            program.setTags(getTagsFromDescription(program.getDescriptions()));
        }
    }
}




