package com.example.mystripepayment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mystripepayment.Models.BaseResponse;
import com.example.mystripepayment.Models.Subscription;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mystripepayment.Utils.getClientEmail;

public class MyStripePayment {

    public static String serverURL = "https://islamicency.com/stripe/quran/";
    public static String pk_key = "pk_test_WB4m4bC3fHGcOsXgjK2i5BYm";

    public static onActiveSubscriptionResult result = null;

    public static void startPaymentActivityِ(Activity activity,final onActiveSubscriptionResult resultSubs){


        Intent intent = new Intent(activity,MyStripePaymentِActivity.class);

        try {
            result =  resultSubs;
        }catch (Exception e){

            e.printStackTrace();
        }
        activity.startActivity(intent);


    }

    public static void getActiveSubscriptions(Context context, final onActiveSubscriptionResult result) {

        String email = getClientEmail(context);
        if (email == null) {

            result.noSubscription();
            return;
        }

        Retrofit2Client.getInstance().getApiService().getSubscription(email).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                Log.e("Tag", response.message());

                if (response.body() != null && response.body().getResponse() != null && response.body().getResponse().equals("Success")) {


                    Subscription subscription = response.body().getSubscription();

                    if (subscription != null) {

                        if (subscription.getStatus().toLowerCase().equals("active")) {//

                           // MyStripePaymentِActivity.this.subscription = subscription;
                            result.activeSubscription(subscription);


                        }
                       else{

                            result.noSubscription();

                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                Log.e("Tag", t.getLocalizedMessage());

            }
        });
    }

}
