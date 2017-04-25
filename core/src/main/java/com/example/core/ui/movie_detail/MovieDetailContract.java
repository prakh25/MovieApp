package com.example.core.ui.movie_detail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.core.model.home.movie.Poster;
import com.example.core.model.home.movie.Result;
import com.example.core.model.movie_detail.GenericMovieDataWrapper;
import com.example.core.model.movie_detail.tmdb.BelongsToCollection;
import com.example.core.model.movie_detail.tmdb.Cast;
import com.example.core.model.movie_detail.tmdb.Crew;
import com.example.core.model.movie_detail.tmdb.TmdbMovieDetail;
import com.example.core.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 3/4/2017.
 */

public interface MovieDetailContract {

    interface ViewActions {
        void onMovieRequested(Integer movieId);

        void onAddToWatchlistClicked(GenericMovieDataWrapper wrapper);

        void onRemoveFromWatchlistClicked(Integer movieId);

        void onAddMarkAsFavoriteClicked(GenericMovieDataWrapper wrapper);

        void onRemoveFromFavoriteList(Integer movieId);

        void onSaveMovieRatingClicked(GenericMovieDataWrapper wrapper, int userRating);

        void onDeleteMovieRatingClicked(Integer movieId);

        void onAddToListClicked(GenericMovieDataWrapper wrapper);

        void onCreateNewListRequested(@NonNull String title,
                                      @Nullable String description,
                                      GenericMovieDataWrapper wrapper);

        void onAddMovieToList(int listId, GenericMovieDataWrapper wrapper);
    }

    interface DetailView extends BaseView {
        void showMovieHeader(TmdbMovieDetail tmdbMovieDetail);

        void showMovieStatus(GenericMovieDataWrapper wrapper, boolean isAddedToWatchlist,
                             boolean isMarkedAsFavorite);

        void showMovieDetails(String releaseDate, String region, String tagLine, String overView);

        void showRatings(GenericMovieDataWrapper wrapper, Integer userRating, Double traktRating,
                         Integer traktVotes);

        void showMovieTrailer(String key);

        void showSimilarMovies(List<Result> resultList);

        void showMovieCast(List<Cast> castList);

        void showMovieCrew(List<Crew> directorsList, List<Crew> writesList,
                           List<Crew> screenplayList);

        void showAddToListDialog(List<String> userListsName, GenericMovieDataWrapper wrapper);

        void showMovieImages(List<Poster> imageList, Integer imageNumber, boolean showViewAll);

        void showBelongToCollection(BelongsToCollection belongsToCollection);

        void showExternalLinks(String movieHomepage, Integer tmdbId,
                               String imdbId, String movieName);
    }
}
