package com.example.user.newsproject;

import com.example.user.newsproject.request.articlesData.ArticleDataResponse;
import com.example.user.newsproject.request.categories.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("list.php")
    Call<ArticleResponse> getArticles(@Query("category") String category);
    @GET("post.php")
    Call<ArticleDataResponse> getArticlesData(@Query("article") String article);

}
