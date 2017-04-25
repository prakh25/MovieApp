package com.example.prakhar.mobile.ui.genre;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.core.model.genre.Genre;
import com.example.prakhar.mobile.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Prakhar on 3/27/2017.
 */

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder>{

    private List<Genre> genreList;
    private GenreInteractionListener listener;

    public GenreAdapter() {
        genreList = new ArrayList<>();
        listener = null;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_list_item, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        Timber.i("GenreAdapter"+ genreList.get(position).getName());
        holder.genreName.setText(genreList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public void add(Genre item) {
        add(null, item);
    }

    public void add(@Nullable Integer position, Genre item) {
        if (position != null) {
            genreList.add(position, item);
            notifyItemInserted(position);
        } else {
            genreList.add(item);
            notifyItemInserted(genreList.size() - 1);
        }
    }

    public void addItems(List<Genre> genres) {
        genreList.addAll(genres);
        notifyItemRangeInserted(getItemCount(), genreList.size() - 1);
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.genre_name)
        TextView genreName;

        public GenreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            genreName.setOnClickListener(v ->
            listener.onGenreSelected(genreList.get(getAdapterPosition()).getName(),
                    genreList.get(getAdapterPosition()).getId()));
        }
    }

    public interface GenreInteractionListener {
        void onGenreSelected(String name, Integer id);
    }

    public void setGenreInteractionListener(GenreInteractionListener interactionListener) {
        listener = interactionListener;
    }
}
