package com.example.prakhar.movieapp.ui.movie_detail;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/25/2017.
 */

public class UserListDialogAdapter extends
        RecyclerView.Adapter<UserListDialogAdapter.UserListViewHolder> {

    private List<String> stringList;
    private UserListDialogListener listener;

    public UserListDialogAdapter() {
        stringList = new ArrayList<>();
    }

    @Override
    public UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_add_to_user_list_item, parent, false);
        return new UserListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserListViewHolder holder, int position) {
        holder.userListName.setText(stringList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public void add(String item) {
        add(null, item);
    }

    public void add(@Nullable Integer position, String item) {
        if (position != null) {
            stringList.add(position, item);
            notifyItemInserted(position);
        } else {
            stringList.add(item);
            notifyItemInserted(stringList.size() - 1);
        }
    }

    public void addItems(List<String> userListName) {
        this.stringList.addAll(userListName);
        notifyItemRangeInserted(getItemCount(), this.stringList.size() - 1);
    }

    class UserListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dialog_add_to_user_list_item_layout)
        LinearLayout listItem;
        @BindView(R.id.user_list_dialog_list_name)
        TextView userListName;

        UserListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            listItem.setOnClickListener(v ->
                listener.onListSelected(stringList.get(getAdapterPosition()),
                        getAdapterPosition() + 1)
            );
        }
    }

    public interface UserListDialogListener {
        void onListSelected(String title, int position);
    }

    public  void setListInteractionListener(UserListDialogListener listDialogListener) {
        listener = listDialogListener;
    }
}
