package navneet.com.braintreepaypalsample;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Navneet Krishna on 21/01/19.
 */
public interface RequestInterface {

    @GET("token")
    Call<Modeltoken> getClientToken();

    @GET("noncetoken")
    Call<Noncemodel> getPayment(@Query("payment_method_token") String nonce,
                                  @Query("payment") String amount);

}
