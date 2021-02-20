package data;

import java.util.ArrayList;
import java.util.List;

public class MovieRepo {
    public String getTotal_pages() {
        return total_pages;
    }

    private String total_pages;
    private ArrayList<Movie> results;

    public MovieRepo(ArrayList<Movie> results, String total_pages) {
        this.results = results;
        this.total_pages = total_pages;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }
}
