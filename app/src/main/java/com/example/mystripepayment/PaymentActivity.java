package com.example.mystripepayment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mystripepayment.Models.BaseResponse;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    public String email = "";

    public String plan_id = "";

    public String subscription_id = "";

    private ProgressDialog pr;

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

        pr = new ProgressDialog(PaymentActivity.this);
        pr.setCancelable(false);

        Button payBtn = findViewById(R.id.payBtn);

        final CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

        mCardInputWidget.setCardNumber("4000003800000008");
        mCardInputWidget.setCvcCode("123");
        mCardInputWidget.setExpiryDate(12, 20);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Card cardToSave = mCardInputWidget.getCard();
                if (cardToSave == null) {
                    // mErrorDialogHandler.showError("Invalid Card Data");
                    Toast.makeText(PaymentActivity.this, "خطأ في بيانات البطاقة", Toast.LENGTH_LONG).show();
                    return;
                }

                showProgress("الرجاء الانتظار...");

                Stripe stripe = new Stripe(PaymentActivity.this, MyStripePayment.pk_key);
                stripe.createToken(
                        cardToSave,
                        new TokenCallback() {
                            public void onSuccess(Token token) {

                                hideProgress();
                                // Send token to your server
//                                Toast.makeText(PaymentActivity.this,
//                                        token.getId(),
//                                        Toast.LENGTH_LONG
//                                ).show();
                                doCreateCustomer(email, token.getId());
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

