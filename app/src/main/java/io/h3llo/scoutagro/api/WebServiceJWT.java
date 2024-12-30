package io.h3llo.scoutagro.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceJWT {

    // private static final String BASE_URL_JWT = "http://10.0.2.2:8046";
    // private static final String BASE_URL_JWT = "http://10.0.2.2:3000";
    // private static final String BASE_URL_JWT = "http://200.37.16.203:3000";
    private static final String BASE_URL_JWT = "http://192.168.1.76:3000";
    // private static final String BASE_URL_JWT = "http://190.116.45.202:3000";


    private static WebServiceJWT instance;
    private Retrofit retrofit;
    private HttpLoggingInterceptor logginInterceptor;
    private OkHttpClient.Builder httpClientBuilder;

    private WebServiceJWT(){
        logginInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(logginInterceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_JWT)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized WebServiceJWT getInstance(){
        if(instance == null){
            instance = new WebServiceJWT();
        }
        return instance;
    }

    public <S> S createService (Class<S> serviceClass ){
        return retrofit.create(serviceClass);

    }
}
