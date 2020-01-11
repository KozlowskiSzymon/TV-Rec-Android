package com.example.tvrec.utils;

import android.content.Context;

import com.example.tvrec.model.Tag;
import com.example.tvrec.tags.TinyDB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TagsHandler {


    private static final String CHARSET = "UTF-8";
    private static final String USER_TAGS_PATH = "userTags.txt";
    private static final String KEY_WORDS = "keyWords.txt";
    private static Context context;
    private static TinyDB tinydb;


    public TagsHandler(Context context){
        this.context = context;
        tinydb = new TinyDB(context);
    }

    public static void initLists(Context context){
        TagsHandler handler = new TagsHandler(context);
        handler.readGlobalTagsFromTxt(context);
        handler.readUserTagsFromTxt(context);
    }

    public static void readUserTagsFromTxt(Context context){
        ArrayList<Tag> userTags = new ArrayList<>();
        try {
            String word;
            Charset charSet = Charset.forName(CHARSET);
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(USER_TAGS_PATH),charSet));
            while ((word = reader.readLine()) != null) {
                userTags.add(new Tag(word));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tinydb.putListTags("userTags", userTags);
    }

    public ArrayList<Tag> readUserTagsFromPrefs(){
        return tinydb.getListTags("userTags");
    }

    public ArrayList<Tag> readGlobalTagsFromPrefs(){
        return tinydb.getListTags("keyWords");
    }

    private static void readGlobalTagsFromTxt(Context context){
        ArrayList<Tag> globalTags = new ArrayList<>();
        try {
            String word;
            Charset charSet = Charset.forName(CHARSET);
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(KEY_WORDS),charSet));
            while ((word = reader.readLine()) != null)
                globalTags.add(new Tag(word));
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tinydb.putListTags("keyWords", globalTags);
    }


    public void addUserTag(String tag){
        ArrayList<Tag> userTags = tinydb.getListTags("userTags");
        Boolean isOnList = false;
        tinydb.remove("userTags");
        for (Tag tag1 : userTags) {
            if (tag1.getWord().equals(tag))
                isOnList = true;
        }
        if (!isOnList){
            userTags.add(new Tag(tag));
            tinydb.putListTags("userTags", userTags);
        }
        addGlobalTag(tag);
    }

    public void addUserTag(List<Tag> list){
        ArrayList<Tag> userTags = tinydb.getListTags("userTags");
        tinydb.remove("userTags");
        for (Tag tag : list) {
            Boolean isOnList = false;
            for (Tag listTag : userTags){
                if (listTag.getWord().equals(tag.getWord()))
                    isOnList = true;
            }
            if (!isOnList) {
                userTags.add(tag);
            }
        }
        tinydb.putListTags("userTags", userTags);
    }

    public void addTagsFromApprovedRecommendation(List<Tag> list){
        ArrayList<Tag> userTags = tinydb.getListTags("userTags");
        tinydb.remove("userTags");
        if(list != null) {
            for (Tag tag : list) {
                if (listContains(userTags, tag)) {
                    for (Tag userTag : userTags)
                        if (userTag.getWord().equals(tag.getWord()))
                            userTag.setWage(userTag.getWage() + 1);
                } else {
                    userTags.add(tag);
                }

            }
            tinydb.putListTags("userTags", userTags);
        }
    }

    private boolean listContains(List<Tag> list, Tag tag){

        for (Tag listTag : list){
            if (listTag.getWord().equals(tag.getWord()))
                return true;
        }
        return false;
    }

    public void removeTagsFromRejectedRecommendation(List<Tag> list){
        ArrayList<Tag> userTags = tinydb.getListTags("userTags");
        tinydb.remove("userTags");
        for (Tag tag : list) {
            for (Tag listTag : userTags)
            if (listTag.getWord().equals(tag.getWord())) {
                System.out.println(listTag.getWord() + " " + listTag.getWage());
                double wage = listTag.getWage();
                if (wage == 1.0)
                    userTags.remove(listTag);
                else
                    listTag.setWage(listTag.getWage() - 1.0);
            }
        }
        tinydb.putListTags("userTags", userTags);
    }

    public void addGlobalTag(String tag){
        ArrayList<Tag> globalTags = tinydb.getListTags("keyWords");
        tinydb.remove("keyWords");
        if(!globalTags.contains(tag)){
            globalTags.add(new Tag(tag));
            tinydb.putListTags("keyWords", globalTags);
        }

    }

    public void addGlobalTag(ArrayList<Tag> list) {
        ArrayList<Tag> globalTags = tinydb.getListTags("keyWords");
        tinydb.remove("keyWords");
        list.forEach(tag -> {
            if(!globalTags.contains(tag)){
                globalTags.add(tag);
            }
        });
        tinydb.putListTags("keyWords", globalTags);
    }

}
