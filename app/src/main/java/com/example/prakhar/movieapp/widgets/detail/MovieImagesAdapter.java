package com.example.prakhar.movieapp.widgets.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.home.movie.Poster;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/28/2017.
 */

public class MovieImagesAdapter extends RecyclerView.Adapter<MovieImagesAdapter.ImageViewHolder> {

    private List<Poster> images;
    private MovieImageAdapterListener listener;

    public MovieImagesAdapter(List<Poster> imagesList, MovieImageAdapterListener adapterListener) {
        images = new ArrayList<>();
        images.addAll(imagesList);
        listener = adapterListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_images_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(Constants.TMDB_IMAGE_URL
                + "w780" + images.get(position).getFilePath()).into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.detail_movie_image)
        ImageView movieImage;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            movieImage.setOnClickListener(v -> listener.onImageClicked(getAdapterPosition()));
        }
    }

    public interface MovieImageAdapterListener {
        void onImageClicked(int position);
    }
}
