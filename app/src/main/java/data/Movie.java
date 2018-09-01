package data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    private static final String _BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w500";
    @SerializedName("poster_path")
    private final String _RELATIVE_POSTER_PATH;
    @SerializedName("backdrop_path")
    private final String _RELATIVE_BACKDROP_PATH;
    @SerializedName("title")
    private final String _title;
    @SerializedName("original_title")
    private final String _original_title;
    @SerializedName("original_language")
    private final String _original_language;
    @SerializedName("overview")
    private final String _overview;
    @SerializedName("release_date")
    private final String _release_date;

    @SerializedName("vote_count")
    private final int _vote_count;
    @SerializedName("id")
    private final int _id;
    @SerializedName("vote_average")
    private final double _vote_average;
    @SerializedName("popularity")
    private final double _popularity;

    @SerializedName("video")
    private final boolean _video;
    @SerializedName("adult")
    private final boolean _adult;


    public Movie(
            String posterPath, String relative_backdrop_path,
            String title, String original_title,
            String original_language, String overview, String release_date,
            int vote_count, int id,
            double vote_average, double popularity,
            boolean video, boolean adult) {
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

    public Movie (Parcel in) {
        this._RELATIVE_POSTER_PATH = in.readString();
        this._RELATIVE_BACKDROP_PATH = in.readString();
        this._title = in.readString();
        this._original_title = in.readString();
        this._original_language = in.readString();
        this._overview = in.readString();
        this._release_date = in.readString();
        this._vote_count = in.readInt();
        this._id = in.readInt();
        this._vote_average = in.readDouble();
        this._popularity = in.readDouble();
        this._video = in.readInt() == 1;
        this._adult = in.readInt() == 1;
    }


    /* Getters and Setters for Above class properties */

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public static String getBasePosterPath() {
        return _BASE_POSTER_PATH;
    }

    public String get_RELATIVE_POSTER_PATH() {
        return _RELATIVE_POSTER_PATH;
    }

    public String get_RELATIVE_BACKDROP_PATH() {
        return _RELATIVE_BACKDROP_PATH;
    }

    public String get_title() {
        return _title;
    }

    public String get_original_title() {
        return _original_title;
    }

    public String get_original_language() {
        return _original_language;
    }

    public String get_overview() {
        return _overview;
    }

    public String get_release_date() {
        return _release_date;
    }

    public int get_vote_count() {
        return _vote_count;
    }

    public int get_id() {
        return _id;
    }

    public double get_vote_average() {
        return _vote_average;
    }

    public double get_popularity() {
        return _popularity;
    }

    public boolean is_video() {
        return _video;
    }

    public boolean is_adult() {
        return _adult;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._RELATIVE_POSTER_PATH); dest.writeString(this._RELATIVE_BACKDROP_PATH);
        dest.writeString(this._title); dest.writeString(this._original_title);
        dest.writeString(this._original_language); dest.writeString(this._overview); dest.writeString(_release_date);

        dest.writeInt(this._vote_count); dest.writeInt(this._id);
        dest.writeDouble(this._vote_average); dest.writeDouble(this._popularity);

        int iVideo = (this._video) ? 1 : 0;
        int iAdult = (this._adult) ? 1 : 0;

        dest.writeInt(iVideo);
        dest.writeInt(iAdult);
    }
}
