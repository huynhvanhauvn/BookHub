package com.hhub.bookhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hhub.bookhub.Models.Quote;
import com.hhub.bookhub.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<Quote> quotes;

    public ReviewAdapter(Context context, ArrayList<Quote> quotes) {
        this.context = context;
        this.quotes = quotes;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        Quote quote = quotes.get(position);
        holder.txt.setText(quote.getEnd());
        holder.txtAuthor.setText("-- "+quote.getAuthor()+" --");
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null){
                    //return clicked view and its index
                    onItemClickListener.OnItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView txt, txtAuthor;
        private ConstraintLayout line;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.item_txt_review);
            txtAuthor = (TextView) itemView.findViewById(R.id.item_txt_author);
            line = (ConstraintLayout) itemView.findViewById(R.id.item_review_line);
        }
    }

    public interface OnItemClickListener{
        public void OnItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    //method to set item click in adapter
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
