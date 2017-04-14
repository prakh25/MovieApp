package com.example.prakhar.movieapp.ui.full_credits;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Cast;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Crew;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/31/2017.
 */

public class FullCreditAdapter extends RecyclerView.Adapter<FullCreditAdapter.CreditViewHolder> {

    private static final int VIEW_TYPE_CAST = 0;
    private static final int VIEW_TYPE_CREW = 1;

    private int viewType;

    private List<Cast> castList;
    private List<Crew> crewList;
    private FullCreditListener listener;

    public FullCreditAdapter(int id) {
        castList = new ArrayList<>();
        crewList = new ArrayList<>();
        listener = null;
        viewType = id;
    }

    @Override
    public CreditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.full_credits_list_item, parent, false);
        return new CreditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CreditViewHolder holder, int position) {
        switch (viewType) {
            case VIEW_TYPE_CAST:
                Glide.with(holder.itemView.getContext())
                        .load(Constants.TMDB_IMAGE_URL + "w185" + castList.get(position).getProfilePath())
                        .placeholder(R.drawable.movie_people_placeholder)
                        .into(holder.profileImage);
                holder.personName.setText(castList.get(position).getName());
                holder.characterPlayed.setText(castList.get(position).getCharacter());
                holder.creditFrame.setOnClickListener(v ->
                    listener.onPersonClicked(castList.get(position).getId(),
                            castList.get(position).getProfilePath(), position));
                break;
            case VIEW_TYPE_CREW:
                Glide.with(holder.itemView.getContext())
                        .load(Constants.TMDB_IMAGE_URL + "w185" + crewList.get(position).getProfilePath())
                        .placeholder(R.drawable.movie_people_placeholder)
                        .into(holder.profileImage);
                holder.personName.setText(crewList.get(position).getName());
                holder.characterPlayed.setText(crewList.get(position).getJob());
                holder.creditFrame.setOnClickListener(v ->
                    listener.onPersonClicked(crewList.get(position).getId(),
                            crewList.get(position).getProfilePath(), position));
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
        Glide.clear(holder.profileImage);
    }

    public void addCasts(List<Cast> casts) {
        castList.addAll(casts);
        notifyItemRangeInserted(0, castList.size() - 1);
    }

    public void addCrews(List<Crew> crews) {
        crewList.addAll(crews);
        notifyItemRangeInserted(0, crewList.size() - 1);
    }

    public class CreditViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.full_credit_item_frame)
        FrameLayout creditFrame;
        @BindView(R.id.full_crew_person_image)
        ImageView profileImage;
        @BindView(R.id.full_crew_person_name)
        TextView personName;
        @BindView(R.id.full_crew_person_character)
        TextView characterPlayed;

        public CreditViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface FullCreditListener {
        void onPersonClicked(Integer personId, String profilePath, int clickedPosition);
    }

    public void setUpFullCreditListener(FullCreditListener creditListener) {
        listener = creditListener;
    }
}
