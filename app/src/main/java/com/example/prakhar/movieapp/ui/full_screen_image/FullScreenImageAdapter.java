package com.example.prakhar.movieapp.ui.full_screen_image;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.home.movie.Poster;
import com.example.prakhar.movieapp.utils.Constants;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/31/2017.
 */

public class FullScreenImageAdapter extends PagerAdapter {

    @BindView(R.id.full_screen_image)
    PhotoView photoView;

    private List<Poster> images;
    private LayoutInflater layoutInflater;
    private Context context;
    private FullScreenInteractionListener listener;

    public FullScreenImageAdapter(Context context, List<Poster> imageList,
                                  FullScreenInteractionListener interactionListener) {
        images = new ArrayList<>();
        images.addAll(imageList);
        this.context = context;
        listener = interactionListener;
        layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.full_screen_view_pager_item,
                container, false);
        ButterKnife.bind(this, view);

        Glide.with(context).load(Constants.TMDB_IMAGE_URL + "original" +
                images.get(position).getFilePath())
                .thumbnail(0.5f)
                .crossFade()
                .into(photoView);

        photoView.setOnClickListener(v -> listener.onImageClicked(position));
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface FullScreenInteractionListener {
        void onImageClicked(int position);
    }
}
