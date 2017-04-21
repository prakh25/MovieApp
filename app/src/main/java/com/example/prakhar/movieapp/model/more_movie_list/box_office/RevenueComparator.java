package com.example.prakhar.movieapp.model.more_movie_list.box_office;

import com.example.prakhar.movieapp.model.more_movie_list.MovieListResult;

import java.util.Comparator;

/**
 * Created by Prakhar on 4/20/2017.
 */

public class RevenueComparator implements Comparator<MovieListResult> {
    @Override
    public int compare(MovieListResult o1, MovieListResult o2) {
        Double revenue1 = o1.getRevenue();
        Double revenue2 = o2.getRevenue();

        if(revenue2.compareTo(revenue1) > 0) {
            return 1;
        } else if(revenue2.compareTo(revenue1) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
