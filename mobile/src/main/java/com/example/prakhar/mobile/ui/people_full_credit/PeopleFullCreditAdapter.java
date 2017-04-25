package com.example.prakhar.mobile.ui.people_full_credit;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.core.model.people_detail.Cast;
import com.example.core.model.people_detail.Crew;
import com.example.prakhar.mobile.R;
import com.example.prakhar.mobile.utils.Constants;
import com.example.prakhar.mobile.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 4/6/2017.
 */

public class PeopleFullCreditAdapter extends RecyclerView.Adapter<PeopleFullCreditAdapter.CreditViewHolder> {

    private static final int VIEW_TYPE_CAST = 0;
    private static final int VIEW_TYPE_CREW = 1;

    private int viewType;

    private List<Cast> castList;
    private List<Crew> crewList;

    private PeopleFullCreditListener listener;

    private int year;

    public PeopleFullCreditAdapter(int id) {
        castList = new ArrayList<>();
        crewList = new ArrayList<>();
        listener = null;
        viewType = id;
    }

    @Override
    public CreditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.people_full_credit_item, parent, false);
        return new CreditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CreditViewHolder holder, int position) {
        switch (viewType) {
            case VIEW_TYPE_CAST:
                Glide.with(holder.itemView.getContext())
                        .load(Constants.TMDB_IMAGE_URL + "w185" + castList.get(position).getPosterPath())
                        .placeholder(R.drawable.movie_poster_placeholder)
                        .into(holder.moviePoster);

                ViewCompat.setTransitionName(holder.moviePoster,
                        castList.get(position).getPosterPath());

                if(castList.get(position).getMediaType().equals("movie")) {
                    if (castList.get(position).getReleaseDate() != null &&
                            !castList.get(position).getReleaseDate().isEmpty()) {
                        year = Utils.getReleaseYear(castList.get(position).getReleaseDate());
                    }
                } else {
                    if (castList.get(position).getFirstAirDate() != null &&
                            !castList.get(position).getFirstAirDate().isEmpty()) {
                        year = Utils.getReleaseYear(castList.get(position).getFirstAirDate());
                    }
                }

                if(castList.get(position).getMediaType().equals("movie")) {
                    holder.movieTitle.setText(String.format(Locale.US, "%s (%d)",
                            castList.get(position).getTitle(), year));
                } else {
                    holder.movieTitle.setText(String.format(Locale.US, "%s (%d TV Series)",
                            castList.get(position).getName(), year));
                }

                holder.characterPlayed.setText(String.format(Locale.US,"as %s",
                        castList.get(position).getCharacter()));

                holder.creditFrame.setOnClickListener(v ->
                        listener.onPersonClicked(castList.get(position).getId(), position,
                                holder.moviePoster));
                break;
            case VIEW_TYPE_CREW:
                Glide.with(holder.itemView.getContext())
                        .load(Constants.TMDB_IMAGE_URL + "w185" + crewList.get(position).getPosterPath())
                        .placeholder(R.drawable.movie_poster_placeholder)
                        .into(holder.moviePoster);

                if(crewList.get(position).getMediaType().equals("movie")) {
                    if (crewList.get(position).getReleaseDate() != null &&
                            !crewList.get(position).getReleaseDate().isEmpty()) {
                        year = Utils.getReleaseYear(crewList.get(position).getReleaseDate());
                    }
                } else {
                    if (crewList.get(position).getFirstAirDate() != null &&
                            !crewList.get(position).getFirstAirDate().isEmpty()) {
                        year = Utils.getReleaseYear(crewList.get(position).getFirstAirDate());
                    }
                }

                if(crewList.get(position).getMediaType().equals("movie")) {
                    holder.movieTitle.setText(String.format(Locale.US, "%s (%d)",
                            crewList.get(position).getTitle(), year));
                } else {
                    holder.movieTitle.setText(String.format(Locale.US, "%s (%d TV Series)",
                            crewList.get(position).getName(), year));
                }

                holder.characterPlayed.setText(crewList.get(position).getJob());

                holder.creditFrame.setOnClickListener(v ->
                        listener.onPersonClicked(crewList.get(position).getId(), position,
                                holder.moviePoster));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return viewType == 0 ? castList.size() : crewList.size();
    }

    @Override
    public void onViewRecycled(CreditViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.clear(holder.moviePoster);
    }

    public void addCasts(List<Cast> casts) {
        castList.addAll(casts);
        notifyItemRangeInserted(0, castList.size() - 1);
    }

    public void addCrews(List<Crew> crews) {
        crewList.addAll(crews);
        notifyItemRangeInserted(0, crewList.size() - 1);
    }

    public static class CreditViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.people_full_credit_frame)
        FrameLayout creditFrame;
        @BindView(R.id.people_full_credit_poster)
        ImageView moviePoster;
        @BindView(R.id.people_full_credit_title)
        TextView movieTitle;
        @BindView(R.id.people_full_credit_character)
        TextView characterPlayed;

        public CreditViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface PeopleFullCreditListener {
        void onPersonClicked(Integer movieId, int clickedPosition, ImageView sharedImageView);
    }

    public void setUpFullCreditListener(PeopleFullCreditListener creditListener) {
        listener = creditListener;
    }
}
