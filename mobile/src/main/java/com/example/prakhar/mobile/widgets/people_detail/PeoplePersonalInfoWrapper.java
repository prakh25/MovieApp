package com.example.prakhar.mobile.widgets.people_detail;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prakhar.mobile.R;
import com.example.prakhar.mobile.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 4/4/2017.
 */

public class PeoplePersonalInfoWrapper extends LinearLayout {

    @BindView(R.id.people_gender_frame)
    FrameLayout genderFrame;
    @BindView(R.id.people_gender)
    TextView personGender;
    @BindView(R.id.people_birthday_frame)
    FrameLayout birthdayFrame;
    @BindView(R.id.people_birthday)
    TextView personBirthday;
    @BindView(R.id.people_death_day_frame)
    FrameLayout deathDayFrame;
    @BindView(R.id.people_death_day)
    TextView personDeathDay;
    @BindView(R.id.people_place_of_birth_frame)
    FrameLayout placeOfBirthFrame;
    @BindView(R.id.people_place_of_birth)
    TextView personPlaceOfBirth;
    @BindView(R.id.people_also_know_as_frame)
    FrameLayout alsoKnownAsFrame;
    @BindView(R.id.people_also_known_as)
    TextView personAlsoKnownAs;

    public PeoplePersonalInfoWrapper(Context context) {
        super(context);
    }

    public PeoplePersonalInfoWrapper(Context context, Integer gender, String birthday,
                                     String deathDay, String placeOfBirth, List<String> alsoKnownAs) {
        super(context);
        init(context);

        if (gender != 0) {
            genderFrame.setVisibility(VISIBLE);
            switch (gender) {
                case 1:
                    personGender.setText("Female");
                    break;
                case 2:
                    personGender.setText("Male");
                    break;
            }
        }

        if (birthday != null && !birthday.isEmpty()) {
            birthdayFrame.setVisibility(VISIBLE);
            personBirthday.setText(Utils.getDate(birthday));
        }

        if (deathDay != null && !deathDay.isEmpty()) {
            deathDayFrame.setVisibility(VISIBLE);
            personDeathDay.setText(Utils.getDate(deathDay));
        }

        if (placeOfBirth != null && !placeOfBirth.isEmpty()) {
            placeOfBirthFrame.setVisibility(VISIBLE);
            personPlaceOfBirth.setText(placeOfBirth);
        }

        if (!alsoKnownAs.isEmpty()) {
            alsoKnownAsFrame.setVisibility(VISIBLE);
            personAlsoKnownAs.setText(android.text.TextUtils.join(", ", alsoKnownAs));
        }
    }

    private void init(Context context) {
        inflate(context, R.layout.people_personal_info_wrapper, this);
        ButterKnife.bind(this);
    }
}
