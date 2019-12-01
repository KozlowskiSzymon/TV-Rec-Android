package com.example.tvrec.utils;

import java.util.List;
import java.util.stream.Collectors;

import morfologik.stemming.polish.PolishStemmer;


public class DictionaryHandler {

    private PolishStemmer stemmer;


    public DictionaryHandler() {
        this.stemmer = new PolishStemmer();
    }

    public List<String> stemsOf(String word) {
        return stemmer.lookup(word).stream()
                .map((wd) -> wd.getStem().toString())
                .collect(Collectors.toList());
    }

    public List<String> tagsOf(String word) {
        return stemmer.lookup(word).stream()
                .map((wd) -> wd.getTag().toString())
                .collect(Collectors.toList());
    }
}
