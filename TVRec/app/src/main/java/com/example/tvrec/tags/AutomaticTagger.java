package com.example.tvrec.tags;


import com.example.tvrec.model.Tag;
import com.example.tvrec.utils.DictionaryHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutomaticTagger {

    private DictionaryHandler dictionaryHandler;

    public AutomaticTagger() {
        this.dictionaryHandler = new DictionaryHandler();
    }

    public ArrayList<Tag> tagAutomatically(String description){
        ArrayList<Tag> tags = new ArrayList<>();
        ArrayList<Tag> toReturn = new ArrayList<>();
        description = description.replaceAll("\\.","");
        description = description.replaceAll("\\)","");
        description = description.replaceAll("\\(","");
        String[] wordsList=description.split(" ");//Split the word from String
        if (description.contains("Brak programu w bazie"))
            return toReturn;

        ArrayList<String> words = new ArrayList<>();
        for (String word: wordsList){
            if (word.length() > 2)
                if(dictionaryHandler.tagsOf(word).size() > 0)
                    if (dictionaryHandler.tagsOf(word).get(0).contains("subst")){
                        if(dictionaryHandler.stemsOf(word).size() > 0)
                            words.add(dictionaryHandler.stemsOf(word).get(0));
                    }
        }

        double wrc=1;//Variable for getting Repeated word count

        for(int i=0;i<words.size();i++)		//Outer loop for Comparison
        {
            for(int j=i+1;j<words.size();j++)	//Inner loop for Comparison
            {

                if(words.get(i).equals(words.get(j)))	//Checking for both strings are equal
                {
                    wrc=wrc+1;				//if equal increment the count
                    words.set(j, "0");			//Replace repeated words by zero
                }
            }
            if(words.get(i) !="0") {
                tags.add(new Tag(words.get(i), 1.0));
            }
            wrc=1;

        }
        Collections.sort(tags,Collections.reverseOrder());


        if(tags.size() > 10) {
            for (int i = 0; i < 10; i++) {
                toReturn.add(tags.get(i));
            }
        }else
            return tags;
        return toReturn;

    }
}

