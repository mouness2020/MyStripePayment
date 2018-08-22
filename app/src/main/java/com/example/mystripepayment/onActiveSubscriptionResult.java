package com.example.mystripepayment;

import com.example.mystripepayment.Models.Subscription;

public interface onActiveSubscriptionResult{

    void activeSubscription(Subscription subscription);

    void noSubscription();

}