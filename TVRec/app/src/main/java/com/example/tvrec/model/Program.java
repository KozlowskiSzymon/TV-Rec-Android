package com.example.tvrec.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Program implements Comparable<Program>, Serializable {

    public String title;
    public String yearOfProduction;
    public String channel;
    public String score;
    public String descriptions;
    private int tagsCount;
    ArrayList<Tag> tags;
    private double wage;

    public Program(String title, String yearOfProduction, String channel, String score, String descriptions) {
        this.title = title;
        this.yearOfProduction = yearOfProduction;
        this.channel = channel;
        this.score = score;
        this.descriptions = descriptions;
        this.tagsCount = 0;
        this.tags = new ArrayList<>();
    }

    public int getTagsCount() {
        return tagsCount;
    }

    public void setTagsCount(int tagsCount) {
        this.tagsCount = tagsCount;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public int compareTo(Program o) {
        return Double.compare(this.wage - o.getWage(), 0.0);
    }
}