package com.example.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mystripepayment.Models.Subscription;
import com.example.mystripepayment.MyStripePayment;
import com.example.mystripepayment.onActiveSubscriptionResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        MyStripePayment.serverURL = "https://islamicency.com/stripe/quran/";
        MyStripePayment.pk_key = "pk_test_WB4m4bC3fHGcOsXgjK2i5BYm";

        Button btnSub = findViewById(R.id.btnSub);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyStripePayment.startPaymentActivityِ(MainActivity.this, new onActiveSubscriptionResult() {
                    @Override
                    public void activeSubscription(Subscription subscription) {
                        Log.e("MainActivity","StartPaymentActivityِ activeSubscription: " + subscription.getId());

                    }

                    @Override
                    public void noSubscription() {

                        Log.e("MainActivity","StartPaymentActivityِ noSubscription");

                    }
                });

            }
        });


        MyStripePayment.getActiveSubscriptions(MainActivity.this, new onActiveSubscriptionResult() {
            @Override
            public void activeSubscription(Subscription subscription) {

                Log.e("MainActivity","activeSubscription: " + subscription.getId());
            }

            @Override
            public void noSubscription() {

                Log.e("MainActivity","noSubscription");

            }
        });
    }
}
