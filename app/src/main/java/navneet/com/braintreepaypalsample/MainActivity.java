package navneet.com.braintreepaypalsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

public class MainActivity extends AppCompatActivity  implements Tokeninterface,Nonceinterface {

    private static final int REQUEST_CODE = 123;
    private Button paypal_button;
    private Context context;
    private Service service=new Service();
    private
    String tokens,payment_method_nonce;
    String amount="20";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
        paypal_button = findViewById(R.id.start_payment);


        paypal_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

               startPayment();
//             //   -->> add api to generate client token to call startPayment()
            //   onBraintreeSubmit(getString(R.string.test_client_token)); // -->> added for testing with sample client token, call this method from onResponse() of startPayment() only
            }
        });
    }


    @Override
    public void tokenclass(Modeltoken model)
    {
        tokens=model.getToken();
        onBraintreeSubmit(tokens);

    }
    private void startPayment()
    {

        service.tokeninterface=this;
        service.Tokendetails(context);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK)
            {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                payment_method_nonce = result.getPaymentMethodNonce().getNonce();
                sendnonce();
                Log.d("nonce",payment_method_nonce);
                //-->> create server side api to accept payment amount and PaymentNonce
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(this,"cancelled",Toast.LENGTH_LONG).show();
                Exception errora=(Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("err",errora.toString());
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Toast.makeText(this,"Something else",Toast.LENGTH_LONG).show();

                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("err",error.toString());

            }
        }
    }





    public void onBraintreeSubmit(String token)
    {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }
    public void sendnonce()
    {
        String Noncevalue=payment_method_nonce;
        String amountvalue=amount;
        service.nonceinterface=this;
        service.Noncedetails(context,Noncevalue,amountvalue);
    }

    @Override
    public void nonceclass(Noncemodel noncemodel)
    {
        if (noncemodel.getStatus().equals("Error"))
        {
            Toast.makeText(context,"not Success",Toast.LENGTH_LONG).show();
        } else
        {
            Toast.makeText(context,"Pay ho gya hai",Toast.LENGTH_LONG).show();
        }

    }
}
