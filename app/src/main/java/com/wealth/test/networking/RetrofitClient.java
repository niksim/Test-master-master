package com.wealth.test.networking;

import com.wealth.test.AppConstants;
import com.wealth.test.models.UserData;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class RetrofitClient {

    private static final String TAG = "Retrofit";
    public static RetroApiInterface retroApiInterface;
    private final static String USER_DETAIL_URL = "api/people";

    public static RetroApiInterface getClient() {
        if (retroApiInterface == null) {

            OkHttpClient okClient = new OkHttpClient.Builder()
                    .build();
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
//                    .client(okClient)
                    .client(getUnsafeOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retroApiInterface = client.create(RetroApiInterface.class);
        }
        return retroApiInterface;
    }

    public interface RetroApiInterface {

        @GET(USER_DETAIL_URL)
        Call<UserData> loadUserDetails(@QueryMap Map<String, String> options);

    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).readTimeout(1200000, TimeUnit.MILLISECONDS).connectTimeout(1200000, TimeUnit.MILLISECONDS).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
