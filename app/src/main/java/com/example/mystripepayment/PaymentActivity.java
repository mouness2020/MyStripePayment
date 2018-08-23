package com.example.mystripepayment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.mystripepayment.Models.BaseResponse;
import com.stripe.android.SourceCallback;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.exception.APIConnectionException;
import com.stripe.android.exception.APIException;
import com.stripe.android.exception.AuthenticationException;
import com.stripe.android.exception.CardException;
import com.stripe.android.exception.InvalidRequestException;
import com.stripe.android.model.Card;
import com.stripe.android.model.Source;
import com.stripe.android.model.SourceCardData;
import com.stripe.android.model.SourceParams;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    public String email = "";

    public String plan_id = "";

    public String subscription_id = "";

    public String currency = "USD";

    public long amount = 0L;

    private ProgressDialog pr;

    String source = null;

    String card_token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("email")) {

            email = getIntent().getStringExtra("email");
        }
        if (getIntent().hasExtra("plan_id")) {
            plan_id = getIntent().getStringExtra("plan_id");
        }
        if (getIntent().hasExtra("subscription_id")) {
            subscription_id = getIntent().getStringExtra("subscription_id");
        }

        if (getIntent().hasExtra("amount")) {
            amount = getIntent().getLongExtra("amount",0L);
        }

        if (getIntent().hasExtra("currency")) {
            currency = getIntent().getStringExtra("currency");
        }

        pr = new ProgressDialog(PaymentActivity.this);
        pr.setCancelable(false);

        Button payBtn = findViewById(R.id.payBtn);

        final CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

//        mCardInputWidget.setCardNumber("4000000000003063");
//        mCardInputWidget.setCvcCode("123");
//        mCardInputWidget.setExpiryDate(12, 20);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Card card = mCardInputWidget.getCard();
                if (card == null) {
                    // mErrorDialogHandler.showError("Invalid Card Data");
                    Toast.makeText(PaymentActivity.this, "خطأ في بيانات البطاقة", Toast.LENGTH_LONG).show();
                    return;
                }
                final SourceParams cardSourceParams = SourceParams.createCardParams(card);

                showProgress("الرجاء الانتظار...");

                final Stripe stripe = new Stripe(PaymentActivity.this, MyStripePayment.pk_key);
                stripe.createToken(
                        card,
                        new TokenCallback() {
                            public void onSuccess(final Token token) {

                                stripe.createSource(
                                        cardSourceParams,
                                        new SourceCallback() {
                                            @Override
                                            public void onSuccess(Source cardSource) {
                                                // Store the source somewhere, use it, etc
                                                // create your source as described
                                                SourceCardData cardData = (SourceCardData) cardSource.getSourceTypeModel();
                                                String threeDStatus = cardData.getThreeDSecureStatus();
                                                if (SourceCardData.REQUIRED.equals(threeDStatus)) {

                                                    Utils.showMessage("خطأ","البطاقة الخاصة بك غير مدعومه بسبب البنك يضع قيود الزامية في التحقق",PaymentActivity.this);

                                                  /* String cardSourceId = cardSource.getId();
                                                    card_token = cardSourceId;

                                                    final SourceParams threeDParams = SourceParams.createThreeDSecureParams(
                                                            amount, // some price: this represents 10.00 EUR
                                                            currency, // a currency
                                                            "islamicency://" + MyStripePayment.package_name, // your redirect
                                                            cardSourceId);

                                                    stripe.createSource(threeDParams, new SourceCallback() {
                                                        @Override
                                                        public void onError(Exception error) {

                                                            Toast.makeText(PaymentActivity.this,
                                                                    error.getLocalizedMessage(),
                                                                    Toast.LENGTH_LONG
                                                            ).show();
                                                        }

                                                        @Override
                                                        public void onSuccess(Source mThreeDSource) {

                                                            String externalUrl = mThreeDSource.getRedirect().getUrl();
                                                            source = mThreeDSource.getId();
                                                            Intent intent = new Intent(PaymentActivity.this,WebActivity.class);
                                                            intent.putExtra("url",externalUrl);
                                                            startActivityForResult(intent,1235);
                                                        }
                                                    });
                                                    // this is the case where you would need to conduct a 3DS check
                                                    //}
                                                    //else if (SourceCardData.OPTIONAL.equals(threeDStatus)) {


                                                    // In this case, a 3DS step is optional. You should be able to charge the card immediately.
                                                    //}
                                                    //else if (SourceCardData.UNKNOWN.equals(threeDStatus)) {


                                                    // This case should be rare.*/
                                                } else {

                                                    doCreateCustomer(email, token.getId());

                                                    // the value will be SourceCardData.NOT_SUPPORTED, and you're free to charge the card.
                                                }
                                                hideProgress();
                                            }

                                            @Override
                                            public void onError(Exception error) {
                                                hideProgress();

                                                // Tell the user that something went wrong
                                                Toast.makeText(PaymentActivity.this,
                                                        error.getLocalizedMessage(),
                                                        Toast.LENGTH_LONG
                                                ).show();
                                            }
                                        });
                            }

                            public void onError(Exception error) {
                                hideProgress();
                                // Show localized error message
                                Toast.makeText(PaymentActivity.this,
                                        error.getLocalizedMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1235){

            if(resultCode == 33){

                Utils.showMessage("خطأ","حدث خطأ ما الرجاء المحاولة ببطاقة اخرى",PaymentActivity.this);
            }
            else if(resultCode == 11){

                //Utils.showMessage("خطأ","حدث خطأ ما الرجاء المحاولة ببطاقة اخرى",PaymentActivity.this);
                //doCreateCustomer(email,source,card_token);
                doSubscription(email,plan_id,source,card_token);

            }
            else if(resultCode == 22){

                Utils.showMessage("خطأ","فشل التحقق من البطاقة الخاصة بك",PaymentActivity.this);
            }
        }
    }

    private void doCreateCustomer(final String email, final String token) {

        showProgress("الرجاء الانتظار...");
        Call<BaseResponse> responseCall = Retrofit2Client.getInstance().getApiService().postCreateCustomer(email, token);


        responseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                hideProgress();
                Log.e("Tag", response.message());

                if (response.body() != null && response.body().getResponse() != null && response.body().getResponse().equals("Success")) {


                    if (response.body().getCustomer() != null) {

                        // do subc
                        doSubscription(response.body().getCustomer().getId(), plan_id,subscription_id);

                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                hideProgress();
                Log.e("Tag", t.getLocalizedMessage());
                Toast.makeText(PaymentActivity.this, t.getLocalizedMessage() + "", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void doSubscription(final String email, final String plan_id,String source,String card_token) {

        showProgress("الرجاء الانتظار...");
        Call<BaseResponse> responseCall = Retrofit2Client.getInstance().getApiService().postSubscription(email, plan_id,source,card_token);

        responseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                hideProgress();
                Log.e("Tag", response.message());

                if (response.body() != null && response.body().getResponse() != null && response.body().getResponse().equals("Success")) {


                    if (response.body().getSubscription() != null) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                        builder.setMessage("تم الاشتراك بنجاح.. شكرا لك.")
                                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // FIRE ZE MISSILES!

                                        setResult(RESULT_OK);
                                        finish();

                                    }
                                });
//                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        // User cancelled the dialog
//                                    }
//                                });
                        builder.create().show();
                        //Toast.makeText(PaymentActivity.this,response.body().getSubscription().getStatus(),Toast.LENGTH_LONG).show();

                    }

                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                hideProgress();
                Log.e("Tag", t.getLocalizedMessage());
                Toast.makeText(PaymentActivity.this, t.getLocalizedMessage() + "", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void doSubscription(final String customer_id, final String plan_id,final String subscription_id) {

        showProgress("الرجاء الانتظار...");
        Call<BaseResponse> responseCall = null;
        if (subscription_id == null || subscription_id.equals("")) {

            responseCall = Retrofit2Client.getInstance().getApiService().postSubscription(customer_id, plan_id);

        } else {

            responseCall = Retrofit2Client.getInstance().getApiService().postUpgradeDownGradeSubscription(subscription_id, plan_id);
        }

        responseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                hideProgress();
                Log.e("Tag", response.message());

                if (response.body() != null && response.body().getResponse() != null && response.body().getResponse().equals("Success")) {


                    if (response.body().getSubscription() != null) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                        builder.setMessage("تم الاشتراك بنجاح.. شكرا لك.").setCancelable(false)
                                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // FIRE ZE MISSILES!

                                        setResult(RESULT_OK);
                                        finish();

                                    }
                                });
//                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        // User cancelled the dialog
//                                    }
//                                });
                        builder.create().show();
                        //Toast.makeText(PaymentActivity.this,response.body().getSubscription().getStatus(),Toast.LENGTH_LONG).show();

                    }

                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                hideProgress();
                Log.e("Tag", t.getLocalizedMessage());
                Toast.makeText(PaymentActivity.this, t.getLocalizedMessage() + "", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void showProgress(String content) {
        pr.setMessage(content);
        if (!pr.isShowing()) {
            pr.show();

        }

    }

    public void hideProgress() {
        if (pr.isShowing()) pr.dismiss();
    }

}

