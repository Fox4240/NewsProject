package com.example.user.newsproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.newsproject.request.articlesData.Article;

import java.util.List;

public class ArticleAdditionalInfoAdapter extends RecyclerView.Adapter<ArticleAdditionalInfoAdapter.ViewHolder>{

    private List<Article> mArticleResponse;
    private Context mContext;//то объект, который предоставляет доступ к базовым функциям приложения: доступ к ресурсам, к файловой системе, вызов активности и т.д.


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public TextView additioinalInfo;

        public ViewHolder(View itemView){//Реализует обязательный паттерн вью холдера
            super(itemView);
            header=(TextView) itemView.findViewById(R.id.dditionalIndoHeaderTextView);
            additioinalInfo=(TextView) itemView.findViewById(R.id.additioinalInfoTextView);

        }

    }

    ArticleAdditionalInfoAdapter(List<Article> tikets){
        this.mArticleResponse = tikets;
    }

    @Override
    public int getItemCount() {
        return mArticleResponse.size();
    }

    @Override
    public ArticleAdditionalInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.additional_artical_info_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleAdditionalInfoAdapter.ViewHolder holder, int position) {

        Article ticketResponse = mArticleResponse.get(position);
        TextView textView = holder.header;
        TextView textView1=holder.additioinalInfo;
        textView.setText(ticketResponse.getHeader());
        textView1.setText(ticketResponse.getText());
    }

    public void updateArticle(List<Article> tikets){
        mArticleResponse=tikets;
        notifyDataSetChanged();
    }

    private Article getTicket(int adapterPosition){
        return mArticleResponse.get(adapterPosition);
    }

}
