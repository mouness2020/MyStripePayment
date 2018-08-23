package com.example.mystripepayment;

import com.example.mystripepayment.Models.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyApiEndpointInterface {
    // Request method and URL specified in the annotation

//    @GET("users/{username}")
//    Call<User> getUser(@Path("username") String username);
//
//    @GET("group/{id}/users")
//    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @GET("plans.php")
    Call<BaseResponse> getPlanList();

    @FormUrlEncoded
    @POST("customer.php")
    Call<BaseResponse> postCreateCustomer(@Field("email") String email);

    @FormUrlEncoded
    @POST("customer.php")
    Call<BaseResponse> postCreateCustomer(@Field("email") String email,@Field("source") String token);

    @FormUrlEncoded
    @POST("subscription.php")
    Call<BaseResponse> postSubscription(@Field("customer") String customer_id, @Field("plan") String plan_id);//do subsc

    @FormUrlEncoded
    @POST("subscription.php")
    Call<BaseResponse> postSubscription(@Field("email") String email, @Field("plan") String plan_id,@Field("source")String source,@Field("card_token")String card_token);//do subsc

    @FormUrlEncoded
    @POST("subscription.php")
    Call<BaseResponse> getSubscription(@Field("email") String email);//get active subscr

    @FormUrlEncoded
    @POST("subscription.php")
    Call<BaseResponse> cancelSubscription(@Field("subscription") String subscription_id,@Field("cancel") boolean cancel);

    @FormUrlEncoded
    @POST("subscription.php")
    Call<BaseResponse> postUpgradeDownGradeSubscription(@Field("subscription") String subscription_id, @Field("plan") String plan_id);//upgrade downgrade

    @FormUrlEncoded
    @POST("addcard.php")
    Call<BaseResponse> postAddCard(@Field("customer") String customer_id, @Field("source") String source);// add card for 3d secure

}