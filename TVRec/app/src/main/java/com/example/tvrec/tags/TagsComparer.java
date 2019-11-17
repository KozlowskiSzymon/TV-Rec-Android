package com.example.tvrec.tags;


import android.content.Context;

import com.example.tvrec.model.Program;
import com.example.tvrec.utils.TagsHandler;

import java.util.ArrayList;
import java.util.Collections;

public class TagsComparer {

    private TagsHandler tagsHandler;

    public TagsComparer(Context context) {
        this.tagsHandler = new TagsHandler(context);
    }

    public void setInRecommendedOrder(ArrayList<Program> taggedPrograms){
        for (Program program: taggedPrograms)
            countMatchingTags(program);
        Collections.sort(taggedPrograms,Collections.reverseOrder());
    }

    private Program countMatchingTags(Program program){
        ArrayList<String> userTags = tagsHandler.readUserTagsFromPrefs();
        int count = 0;
        for (String programTag: program.getTags()){
            if(userTags.contains(programTag)){
                count++;
            }

        }
        program.setTagsCount(count);
        return program;
    }

}