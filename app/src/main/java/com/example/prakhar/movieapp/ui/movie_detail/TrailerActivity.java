package com.example.prakhar.movieapp.ui.movie_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.prakhar.movieapp.BuildConfig;
import com.example.prakhar.movieapp.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Prakhar on 4/15/2017.
 */

public class TrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final String EXTRA_VIDEO_ID = "videoId";
    private static final String EXTRA_VIDEO_TIME = "videoTime";

    @BindView(R.id.you_tube_player_view)
    YouTubePlayerView youTubePlayerView;
    @BindView(R.id.you_tube_activity)
    FrameLayout frameLayout;

    private int videoTime;
    private String videoId;

    private YouTubePlayer player;
    private boolean isFullScreen;
    private Unbinder unbinder;

    public static Intent newStartIntent(Context context, String videoId) {
        Intent intent = new Intent(context, TrailerActivity.class);
        intent.putExtra(EXTRA_VIDEO_ID, videoId);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(EXTRA_VIDEO_ID)) {
            videoId = extras.getString(EXTRA_VIDEO_ID);
        } else {
            finish();
        }

        setContentView(R.layout.activity_trailer);

        unbinder = ButterKnife.bind(this);

        youTubePlayerView.initialize(BuildConfig.YOU_TUBE_DATA_API_KEY, this);

        if (savedInstanceState != null) {
            videoTime = savedInstanceState.getInt(EXTRA_VIDEO_TIME);
        }

//        frameLayout.setOnClickListener(v -> {
//            onBackPressed();
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        player = youTubePlayer;
        youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
        youTubePlayer.setOnFullscreenListener(b -> isFullScreen = b);

        if (videoId != null && !wasRestored) {
            youTubePlayer.loadVideo(videoId);
        }

        if (wasRestored) {
            youTubePlayer.seekToMillis(videoTime);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Unable to load video", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (player != null) {
            bundle.putInt(EXTRA_VIDEO_TIME, player.getCurrentTimeMillis());
        }
    }

    @Override
    public void onBackPressed() {
        boolean finish = true;
        try {
            if (player != null) {
                if (isFullScreen) {
                    finish = false;
                    player.setOnFullscreenListener(b -> {
                        if (!b) {
                            finish();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    });
                    player.setFullscreen(false);
                }
                player.pause();
            }
        } catch (final IllegalStateException e) {
            e.printStackTrace();
        }

        if (finish) {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
