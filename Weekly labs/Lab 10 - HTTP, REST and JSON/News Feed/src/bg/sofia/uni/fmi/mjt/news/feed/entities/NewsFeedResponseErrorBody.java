package bg.sofia.uni.fmi.mjt.news.feed.entities;

import com.google.gson.annotations.SerializedName;

public record NewsFeedResponseErrorBody(String status, String code,
                                        @SerializedName("codeErrorMessage") String message) {
}