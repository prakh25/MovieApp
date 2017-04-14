package com.example.prakhar.movieapp.widgets.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Cast;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/7/2017.
 */

public class MovieCastAdapter extends BaseAdapter {

    @BindView(R.id.detail_cast_item_frame)
    FrameLayout castFrame;
    @BindView(R.id.credit_image)
    ImageView castImage;
    @BindView(R.id.credit_name)
    TextView castName;

    private List<Cast> castList;
    private InteractionListener listener;

    public MovieCastAdapter(List<Cast> casts, InteractionListener interactionListener) {
        castList = new ArrayList<>();
        castList.addAll(casts);
        listener = interactionListener;
    }

    @Override
    public int getCount() {
        return castList.size();
    }

    @Override
    public Object getItem(int position) {
        return castList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.detail_cast_item, null);
        }
        ButterKnife.bind(this, convertView);

        Glide.with(castImage.getContext())
                .load(Constants.TMDB_IMAGE_URL + "w185" + castList.get(position).getProfilePath())
                .placeholder(R.drawable.movie_people_placeholder)
                .into(castImage);
        castName.setText(castList.get(position).getName());

        castFrame.setOnClickListener(v -> listener.onPersonClicked(castList.get(position).getId(),
                castList.get(position).getProfilePath(), position));

        return convertView;
    }

    public interface InteractionListener {
        void onPersonClicked(Integer id, String profilePath, int clickedPosition);
    }

}
