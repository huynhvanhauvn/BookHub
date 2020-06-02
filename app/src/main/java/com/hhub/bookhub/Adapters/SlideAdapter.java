package com.hhub.bookhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.hhub.bookhub.Models.Promotion;
import com.hhub.bookhub.R;

import java.util.ArrayList;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.RecycleViewHolder> {

    private Context context;
    private ArrayList<Promotion> promotions;
    private ViewPager2 viewPager2;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context, ArrayList<Promotion> promotions, ViewPager2 viewPager2) {
        this.context = context;
        this.promotions = promotions;
        this.viewPager2 = viewPager2;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_slide,parent,false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, final int position) {
        Promotion promotion = promotions.get(position);
        Glide.with(context).load(promotion.getImage()).into(holder.imageView);
        holder.txtTitle.setText(promotion.getName());
        if(position == promotions.size()-2) {
            viewPager2.post(slideRunnable);
        }
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    //return clicked view and its index
                    onItemClickListener.OnItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView txtTitle;
        private CardView line;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_slide_img);
            txtTitle = (TextView) itemView.findViewById(R.id.item_slide_txt);
            line = (CardView) itemView.findViewById(R.id.header_line);
        }
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            promotions.addAll(promotions);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener{
        public void OnItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    //method to set item click in adapter
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
