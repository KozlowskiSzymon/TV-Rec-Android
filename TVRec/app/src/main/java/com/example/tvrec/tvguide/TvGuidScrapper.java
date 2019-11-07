package com.example.tvrec.tvguide;

import com.example.tvrec.filmweb.FilmwebAPI;
import com.example.tvrec.model.Program;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import static com.example.tvrec.utils.DocumentHandler.getDocumentFromPage;

public class TvGuidScrapper implements Runnable{

    private ArrayList<String[]> dataVectors = new ArrayList<>();
    private String genre;
    private String urlString;
    ArrayList<Program> programs;
    FilmwebAPI filmwebAPI = new FilmwebAPI();

    public TvGuidScrapper(String genre, String urlString, ArrayList <Program> programs){
        this.genre = genre;
        this.urlString = urlString;
        this.programs = programs;
    }

    /**
     * Function getting all info about single program
     *
     * @param url URL of the program with specified data
     * @return table of useful data as title, country the film was made in, date of creation, genre, producer, score, channel
     */
    public static String[] getDataAboutProgram(String url){
        Document doc = getDocumentFromPage(url);
        String title = doc.getElementsByClass("dwieKolumny prawaZnika").select("meta[itemprop = headline]").attr("content");
        String score = doc.getElementsByClass("ocena-spolecznosci").text();
        String channel = doc.getElementsByClass("emisjaSzczegoly").select("a:not(.emisjaOdcinek)").get(0).text();
        if (title.equals("")){
            Document newDoc = getDocumentFromPage(makeUrl(doc.getElementsByClass("odcinekKontener").select("a").attr("href")));
            title = newDoc.getElementsByClass("dwieKolumny prawaZnika").select("meta[itemprop = headline]").attr("content");
            score = newDoc.getElementsByClass("ocena-spolecznosci").text();
        }
        String genre = doc.getElementsByClass("informacje").select("meta[itemprop = genre]").attr("content");
        String producer = doc.getElementsByClass("informacje").select("meta[itemprop = producer]").attr("content");
        String date = doc.getElementsByClass("belkaInfo").select("meta[itemprop = dateCreated]").attr("content");
        String country = doc.getElementsByClass("belkaInfo").select("meta[itemprop = contentLocation]").attr("content");
        String[] table = {title, country, date, genre, producer, score, channel};
        return table;
    }

    /**
     *  Function selects the genre user wants from all programs from TV guide
     *
     * @param genre of programs user want to watch
     * @param allElementsFromPage every program form one page of TV guide
     * @return collection of genre selected elements
     */
    public static Elements getGenreSelectedElements(String genre, Elements allElementsFromPage){
        Elements genreSelectedElements = new Elements();
        for(Element el : allElementsFromPage){
            if(el.text().contains(genre)){
                genreSelectedElements.add(el.parent().parent().parent());
            }
        }
        if(genreSelectedElements.size() == 0)
            return null;
        return genreSelectedElements;
    }

    /**
     *  Function that creates URLs which allow to access more info for collection of elements
     *
     * @param genreSelectedElements data about every program selected by genre
     * @return table of URLs with data after you click on program
     */
    public static String[] getNewUrlForEveryElement(Elements genreSelectedElements){
        String[] tableOfNewUrls = new String[genreSelectedElements.size()];
        int i =0;
        for (Element el : genreSelectedElements){
            String newUrl = makeUrl(el.select("a").attr("href"));
            tableOfNewUrls[i++] = newUrl;
        }
        return tableOfNewUrls;
    }

    /**
     * Function generates one new URL
     *
     * @param appendix rest of the URL
     * @return whole URL that appears after clicking on program
     */
    public static String makeUrl(String appendix){
        String newUrl = "https://www.telemagazyn.pl" + appendix;
        return newUrl;
    }

    @Override
    public void run() {
        Document document = getDocumentFromPage(urlString); // get whole page
        //"progKontener" - class with all played programs listed
        Elements info = document.getElementsByClass("progKontener").select("span.info");
        Elements genreSelectedElements = getGenreSelectedElements(genre,info); // get only the genre you want
        if (genreSelectedElements != null){
            String[] newUrls = getNewUrlForEveryElement(genreSelectedElements);// open every site to get more info
            //synchronized (dataVectors) {
            for (String str : newUrls) {
                dataVectors.add(getDataAboutProgram(str));
            }
            for (String[] vector: dataVectors) {
                programs.add(filmwebAPI.getProgramWithDesriptions(vector));
            }
            //}
        }

    }
}
