package com.example.tvrec.model;

import java.io.Serializable;

public class Tag implements Serializable, Comparable<Tag> {

    private String word;
    private Double wage;

    public Tag(){

    }

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

    @Override
    public int compareTo(Tag o) {
        return Double.compare(this.wage - o.getWage(), 0.0);
    }
}
