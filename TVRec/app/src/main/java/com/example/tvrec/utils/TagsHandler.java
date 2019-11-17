package com.example.tvrec.utils;

import android.content.Context;

import com.example.tvrec.model.Tag;
import com.example.tvrec.tags.TinyDB;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
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

    public static void readGlobalTagsFromTxt(Context context){
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
        tinydb.remove("userTags");
        if(!userTags.contains(tag)){
            userTags.add(new Tag(tag, 1.0/userTags.size()));
            tinydb.putListTags("userTags", userTags);
        }
        addGlobalTag(tag);
    }

    public void addGlobalTag(String tag){
        ArrayList<Tag> globalTags = tinydb.getListTags("keyWords");
        tinydb.remove("keyWords");
        if(!globalTags.contains(tag)){
            globalTags.add(new Tag(tag));
            tinydb.putListTags("keyWords", globalTags);
        }

    }

}
