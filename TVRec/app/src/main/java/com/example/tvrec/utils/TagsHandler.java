package com.example.tvrec.utils;

import android.content.Context;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class TagsHandler {

    private static final String CHARSET = "UTF-8";
    private static final String USER_TAGS_PATH = "userTags.txt";
    private Boolean wasListChanged = false;
    private ArrayList<String> userTags = new ArrayList<>();

    public TagsHandler(Context context){

        try {
            String word;
            Charset charSet = Charset.forName(CHARSET);
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(USER_TAGS_PATH),charSet));
            while ((word = reader.readLine()) != null)
                userTags.add(word);
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getUserTags() {

        return userTags;
    }

    public Boolean addUserTag(String tag){
        if(!userTags.contains(tag)){
            userTags.add(tag);
            wasListChanged = true;
        }
        return false;
    }

    public Boolean removeUserTag(String tag){
        if (userTags.remove(tag)){
            wasListChanged = true;
            return true;
        }
        return false;
    }

    //TODO:czyto działa?
    public void saveUserTags(ArrayList<String> tags) {
        if (wasListChanged) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(USER_TAGS_PATH));
                for (String tag : tags) {
                    String line = new String((tag).getBytes(CHARSET));
                    writer.write(line);
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
