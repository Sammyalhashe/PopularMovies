package data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieTrailerRepo {

    public class MovieTrailer {

        @SerializedName("id")
        private final String id;
        @SerializedName("iso_639_1")
        private final String iso_639_1;
        @SerializedName("iso_3166_1")
        private final String iso_3166_1;
        @SerializedName("key")
        private final String key;
        @SerializedName("name")
        private final String name;
        @SerializedName("site")
        private final String site;
        @SerializedName("size")
        private final int size;
        @SerializedName("type")
        private final String type;

        public MovieTrailer(String id, String iso_639_1, String iso_3166_1, String key, String name, String site, int size, String type) {
            this.id = id;
            this.iso_639_1 = iso_639_1;
            this.iso_3166_1 = iso_3166_1;
            this.key = key;
            this.name = name;
            this.site = site;
            this.size = size;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public String getIso_639_1() {
            return iso_639_1;
        }

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getSite() {
            return site;
        }

        public int getSize() {
            return size;
        }

        public String getType() {
            return type;
        }
    }

    @SerializedName("results")
    private final List<MovieTrailer> results;

    public List<MovieTrailer> getResults() {
        return results;
    }

    public MovieTrailerRepo(List<MovieTrailer> results) {
        this.results = results;
    }
}
