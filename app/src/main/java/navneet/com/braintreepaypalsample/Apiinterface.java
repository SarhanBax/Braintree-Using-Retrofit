package navneet.com.braintreepaypalsample;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apiinterface
{
    private RequestInterface getApiInstance()
    {
        HttpLoggingInterceptor logInter = new HttpLoggingInterceptor();
        logInter.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mIntercepter = new OkHttpClient.Builder()
                .addInterceptor(logInter)
                .build();
        String BASE_URL = "http://192.168.0.109/";
        Retrofit retrofitInstance = new Retrofit.Builder()
                //.addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(mIntercepter)
                .build();


        return retrofitInstance.create(RequestInterface.class);
    }
}

