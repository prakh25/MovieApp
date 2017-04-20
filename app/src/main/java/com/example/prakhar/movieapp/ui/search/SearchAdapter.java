package com.example.prakhar.movieapp.ui.search;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.search.SearchResult;
import com.example.prakhar.movieapp.utils.Constants;
import com.example.prakhar.movieapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Prakhar on 3/22/2017.
 */

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private static final String SEARCH_TYPE_MOVIES = "movie";
    private static final String SEARCH_TYPE_TV = "tv";
    private static final String SEARCH_TYPE_PERSON = "person";

    private List<SearchResult> searchResults;
    private ListInteractionListener listInteractionListener;

    SearchAdapter() {
        searchResults = new ArrayList<>();
        listInteractionListener = null;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (searchResults.get(position).getMediaType().equals(SEARCH_TYPE_MOVIES)) {
            return searchResults.get(position).getId();
        } else if (searchResults.get(position).getMediaType().equals(SEARCH_TYPE_TV)) {
            return searchResults.get(position).getId();
        } else if (searchResults.get(position).getMediaType().equals(SEARCH_TYPE_PERSON)) {
            return searchResults.get(position).getId();
        }
        return -1;
    }

    @Override
    public long getItemId(int position) {
        if (searchResults.get(position).getMediaType().equals(SEARCH_TYPE_MOVIES)) {
            return searchResults.get(position).getId();
        } else if (searchResults.get(position).getMediaType().equals(SEARCH_TYPE_TV)) {
            return searchResults.get(position).getId();
        } else if (searchResults.get(position).getMediaType().equals(SEARCH_TYPE_PERSON)) {
            return searchResults.get(position).getId();
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        switch (searchResults.get(position).getMediaType()) {
            case SEARCH_TYPE_MOVIES:
                String posterPath = searchResults.get(position).getPosterPath();
                String imageUrl = Constants.TMDB_IMAGE_URL + "w185";
                Glide.with(holder.itemView.getContext()).load(imageUrl + posterPath)
                        .placeholder(R.drawable.movie_poster_placeholder)
                        .thumbnail(0.5f)
                        .into(holder.poster);

                ViewCompat.setTransitionName(holder.poster, posterPath);

                if (searchResults.get(position).getTitle() != null) {
                    if (!searchResults.get(position).getReleaseDate().isEmpty() &&
                            !searchResults.get(position).getReleaseDate().equals("")) {
                        int year = Utils.getReleaseYear(searchResults.get(position).getReleaseDate());
                        holder.title.setText(String.format(Locale.US, "%s (%d)",
                                searchResults.get(position).getTitle(),
                                year));
                    } else {
                        holder.title.setText(String.format(Locale.US, "%s",
                                searchResults.get(position).getTitle()));
                    }
                }

                if (searchResults.get(position).getOverview() != null &&
                        !searchResults.get(position).getOverview().isEmpty()) {
                    holder.description.setVisibility(View.VISIBLE);
                    holder.description.setText(String.format(Locale.US, "%s",
                            searchResults.get(position).getOverview()));
                } else {
                    holder.description.setVisibility(View.GONE);
                }

                holder.frameLayout.setOnClickListener(v ->
                        listInteractionListener.onMovieItemClick(searchResults.get(position).getId(),
                                position, holder.poster));
                break;
            case SEARCH_TYPE_TV:
                String tvPoster = searchResults.get(position).getPosterPath();
                String tvImage = Constants.TMDB_IMAGE_URL + "w185";
                Glide.with(holder.itemView.getContext()).load(tvImage + tvPoster)
                        .placeholder(R.drawable.movie_poster_placeholder)
                        .thumbnail(0.5f)
                        .into(holder.poster);

                if (searchResults.get(position).getName() != null &&
                        !searchResults.get(position).getName().isEmpty()) {
                    if (!searchResults.get(position).getFirstAirDate().isEmpty() &&
                            !searchResults.get(position).getFirstAirDate().equals("")) {
                        int year = Utils.getReleaseYear(searchResults.get(position).getFirstAirDate());
                        holder.title.setText(String.format(Locale.US, "%s (%d TV)",
                                searchResults.get(position).getName(),
                                year));
                    } else {
                        holder.title.setText(String.format(Locale.US, "%s",
                                searchResults.get(position).getName()));
                    }
                }
                if (searchResults.get(position).getOverview() != null &&
                        !searchResults.get(position).getOverview().isEmpty()) {
                    holder.description.setVisibility(View.VISIBLE);
                    holder.description.setText(String.format(Locale.US, "%s",
                            searchResults.get(position).getOverview()));
                } else {
                    holder.description.setVisibility(View.GONE);
                }
                break;
            case SEARCH_TYPE_PERSON:
                String profilePath = searchResults.get(position).getProfilePath();
                String profileImage = Constants.TMDB_IMAGE_URL + "w185";
                Glide.with(holder.itemView.getContext()).load(profileImage + profilePath)
                        .placeholder(R.drawable.movie_people_placeholder)
                        .thumbnail(0.5f)
                        .into(holder.poster);

                if (searchResults.get(position).getName() != null &&
                        !searchResults.get(position).getName().isEmpty()) {
                    holder.title.setText(String.format(Locale.US, "%s",
                            searchResults.get(position).getName()));
                }
                holder.description.setVisibility(View.GONE);

                holder.frameLayout.setOnClickListener(v ->
                        listInteractionListener.onPersonClicked(searchResults.get(position).getId(),
                                searchResults.get(position).getProfilePath(), position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void add(SearchResult item) {
        add(null, item);
    }

    public void add(@Nullable Integer position, SearchResult item) {
        if (position != null) {
            searchResults.add(position, item);
            notifyItemInserted(position);
        } else {
            searchResults.add(item);
            notifyItemInserted(searchResults.size() - 1);
        }
    }

    public void addItems(List<SearchResult> searchResultList) {
        searchResults.addAll(searchResultList);
        notifyItemRangeInserted(getItemCount(), searchResults.size() - 1);
    }

    public void remove(int position) {
        if (searchResults.size() < position) {
            Timber.d("The item at position: " + position + " doesn't exist");
            return;
        }
        searchResults.remove(position);
        notifyItemRemoved(position);
    }

    void removeAll() {
        searchResults.clear();
        notifyDataSetChanged();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.search_poster)
        ImageView poster;
        @BindView(R.id.search_title)
        TextView title;
        @BindView(R.id.search_description)
        TextView description;
        @BindView(R.id.search_result_frame)
        FrameLayout frameLayout;

        SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ListInteractionListener {
        void onMovieItemClick(Integer movieId, int clickedPosition, ImageView sharedImageView);

        void onPersonClicked(Integer personId, String profilePath, int clickedPosition);
    }

    public void setInteractionListener(ListInteractionListener interactionListener) {
        listInteractionListener = interactionListener;
    }
}
