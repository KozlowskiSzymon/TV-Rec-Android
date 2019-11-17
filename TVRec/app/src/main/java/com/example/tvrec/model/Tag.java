package com.example.tvrec.model;

public class Tag {

    String word;
    Double wage;

    public Tag(String word, Double wage) {
        this.word = word;
        this.wage = wage;
    }

    public Tag(String word) {
        this.word = word;
        this.wage = 1.0;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }
}