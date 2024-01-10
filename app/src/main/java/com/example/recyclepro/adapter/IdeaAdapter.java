package com.example.recyclepro.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recyclepro.R;
import com.example.recyclepro.callback.IdeaClickListener;
import com.example.recyclepro.utils.Idea;

import java.util.ArrayList;

public class IdeaAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private ArrayList<Idea> ideas;
    private Context context;
    private IdeaClickListener ideaClickListener;
    public IdeaAdapter(Context context, ArrayList<Idea> ideas){
        this.ideas = ideas;
        this.context = context;
    }

    public void setIdeaClickListener(IdeaClickListener ideaClickListener){
        this.ideaClickListener = ideaClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.idea_item, parent, false);
        return new IdeaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IdeaViewHolder ideaViewHolder = (IdeaViewHolder) holder;
        Idea idea = getItem(position);

        ideaViewHolder.idea_TV_title.setText(idea.getTitle());
        ideaViewHolder.idea_TV_description.setText(idea.getDescription());
        Glide
            .with(context)
            .load(idea.getImageUrl())
            .centerCrop()
            .into(ideaViewHolder.idea_IV_image);

    }
    private Idea getItem(int position) {
        return ideas.get(position);
    }

    @Override
    public int getItemCount() {
        return this.ideas.size();
    }

    public class IdeaViewHolder extends  RecyclerView.ViewHolder {
        private ImageView idea_IV_image;
        private TextView idea_TV_title;
        private TextView idea_TV_description;

        public IdeaViewHolder(@NonNull View itemView) {
            super(itemView);
            idea_IV_image = itemView.findViewById(R.id.idea_IV_image);
            idea_TV_title = itemView.findViewById(R.id.idea_TV_title);
            idea_TV_description = itemView.findViewById(R.id.idea_TV_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Idea idea = getItem(getAdapterPosition());
                    int position = getAdapterPosition();
                    ideaClickListener.onClick(idea, position);
                }
            });
        }
    }

}
