package com.example.prakhar.movieapp.ui.movie_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.tmdb.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/25/2017.
 */

public class CreateNewListDialog extends DialogFragment {

    private static final String ARG_CREATE_LIST_DIALOG_RESULT = "argResult";

    @BindView(R.id.dialog_create_new_list_title_input_layout)
    TextInputLayout listTitleLayout;
    @BindView(R.id.dialog_create_new_list_edit_text_title)
    EditText listTitle;
    @BindView(R.id.dialog_create_new_list_edit_text_description)
    EditText listDescription;
    @BindView(R.id.dialog_create_new_list_btn)
    Button createListBtn;

    private Result result;
    private CreateNewListDialogListener listDialogListener;
    private String title;
    private String description;

    public CreateNewListDialog() {
    }

    public static CreateNewListDialog newInstance(Result result) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_CREATE_LIST_DIALOG_RESULT, result);
        CreateNewListDialog fragment = new CreateNewListDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            result = getArguments().getParcelable(ARG_CREATE_LIST_DIALOG_RESULT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_new_list, container);
        listDialogListener = (CreateNewListDialogListener) getTargetFragment();
        init(view);
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        listTitle.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        listTitleLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                title = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        description = listDescription.getText().toString();
        createListBtn.setOnClickListener(v -> {
            if(title == null) {
                listDialogListener.cannotCreateList();
            } else {
                listDialogListener.createNewList(title, description, result);
                dismiss();
            }
        });
    }

    interface CreateNewListDialogListener {
        void createNewList(String title, String description, Result result);

        void cannotCreateList();
    }
}
