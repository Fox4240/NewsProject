package com.example.user.newsproject;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient extends Application{

    private static NewsAPI newsAPI;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://mikonatoruri.win/")//Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())//Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        newsAPI=retrofit.create(NewsAPI.class);//Создаем объект, при помощи которого будем выполнять запросы

    }

    public static NewsAPI getNewsAPI (){return newsAPI;}
}
