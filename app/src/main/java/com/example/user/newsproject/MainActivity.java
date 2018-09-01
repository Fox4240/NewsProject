package com.example.user.newsproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.newsproject.request.articlesData.ArticleDataResponse;
import com.example.user.newsproject.request.categories.ArticleResponse;
import com.example.user.newsproject.request.categories.Event;

import java.security.acl.Group;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.user.newsproject.InternetConnection.disconectMessage;
import static com.example.user.newsproject.InternetConnection.isOnline;

public class MainActivity extends AppCompatActivity {

    Spinner categoriesListSpinner;
    RecyclerView articleList;
    TicketsListAdapter mTicketsListAdapter;
    ProgressBar newsProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesListSpinner= (Spinner) findViewById(R.id.spinner);
        newsProgress= (ProgressBar) findViewById(R.id.newsDownloadsProgressBar);
        disconectMessage(this);
        ArrayAdapter<?> listAdapter = ArrayAdapter.createFromResource(this,
				R.array.categories, android.R.layout.simple_spinner_item);
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




        //Spinner
        categoriesListSpinner.setAdapter(listAdapter);

        categoriesListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.categories);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "You choosed: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                toast.show();

                getArticles(choose[selectedItemPosition].toLowerCase());
                articleList.setVisibility(View.GONE);
                newsProgress.setVisibility(View.VISIBLE);

                Log.i("Online",isOnline(getApplicationContext())+"");
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        //list of Articles
        articleList = (RecyclerView) findViewById(R.id.articlesListRecyclerView);
        mTicketsListAdapter=new TicketsListAdapter(new ArrayList<Event>(0),new TicketsListAdapter.PostTicketListener(){

            @Override
            public void onPostClick(String articleName) {//ToDo добавить передачу названия события
                Log.i("name",articleName+"");
                Intent intent = new Intent(MainActivity.this, ArticleFullInformActivity.class);
                intent.putExtra("articleName",articleName);
                startActivity(intent);

            }
        });
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        articleList.setLayoutManager(layoutManager);
        articleList.setAdapter(mTicketsListAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        articleList.addItemDecoration(itemDecoration);

    }

    public void getArticles(String category){
        RetrofitClient.getNewsAPI().getArticles(category+"").enqueue(new Callback <ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response <ArticleResponse> response) {
                if(response.isSuccessful()){

                    Log.i("Articles","download");

                    mTicketsListAdapter.updateTickets(response.body().getEvents());
                    try{if(response.body().getEvents().isEmpty()){Log.i("s","Now your tickets list is empty");}}
                    catch (NullPointerException e){}
                    articleList.setVisibility(View.VISIBLE);
                }
                else {Toast toast = Toast.makeText(getApplicationContext(),
                        "Something went wrong", Toast.LENGTH_SHORT);}

                Log.i("Articles",response.code()+"");
                newsProgress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.i("Articles","Failed");
                newsProgress.setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Something went wrong", Toast.LENGTH_SHORT);
                toast.show();
                disconectMessage(getApplicationContext());
            }
        });
    }

}
