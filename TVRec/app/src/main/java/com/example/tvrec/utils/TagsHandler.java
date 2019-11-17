package com.example.tvrec.utils;

import android.content.Context;

import com.example.tvrec.tags.TinyDB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

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
        ArrayList<String> userTags = new ArrayList<>();
        try {
            String word;
            Charset charSet = Charset.forName(CHARSET);
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(USER_TAGS_PATH),charSet));
            while ((word = reader.readLine()) != null) {
                userTags.add(word);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tinydb.putListString("userTags", userTags);
    }

    public ArrayList<String> readUserTagsFromPrefs(){
        ArrayList<String> toReturn = tinydb.getListString("userTags");
        for(String str: toReturn)
            System.out.println(str);
        return toReturn;
    }

    public ArrayList<String> readGlobalTagsFromPrefs(){
        ArrayList<String> toReturn = tinydb.getListString("keyWords");
        return toReturn;
    }

    public static void readGlobalTagsFromTxt(Context context){
        ArrayList<String> globalTags = new ArrayList<>();
        try {
            String word;
            Charset charSet = Charset.forName(CHARSET);
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(KEY_WORDS),charSet));
            while ((word = reader.readLine()) != null)
                globalTags.add(word);
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tinydb.putListString("keyWords", globalTags);
    }


    public void addUserTag(String tag){
        ArrayList<String> userTags = tinydb.getListString("userTags");
        tinydb.remove("userTags");
        if(!userTags.contains(tag)){
            userTags.add(tag);
            tinydb.putListString("userTags", userTags);
        }
        addGlobalTag(tag);
    }

    public void addGlobalTag(String tag){
        ArrayList<String> globalTags = tinydb.getListString("keyWords");
        tinydb.remove("keyWords");
        if(!globalTags.contains(tag)){
            globalTags.add(tag);
            tinydb.putListString("keyWords", globalTags);
        }

    }

}
