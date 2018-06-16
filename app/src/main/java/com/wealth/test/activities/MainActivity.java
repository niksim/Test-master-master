package com.wealth.test.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wealth.test.R;
import com.wealth.test.adapters.UserAdapter;
import com.wealth.test.imp_contracts.UserDetailContract;
import com.wealth.test.models.Result;
import com.wealth.test.models.UserData;
import com.wealth.test.presenters.UserDetailsPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements UserAdapter.UserClickListener,UserDetailContract.View{

    UserDetailContract.UserActionsListener userActionsListener;
    private List<Result> userList = new ArrayList<>();
    private UserAdapter mAdapter;

    CoordinatorLayout mRootView;
    ProgressBar pbProgress;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userActionsListener.callUserDetailsApi();
    }

    @Override
    public void initSetContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initComponents() {
        userActionsListener = new UserDetailsPresenter(this);

        recyclerView = findViewById(R.id.recycler_view);

        mAdapter = new UserAdapter(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        pbProgress = findViewById(R.id.pbProgress);
        mRootView = findViewById(R.id.root_view);
    }

    @Override
    public void initListeners() {
        mAdapter.setUserClickListener(this);
    }

    @Override
    public void updateUserData(UserData userData) {
        userList.clear();
        userList.addAll(userData.getResults());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingScreen() {
        pbProgress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showListDataScreen() {
        pbProgress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorSnackie() {
        pbProgress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        Snackbar snackbar = Snackbar
                .make(mRootView, "Try again!", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userActionsListener.callUserDetailsApi();
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView =  sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    public void onItemClick(Result result) {
        Intent intent = new Intent(MainActivity.this, DetailScreen.class);
        intent.putExtra("Result", result);
        startActivity(intent);
    }
}
