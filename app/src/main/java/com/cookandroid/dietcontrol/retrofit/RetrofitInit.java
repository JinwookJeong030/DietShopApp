package com.cookandroid.dietcontrol.retrofit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.dietcontrol.MainActivity;
import com.cookandroid.dietcontrol.util.ErrorDialog;
import com.cookandroid.dietcontrol.util.UtilStr;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInit {
    private static OkHttpClient client;
    private static CookieManager cookieManager = new CookieManager();
    private static Retrofit retrofit;
    private static RetrofitAPI api;


    public static RetrofitAPI getRetrofit(){

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);


    client = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(3,TimeUnit.SECONDS)
            .writeTimeout(3,TimeUnit.SECONDS)
            .addNetworkInterceptor(new LogInterceptor())
            .cookieJar(new JavaNetCookieJar(cookieManager))
            .build();


    retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl(UtilStr.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    api = retrofit.create(RetrofitAPI.class);


        return api;
    }


public static class LogInterceptor implements Interceptor{

        private static final String TAG = LogInterceptor.class.getSimpleName();
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Log.d(TAG,"intercept:request method :"+ request.method());
        Log.d(TAG,"intercept:request url :"+ request.method());
        Log.d(TAG,"intercept:request connection :"+ chain.connection());

        Response response = chain.proceed(request);
        Log.d(TAG,"--------------------------------------------");
        Log.d(TAG,"intercept:response request url "+ response.request().url());
        Log.d(TAG,"intercept:response header "+ response.headers());


        return response;

    }
}


}
