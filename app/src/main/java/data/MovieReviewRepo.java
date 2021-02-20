package data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieReviewRepo {

    public class MovieReview {
        @SerializedName("author")
        private final String author;
        @SerializedName("content")
        private final String content;
        @SerializedName("id")
        private final String id;
        @SerializedName("url")
        private final String url;

        public MovieReview(
                String author,
                String content,
                String id,
                String url
        ) {
            this.author = author;
            this.content = content;
            this.id = id;
            this.url = url;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }
    }

    @SerializedName("results")
    private List<MovieReview> results;

    public MovieReviewRepo(List<MovieReview> results) { this.results = results; }

    public List<MovieReview> getResults() {
        return results;
    }
}
