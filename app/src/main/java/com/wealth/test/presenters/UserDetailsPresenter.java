package com.wealth.test.presenters;

import android.util.Log;

import com.wealth.test.imp_contracts.UserDetailContract;
import com.wealth.test.models.UserData;
import com.wealth.test.networking.NetworkRequestHandler;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class UserDetailsPresenter implements UserDetailContract.UserActionsListener{

    UserDetailContract.View view;

    public UserDetailsPresenter(UserDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void callUserDetailsApi() {
        view.showLoadingScreen();

        HashMap<String, String> map = new HashMap<>();
        map.put("format", "json");

        new NetworkRequestHandler(new NetworkRequestHandler.RetrofitResponseListener<UserData>() {

            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                view.showListDataScreen();
                if(response.isSuccessful()){
                    UserData userDatas = response.body();
                    if(userDatas != null){
                        view.updateUserData(userDatas);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                view.showErrorSnackie();
            }
        }).getUserDetails(map);

    }
}
