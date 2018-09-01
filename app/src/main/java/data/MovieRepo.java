package data;

import java.util.List;

public class MovieRepo {
    public String getTotal_pages() {
        return total_pages;
    }

    private String total_pages;
    private List<Movie> results;

    public MovieRepo(List<Movie> results, String total_pages) {
        this.results = results;
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }
}
