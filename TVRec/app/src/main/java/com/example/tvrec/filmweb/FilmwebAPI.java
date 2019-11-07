package com.example.tvrec.filmweb;

import com.example.tvrec.model.Program;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.tvrec.utils.DocumentHandler.getDocumentFromPage;


public class FilmwebAPI {
    public static void main(String[] args) {

    }

    /**
     * Function getting every description of wanted program on Filmweb
     *
     * @return collection of descriptions
     */

    public ArrayList<Program> getProgramsWithDescriptions(ArrayList<String[]> programs){
        ArrayList<Program> programListToReturn = new ArrayList<>();
        for (String[] program: programs) {
            ArrayList<String> descs = getFilmWebDescriptions(program[0], program[2]);
            StringBuilder desc = new StringBuilder();
            for (String str : descs)
                desc.append(" ").append(str);
            programListToReturn.add(new Program(program[0], program[2], program[6], program[5], desc.toString()));
        }
        return programListToReturn;
    }

    public Program getProgramWithDesriptions(String[] program){
        ArrayList<String> descs = getFilmWebDescriptions(program[0], program[2]);
        StringBuilder desc = new StringBuilder();
        for (String str : descs)
            desc.append(" ").append(str);
        return new Program(program[0], program[2], program[6], program[5], desc.toString());
    }

    public static ArrayList<String> getFilmWebDescriptions(String title, String productionDate){
        String[] fwSearchEffects = getFWSearchEffects(title).split("\\\\a");
        String[] programId = getProgramID(fwSearchEffects, title, productionDate);
        String url;
        ArrayList<String> descriptionsToReturn = new ArrayList<>();
        if(programId != null) {
            url = makeFilmwebDescriptionsUrl(title, productionDate, programId);
            Document doc = getDocumentFromPage(url);
            Elements descs =  doc.getElementsByClass("text");
            for (Element desc: descs)
                descriptionsToReturn.add(desc.text());
        }
        else
            descriptionsToReturn.add("Brak programu w bazie.");
        return descriptionsToReturn;
    }


    /**
     * Function makes FilmWeb URL where we can get descriptions of program
     *
     * @param title title of program we are looking for
     * @param year  year of production
     * @param ID Id of the program on FilmWeb and is it series/film
     * @return URL ready for connection
     */
    private static String makeFilmwebDescriptionsUrl(String title, String year, String[] ID){
        String tempTitle = title.replaceAll(" ", "+");
        String program;
        if (ID[0].equals("f"))
            program = "/film/";
        else
            program = "/serial/";

        return "http://www.filmweb.pl" + program + tempTitle + "-" + year + "-" + ID[1] + "/descs";
    }

    /**
     * Function gets Id of wanted program and checks if it is a film or series
     *
     * @param fwSearchEffects list of tokenized look up results
     * @param title title of the program we are looking for
     * @param year year of production
     * @return returns table with: [0] s - series/ f - film
     *                             [1] ID
     */
    private static String[] getProgramID(String[] fwSearchEffects, String title, String year){
        for (String searchEffect : fwSearchEffects){
            if(effectContains(searchEffect, title) && effectContains(searchEffect, year)){
                String[] splittedEffect = searchEffect.split("\\\\c");
                return new String[]{splittedEffect[0], splittedEffect[1]};
            }
        }
        return null;
    }

    /**
     * Function checks if single search effect contains title and year we are looking for,
     * simply checks whether it's our program
     *
     * @param effect String representing effect of looking up in FilmWeb
     * @param data checking for this fraze
     * @return true if contains
     */
    private static boolean effectContains(String effect, String data){
        return effect.contains(data);
    }

    /**
     * Function getting xml code from FilmWeb with searching results
     *
     * @param title title of the program we are looking for
     * @return returns xml as String
     */
    private static String getFWSearchEffects(String title){
        String url = makeFilmwebSearchUrl(title);
        Connection connection = Jsoup.connect(url);
        Document document = new Document("");
        try {
            document = connection.get();
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
        return document.select("body").text();
    }

    /**
     * Function makes URL to live search FilmWeb
     *
     * @param title title of the program we are looking for
     * @return returns ready URL
     */
    private static String makeFilmwebSearchUrl(String title){
        return "http://www.filmweb.pl/search/live?q=" + title;
    }

}
