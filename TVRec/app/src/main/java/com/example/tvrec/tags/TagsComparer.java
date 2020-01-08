package com.example.tvrec.tags;


import android.content.Context;
import android.widget.ProgressBar;

import com.example.tvrec.model.Program;
import com.example.tvrec.model.Tag;
import com.example.tvrec.utils.DictionaryHandler;
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
            countWage(program);
        Collections.sort(taggedPrograms,Collections.reverseOrder());
        for (Program pg: taggedPrograms)
            System.out.println(pg.title +" " + pg.getWage());
    }

    private Program countWage(Program program){
        ArrayList<Tag> userTags = tagsHandler.readUserTagsFromPrefs();
        int count = 0;
        double wage = 0.0;
        if(program.getTags() != null) {
            for (Tag programTag : program.getTags()) {
                for (Tag userTag : userTags) {
                    if (programTag.getWord().equals(userTag.getWord())) {
                        count++;
                        wage += userTag.getWage();
                    }
                }

            }
        }

        program.setTagsCount(count);
        program.setWage(wage);
        return program;
    }

}