package navneet.com.braintreepaypalsample;

import com.google.gson.annotations.SerializedName;

public class Modeltoken
{
    @SerializedName("token")
    private String token;

    public String getToken()
    {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
