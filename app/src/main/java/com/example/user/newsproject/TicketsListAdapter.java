package com.example.user.newsproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.newsproject.request.categories.Event;

import org.w3c.dom.Text;

import java.util.List;

public class TicketsListAdapter extends RecyclerView.Adapter<TicketsListAdapter.ViewHolder>{

    private List<Event> mTicketResponse;
    private Context mContext;//то объект, который предоставляет доступ к базовым функциям приложения: доступ к ресурсам, к файловой системе, вызов активности и т.д.
    private PostTicketListener mPostItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleTicket;
        public TextView coef;
        public TextView timeOfStart;
        public TextView shortDescription;

        PostTicketListener mPostItemListener;

        public ViewHolder(View itemView, PostTicketListener postTicketListener){//Реализует обязательный паттерн вью холдера
            super(itemView);
            titleTicket=(TextView) itemView.findViewById(R.id.articleTitleTextView);
            coef=(TextView) itemView.findViewById(R.id.gameCoeficientTextView);
            timeOfStart=(TextView) itemView.findViewById(R.id.timeOfStartTextView);
            shortDescription=(TextView) itemView.findViewById(R.id.shortDescriptionTextView);


            this.mPostItemListener=postTicketListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Event ticketResponse=getTicket(getAdapterPosition());
            mPostItemListener.onPostClick(ticketResponse.getArticle().toString());
        }
    }

    TicketsListAdapter(List<Event> tikets, PostTicketListener postTicketListener){
        this.mTicketResponse = tikets;
        mPostItemListener=postTicketListener;
    }

    @Override
    public int getItemCount() {
        return mTicketResponse.size();
    }

    @Override
    public TicketsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.articles_appearance, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView,this.mPostItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TicketsListAdapter.ViewHolder holder, int position) {

        Event ticketResponse = mTicketResponse.get(position);
        TextView textView = holder.titleTicket;
        TextView textView1=holder.coef;
        TextView textView2=holder.timeOfStart;
        TextView textView3=holder.shortDescription;
        textView.setText(ticketResponse.getTitle());
        textView1.setText(ticketResponse.getCoefficient());
        textView2.setText(ticketResponse.getTime());
        textView3.setText(ticketResponse.getPreview());
    }

    public void updateTickets(List<Event> tikets){
        mTicketResponse=tikets;
        notifyDataSetChanged();
    }

    private Event getTicket(int adapterPosition){
        return mTicketResponse.get(adapterPosition);
    }

    public interface PostTicketListener {

        void onPostClick(String number);
    }

}
