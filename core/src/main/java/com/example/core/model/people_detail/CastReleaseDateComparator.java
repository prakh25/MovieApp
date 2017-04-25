package com.example.core.model.people_detail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Prakhar on 4/3/2017.
 */

public class CastReleaseDateComparator implements Comparator<Cast> {
    @Override
    public int compare(Cast o1, Cast o2) {
        String releaseDate1;
        String releaseDate2;

        if (o1.getMediaType().equals("movie")) {
            releaseDate1 = o1.getReleaseDate();
        } else {
            releaseDate1 = o1.getFirstAirDate();
        }

        if(releaseDate1 == null) {
            releaseDate1 = "1800-01-01";
        }

        if (o2.getMediaType().equals("movie")) {
            releaseDate2 = o2.getReleaseDate();
        } else {
            releaseDate2 = o2.getFirstAirDate();
        }

        if(releaseDate2 == null) {
            releaseDate2 = "1800-01-01";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Date date1 = null;

        try {
            date1 = simpleDateFormat.parse(releaseDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date2 = null;

        try {
            date2 = simpleDateFormat.parse(releaseDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert date2 != null;
        assert date1 != null;
        if (date2.compareTo(date1) > 0) {
            return 1;
        } else if (date2.compareTo(date1) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
