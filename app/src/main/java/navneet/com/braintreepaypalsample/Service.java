package navneet.com.braintreepaypalsample;

import android.content.Context;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service

{
   public Tokeninterface tokeninterface=null;
   public Nonceinterface nonceinterface=null;

   public void Noncedetails(final Context context,String Nonce,String Amount)
   {
      RequestInterface requestInterface=getApiInstance(context);
      try {
          requestInterface.getPayment(Nonce,Amount)
                  .enqueue(new Callback<Noncemodel>() {
                      @Override
                      public void onResponse(Call<Noncemodel> call, Response<Noncemodel> response) {
                          if (response.isSuccessful())
                          {
                              String res=response.message();
                              Toast.makeText(context,res,Toast.LENGTH_LONG).show();
                              Noncemodel noncemodel=response.body();
                              try {
                                  nonceinterface.nonceclass(noncemodel);
                              }catch (Exception e)
                              {
                                  e.printStackTrace();
                              }
                          } else
                          {
                              String res=response.message();
                              Toast.makeText(context,res,Toast.LENGTH_LONG).show();
                          }
                      }

                      @Override
                      public void onFailure(Call<Noncemodel> call, Throwable t)
                      {
                        String msg=t.getMessage();
                        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
                      }
                  });
      } catch (Exception e)
      {
          e.printStackTrace();
      }
   }

   public void Tokendetails(final Context context)
   {
       RequestInterface requestInterface=getApiInstance(context);
       try {
           requestInterface.getClientToken()
                   .enqueue(new Callback<Modeltoken>() {
                       @Override
                       public void onResponse(Call<Modeltoken> call, Response<Modeltoken> response) {
                           if (response.isSuccessful())
                           {
                               Toast.makeText(context,"successfull",Toast.LENGTH_LONG).show();
                               Modeltoken modeltoken=response.body();
                               try {
                                   tokeninterface.tokenclass(modeltoken);
                               }catch (Exception e)
                               {
                                   e.printStackTrace();
                               }
                           }
                       }

                       @Override
                       public void onFailure(Call<Modeltoken> call, Throwable t) {
                        String message =t.getMessage();
                        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                       }
                   });
       } catch (Exception e)
       {
           e.printStackTrace();
       }
   }
    private RequestInterface getApiInstance(Context context)
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
