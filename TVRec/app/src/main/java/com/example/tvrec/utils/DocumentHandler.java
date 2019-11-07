package com.example.tvrec.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DocumentHandler {

    /**
     * Function getting document from new URL - document with more info
     *
     * @param urlString URL of specific program of the genre user wants
     * @return whole document
     */
    public static Document getDocumentFromPage(String urlString){
        Connection connection = Jsoup.connect(urlString);
        Document document = new Document("");
        try {
            document = connection.get();
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
        return document;

    }
}