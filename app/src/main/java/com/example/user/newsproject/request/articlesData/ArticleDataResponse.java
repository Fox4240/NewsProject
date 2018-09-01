package com.example.user.newsproject.request.articlesData;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleDataResponse {
    @SerializedName("team1")
    @Expose
    private String team1;
    @SerializedName("team2")
    @Expose
    private String team2;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("tournament")
    @Expose
    private String tournament;
    @SerializedName("article")
    @Expose
    private List<Article> article = null;
    @SerializedName("prediction")
    @Expose
    private String prediction;

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public List<Article> getArticle() {
        return article;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}
