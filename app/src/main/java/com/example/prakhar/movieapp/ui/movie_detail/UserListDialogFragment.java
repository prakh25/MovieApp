package com.example.prakhar.movieapp.ui.movie_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.tmdb.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.prakhar.movieapp.utils.Constants.ARG_USER_LIST_NAME;
import static com.example.prakhar.movieapp.utils.Constants.ARG_USER_RESULT;

/**
 * Created by Prakhar on 3/25/2017.
 */

public class UserListDialogFragment extends DialogFragment
        implements UserListDialogAdapter.UserListDialogListener {

    @BindView(R.id.user_list_dialog_list)
    RecyclerView recyclerView;
    @BindView(R.id.dialog_create_new_list_frame)
    LinearLayout createNewList;

    private List<String> listName;
    private Result result;
    private UserListDialogAdapter adapter;
    private AddToListDialogListener listener;

    public UserListDialogFragment() {
    }

    public static UserListDialogFragment newInstance(List<String> userListsName, Result result) {

        Bundle args = new Bundle();
        args.putStringArrayList(ARG_USER_LIST_NAME, (ArrayList<String>) userListsName);
        args.putParcelable(ARG_USER_RESULT, result);
        UserListDialogFragment fragment = new UserListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listName = new ArrayList<>();
        if(getArguments() != null) {
            listName = getArguments().getStringArrayList(ARG_USER_LIST_NAME);
            result = getArguments().getParcelable(ARG_USER_RESULT);
        }
        adapter = new UserListDialogAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_to_user_list, container);
        listener = (AddToListDialogListener) getTargetFragment();
        adapter.setListInteractionListener(this);
        init(view);
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if(listName == null) {
            return;
        } else {
            adapter.addItems(listName);
        }

        createNewList.setOnClickListener(v -> {
            listener.createNewListClick(result);
            dismiss();
        });
    }

    @Override
    public void onListSelected(String title, int position) {
        listener.addMovieToList(title, position, result);
        dismiss();
    }

    interface AddToListDialogListener {
        void createNewListClick(Result result);

        void addMovieToList(String name, int listId, Result result);
    }
}
