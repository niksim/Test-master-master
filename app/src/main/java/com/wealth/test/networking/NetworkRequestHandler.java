package com.wealth.test.networking;

import com.wealth.test.models.UserData;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkRequestHandler {

    RetrofitResponseListener listener;
    private RetrofitClient.RetroApiInterface retroApiInterface;

    public NetworkRequestHandler(RetrofitResponseListener listener) {
//        retroApiInterface = RetrofitClient.getClient();
        retroApiInterface = RetrofitClient.getClient();
        this.listener = listener;
    }

    public NetworkRequestHandler getUserDetails(HashMap<String, String> options) {

        Call<UserData> callSBIProfileList = retroApiInterface.loadUserDetails(options);


        callSBIProfileList.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                listener.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                listener.onFailure(call, t);
            }
        });
        return this;
    }

    public interface RetrofitResponseListener<T> {
        void onResponse(Call<T> call, Response<T> response);

        void onFailure(Call<T> call, Throwable t);
    }

}
