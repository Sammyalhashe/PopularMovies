package data;

public class Movie {
    private static final String _BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w185";
    private final String _RELATIVE_POSTER_PATH;
    private final String _RELATIVE_BACKDROP_PATH;
    private final String _title, _original_title;
    private final String _original_language, _overview, _release_date;

    private final int _vote_count, _id;
    private final double _vote_average, _popularity;

    private final boolean _video, _adult;


    public Movie(
            String posterPath, String backdropPath,
            String title, String original_title,
            String original_language, String overview, String release_date,
            int vote_count, int id,
            double vote_average, double popularity,
            boolean video, boolean adult,
            String relative_backdrop_path) {
        this._RELATIVE_POSTER_PATH = posterPath;
        this._RELATIVE_BACKDROP_PATH = relative_backdrop_path;
        this._title = title;
        this._original_title = original_title;
        this._original_language = original_language;
        this._overview = overview;
        this._release_date = release_date;
        this._vote_count = vote_count;
        this._id = id;
        this._vote_average = vote_average;
        this._popularity = popularity;
        this._video = video;
        this._adult = adult;
    }





}
