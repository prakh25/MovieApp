package com.example.prakhar.mobile.ui.home.home_people;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.core.model.person_search.PersonSearchResult;
import com.example.prakhar.mobile.R;
import com.example.prakhar.mobile.utils.Constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Prakhar on 4/8/2017.
 */

public class HomePeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PersonSearchResult> personSearchResults;

    public static final int VIEW_TYPE_LIST = 0;
    public static final int VIEW_TYPE_LOADING = 1;

    @IntDef({VIEW_TYPE_LOADING, VIEW_TYPE_LIST})
    @Retention(RetentionPolicy.SOURCE)
    private  @interface ViewType {
    }

    @ViewType
    private int viewType;

    private PersonInteractionListener listener;

    public HomePeopleAdapter() {
        personSearchResults = new ArrayList<>();
        listener = null;
        viewType = VIEW_TYPE_LIST;
    }

    @Override
    public int getItemViewType(int position) {
        return personSearchResults.get(position) == null ?
                VIEW_TYPE_LOADING : personSearchResults.get(position).getId();
    }

    @Override
    public long getItemId(int position) {
        return personSearchResults.size() >= position ?
                personSearchResults.get(position).getId() : -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicationViewHolder(parent);
        }
        return onGenericViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LOADING) {
            return; // no-op
        }
        onBindGenericItemViewHolder((PeopleViewHolder) holder, position);
    }


    @Override
    public int getItemCount() {
        return personSearchResults.size();
    }

    private RecyclerView.ViewHolder onIndicationViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_movie_item, parent, false);
        return new ProgressViewHolder(view);
    }

    private RecyclerView.ViewHolder onGenericViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_people_item, parent, false);
        return new PeopleViewHolder(view);
    }

    private void onBindGenericItemViewHolder(final PeopleViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Constants.TMDB_IMAGE_URL + "h632" +
                        personSearchResults.get(position).getProfilePath())
                .placeholder(R.drawable.movie_people_placeholder)
                .crossFade()
                .centerCrop()
                .into(holder.personProfile);

        holder.personName.setText(String.format(Locale.US,"%d. %s",
                position + 1, personSearchResults.get(position).getName()));
    }

        public void add(PersonSearchResult item) {
        add(null, item);
    }

    public void add(@Nullable Integer position, PersonSearchResult item) {
        if (position != null) {
            personSearchResults.add(position, item);
            notifyItemInserted(position);
        } else {
            personSearchResults.add(item);
            notifyItemInserted(personSearchResults.size() - 1);
        }
    }

    public void addItems(List<PersonSearchResult> searchResults) {
        personSearchResults.addAll(searchResults);
        notifyItemRangeInserted(getItemCount(), personSearchResults.size() - 1);
    }

    public void remove(int position) {
        if (personSearchResults.size() < position) {
            Timber.d("The item at position: " + position + " doesn't exist");
            return;
        }
        personSearchResults.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        personSearchResults.clear();
        notifyDataSetChanged();
    }

    public boolean addLoadingView() {
        if (getItemViewType(personSearchResults.size() - 1) != VIEW_TYPE_LOADING) {
            add(null);
            return true;
        }
        return false;
    }

    public boolean removeLoadingView() {
        if (personSearchResults.size() > 1) {
            int loadingViewPosition = personSearchResults.size() - 1;
            if (getItemViewType(loadingViewPosition) == VIEW_TYPE_LOADING) {
                remove(loadingViewPosition);
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(@ViewType int viewType) {
        this.viewType = viewType;
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_progress_bar)
        ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.home_people_card)
        CardView peopleCard;
        @BindView(R.id.home_people_name)
        TextView personName;
        @BindView(R.id.home_people_profile)
        ImageView personProfile;

        public PeopleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            peopleCard.setOnClickListener(v -> listener.onPersonClicked(
                    personSearchResults.get(getAdapterPosition()).getId(),
                    personSearchResults.get(getAdapterPosition()).getProfilePath(),
                    getAdapterPosition()));
        }
    }

    public interface PersonInteractionListener {
        void onPersonClicked(Integer personId, String profilePath, int clickedPosition);
    }

    public void setUpListener(PersonInteractionListener interactionListener) {
        listener = interactionListener;
    }
}
