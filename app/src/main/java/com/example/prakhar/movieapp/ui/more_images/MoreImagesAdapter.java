package com.example.prakhar.movieapp.ui.more_images;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.tmdb.Poster;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/31/2017.
 */

public class MoreImagesAdapter extends RecyclerView.Adapter<MoreImagesAdapter.ImageViewHolder> {

    private List<Poster> imageList;
    private MoreImagesInteractionListener listener;

    public MoreImagesAdapter() {
        imageList = new ArrayList<>();
        listener = null;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.more_images_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Constants.TMDB_IMAGE_URL + "w780" +
                        imageList.get(position).getFilePath())
                .thumbnail(0.5f)
                .placeholder(R.drawable.movie_image_placeholder)
                .crossFade()
                .into(holder.movieImages);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void add(Poster item) {
        add(null, item);
    }

    public void add(@Nullable Integer position, Poster item) {
        if (position != null) {
            imageList.add(position, item);
            notifyItemInserted(position);
        } else {
            imageList.add(item);
            notifyItemInserted(imageList.size() - 1);
        }
    }

    public void addItems(List<Poster> images) {
        imageList.addAll(images);
        notifyItemRangeInserted(getItemCount(), imageList.size() - 1);
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_images)
        ImageView movieImages;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            movieImages.setOnClickListener(v ->
                    listener.onImageClicked(getAdapterPosition()));
        }
    }

    public interface MoreImagesInteractionListener {
        void onImageClicked(int position);
    }

    public void setListener(MoreImagesInteractionListener imagesInteractionListener) {
        listener = imagesInteractionListener;
    }
}
