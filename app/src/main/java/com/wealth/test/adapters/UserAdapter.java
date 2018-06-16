package com.wealth.test.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wealth.test.R;
import com.wealth.test.models.Result;
import com.wealth.test.models.UserData;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<Result> userList;
    private UserClickListener userClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name;
        private View viewContainer;

        public MyViewHolder(View view) {
            super(view);
            viewContainer = view;
            user_name = (TextView) view.findViewById(R.id.user_name);
        }
    }


    public UserAdapter(List<Result> userList) {
        this.userList = userList;
    }

    public void setUserClickListener(UserClickListener userClickListener) {
        this.userClickListener = userClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Result result = userList.get(position);
        holder.user_name.setText(result.getName());

        holder.viewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickListener.onItemClick(result);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public interface UserClickListener {
        void onItemClick(Result Result);
    }
}