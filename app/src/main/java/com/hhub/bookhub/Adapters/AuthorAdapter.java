package com.hhub.bookhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hhub.bookhub.Models.Author;
import com.hhub.bookhub.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<Author> authors;

    public AuthorAdapter(Context context, ArrayList<Author> authors) {
        this.context = context;
        this.authors = authors;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_artist,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        Author author = authors.get(position);
        Glide.with(context).load(author.getImage()).centerCrop().into(holder.imgAvatar);
        holder.txtName.setText(author.getName());
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
        return authors.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imgAvatar;
        private TextView txtName;
        private ConstraintLayout line;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (RoundedImageView) itemView.findViewById(R.id.artist_avatar);
            txtName = (TextView) itemView.findViewById(R.id.artist_txt_name);
            line = (ConstraintLayout) itemView.findViewById(R.id.iartist_line);
        }
    }

    public ArrayList<Author> getArtists() {
        return authors;
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
