package com.example.user.newsproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.newsproject.request.articlesData.Article;
import com.example.user.newsproject.request.articlesData.ArticleDataResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.user.newsproject.InternetConnection.disconectMessage;

public class ArticleFullInformActivity extends AppCompatActivity {

    static String articleName;
    TextView team1Name;
    TextView team2Name;
    TextView timeOfCompetition;
    TextView placeOfCompetition;
    TextView tournamentName;
    TextView predictionOfCompetition;
    RecyclerView additionalInfo;
    ArticleAdditionalInfoAdapter mAdditionalInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_full_inform);

        disconectMessage(getApplicationContext());

        team1Name=(TextView) findViewById(R.id.team1TextView);
        team2Name=(TextView) findViewById(R.id.team2TextView);
        timeOfCompetition=(TextView) findViewById(R.id.timeOfCompetionTextView);
        placeOfCompetition=(TextView) findViewById(R.id.placeOfCompetitioinTextView);
        tournamentName=(TextView) findViewById(R.id.nameOfCompetitionTextView);
        predictionOfCompetition=(TextView) findViewById(R.id.predictionOfCompetitionTextView);

        additionalInfo=(RecyclerView) findViewById(R.id.additionalInfoRecycleView);
        mAdditionalInfoAdapter=new ArticleAdditionalInfoAdapter(new ArrayList<Article>(0));
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        additionalInfo.setLayoutManager(layoutManager);
        additionalInfo.setAdapter(mAdditionalInfoAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        additionalInfo.addItemDecoration(itemDecoration);




        articleName=getIntent().getStringExtra("articleName");
        Log.i("ArticlesData",articleName+"");
        getArticlesData(articleName);




    }

    public void getArticlesData(String articleName){
        RetrofitClient.getNewsAPI().getArticlesData(articleName+"").enqueue(new Callback<ArticleDataResponse>() {
            @Override
            public void onResponse(Call<ArticleDataResponse> call, Response<ArticleDataResponse> response) {
                if(response.isSuccessful()){

                    Log.i("ArticlesData","Download");
                    team1Name.setText(response.body().getTeam1());
                    team2Name.setText(response.body().getTeam2());
                    timeOfCompetition.setText(response.body().getTime());
                    mAdditionalInfoAdapter.updateArticle(response.body().getArticle());
                    //if(response.body().getPlace().isEmpty()){response.body().setPlace("Heaven knows");}
                    //placeOfCompetition.setText(response.body().getPlace());
                    tournamentName.setText(response.body().getTournament());
                    predictionOfCompetition.setText(response.body().getPrediction());
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Something went wrong", Toast.LENGTH_SHORT);
                    toast.show();
                }
                Log.i("ArticlesData",response.code()+"");

            }

            @Override
            public void onFailure(Call<ArticleDataResponse> call, Throwable t) {
                Log.i("ArticlesData","Failed");//ToDo добавить прогресс бар, скрыть элементы при загрузке, убрать лишний текствью
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Something went wrong", Toast.LENGTH_SHORT);
                toast.show();
                disconectMessage(getApplicationContext());
            }
        });
    }


}
