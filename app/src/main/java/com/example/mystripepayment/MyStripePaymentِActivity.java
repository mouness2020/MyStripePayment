package com.example.mystripepayment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystripepayment.Models.BaseResponse;
import com.example.mystripepayment.Models.Plan;
import com.example.mystripepayment.Models.Subscription;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mystripepayment.Utils.getClientEmail;
import static com.example.mystripepayment.Utils.getDate;
import static com.example.mystripepayment.Utils.isValidEmail;
import static com.example.mystripepayment.Utils.saveClientEmail;

public class MyStripePaymentِActivity extends AppCompatActivity {

    private TextView txtSubscriptionDesc;

    private TextView txt1month;
    private TextView txt6month;
    private TextView txt12month;

    private Button btn1month;
    private Button btn6month;
    private Button btn12month;

    private Button btnGetPayment;

    private String plan_id = "";

    private ProgressDialog pr;

    private Subscription subscription = null;

    private boolean isThereActiveSubscription = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stripe_payment);
        init();

    }


    private void init() {

        pr = new ProgressDialog(MyStripePaymentِActivity.this);



        txt1month = findViewById(R.id.txt1month);
        txt6month = findViewById(R.id.txt6month);
        txt12month = findViewById(R.id.txt12month);

        btn1month = findViewById(R.id.btn1month);
        btn6month = findViewById(R.id.btn6month);
        btn12month = findViewById(R.id.btn12month);

        btnGetPayment = findViewById(R.id.btnGetPayment);

        txtSubscriptionDesc = findViewById(R.id.txtSubscriptionDesc);


        getPlanList();

        getActiveSubscriptions(false);

        setPaymentListener();


    }


    private void setPaymentListener() {

        btn1month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doPayment(btn1month);
            }
        });

        btn6month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doPayment(btn6month);

            }
        });

        btn12month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doPayment(btn12month);

            }
        });

        btnGetPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = getClientEmail(MyStripePaymentِActivity.this);
                if (email != null && !email.equals("")) {

                    getActiveSubscriptions(true);

                } else {

                    showEmailDialog(true);

                }
            }
        });
    }

    private void doPayment(Button btn) {

        if (btn.getText().toString().equals("تم الاشتراك")) {// && !subscription.getCancelAtPeriodEnd()

            if(subscription != null) {
                showSubscriptionDetailsDialog(subscription);
            }

        } else {

            if(!isThereActiveSubscription) {
                plan_id = (String) btn.getTag();

                String email = getClientEmail(MyStripePaymentِActivity.this);
                if (email != null) {

                    doCreateCustomer(email + "");

                } else {

                    showEmailDialog(false);

                }
            }
            else{

                Utils.showMessage(
                        "تنويه",
                        "يوجد اشتراك فعال حاليا يجب ان يتم الغاء الاشتراك الحالي وذلك الضغط على (تم الاشتراك) ومن تم انتظار انتهائه",
                        MyStripePaymentِActivity.this
                );
            }
        }
    }

    private void getPlanList() {

        showProgress("الرجاء الانتظار...");
        Retrofit2Client.getInstance().getApiService().getPlanList().enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                hideProgress();
                Log.e("Tag", response.message());

                if (response.body() != null && response.body().getResponse() != null && response.body().getResponse().equals("Success")) {


                    if (response.body().getPlans() != null) {

                        List<Plan> planList = response.body().getPlans();


                        for (Plan plan : planList) {

                            if (plan.getInterval().equals("month") && plan.getIntervalCount() == 1) {


                                btn1month.setTag(plan.getId() + "");
                                txt1month.setText(txt1month.getText() + " " + (plan.getAmount() / 100.0) + "" + plan.getCurrency().replace("usd", "$"));
                            } else if (plan.getInterval().equals("month") && plan.getIntervalCount() == 6) {

                                btn6month.setTag(plan.getId() + "");
                                txt6month.setText(txt6month.getText() + " " + (plan.getAmount() / 100.0) + "" + plan.getCurrency().replace("usd", "$"));
                            } else if (plan.getInterval().equals("year") && plan.getIntervalCount() == 1) {

                                btn12month.setTag(plan.getId() + "");
                                txt12month.setText(txt12month.getText() + " " + (plan.getAmount() / 100.0) + "" + plan.getCurrency().replace("usd", "$"));
                            }
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                hideProgress();
                Log.e("Tag", t.getLocalizedMessage());

            }
        });
    }

    private void getActiveSubscriptions(final boolean isGetPayment) {

        String email = getClientEmail(MyStripePaymentِActivity.this);
        if (email == null) {
            if(MyStripePayment.result != null) {
                MyStripePayment.result.noSubscription();
            }
            return;
        }
        showProgress("الرجاء الانتظار...");
        Retrofit2Client.getInstance().getApiService().getSubscription(email).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                hideProgress();
                Log.e("Tag", response.message());

                if (response.body() != null && response.body().getResponse() != null && response.body().getResponse().equals("Success")) {


                    Subscription subscription = response.body().getSubscription();

                    if (subscription != null) {

                        if (subscription.getStatus().toLowerCase().equals("active")) {//

                            MyStripePaymentِActivity.this.subscription = subscription;
                            txtSubscriptionDesc.setVisibility(View.VISIBLE);

                            if (subscription.getPlan().getInterval().equals("month") && subscription.getPlan().getIntervalCount() == 1) {

                                btn1month.setText("تم الاشتراك");
                                btn1month.setTag(subscription.getPlan().getId() + "");

                                isThereActiveSubscription = true;
                                txtSubscriptionDesc.setText("اشتراك لمدة شهر وينتهي بتاريخ " + getDate(subscription.getCurrentPeriodEnd()));

                            } else if (subscription.getPlan().getInterval().equals("month") && subscription.getPlan().getIntervalCount() == 6) {

                                btn6month.setText("تم الاشتراك");
                                btn6month.setTag(subscription.getPlan().getId() + "");

                                isThereActiveSubscription = true;
                                txtSubscriptionDesc.setText("اشتراك لمدة 6 شهور وينتهي بتاريخ " + getDate(subscription.getCurrentPeriodEnd()));


                            } else if (subscription.getPlan().getInterval().equals("year") && subscription.getPlan().getIntervalCount() == 1) {

                                btn12month.setText("تم الاشتراك");
                                btn12month.setTag(subscription.getPlan().getId() + "");

                                isThereActiveSubscription = true;
                                txtSubscriptionDesc.setText("اشتراك لمدة 12 شهر وينتهي بتاريخ " + getDate(subscription.getCurrentPeriodEnd()));

                            }

                            if(isThereActiveSubscription){

                                if(MyStripePayment.result != null) {
                                    MyStripePayment.result.activeSubscription(subscription);
                                }
                            }
                        }
                        else{
                            isThereActiveSubscription = false;
                            if(MyStripePayment.result != null) {
                                MyStripePayment.result.noSubscription();
                            }
                            if(isGetPayment){
                                saveClientEmail(MyStripePaymentِActivity.this,  null);
                                Utils.showMessage("رسالة","لا يوجد اشتراك فعال على هذا الايميل" ,MyStripePaymentِActivity.this);
                            }

                        }


                    }
                    else{

                        isThereActiveSubscription = false;
                        if(MyStripePayment.result != null) {
                            MyStripePayment.result.noSubscription();
                        }
                        if(isGetPayment){
                            saveClientEmail(MyStripePaymentِActivity.this,  null);
                            Utils.showMessage("رسالة","لا يوجد اشتراك فعال على هذا الايميل" ,MyStripePaymentِActivity.this);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                hideProgress();
                Log.e("Tag", t.getLocalizedMessage());

            }
        });
    }

    private void cancelSubscriptions() {

        String email = getClientEmail(MyStripePaymentِActivity.this);
        if (email == null) {
            return;
        }
        showProgress("الرجاء الانتظار...");
        Retrofit2Client.getInstance().getApiService().cancelSubscription(subscription.getId(),true).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                hideProgress();
                Log.e("Tag", response.message());

                if (response.body() != null && response.body().getResponse() != null && response.body().getResponse().equals("Success")) {


                    Subscription subscription = response.body().getSubscription();
                    if (subscription != null) {


                        getActiveSubscriptions(false);


                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                hideProgress();
                Log.e("Tag", t.getLocalizedMessage());

            }
        });
    }

    private void doCreateCustomer(final String email) {

        showProgress("الرجاء الانتظار...");
        Call<BaseResponse> responseCall = Retrofit2Client.getInstance().getApiService().postCreateCustomer(email);


        responseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                hideProgress();
                Log.e("Tag", response.message());

                if (response.body() != null && response.body().getResponse() != null && response.body().getResponse().equals("Success")) {


                    if (response.body().getCustomer() != null) {

                        saveClientEmail(MyStripePaymentِActivity.this,email + "");

                        Intent intent = new Intent(MyStripePaymentِActivity.this, PaymentActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("plan_id", plan_id);

                        if(MyStripePaymentِActivity.this.subscription != null) {
                            intent.putExtra("subscription_id", MyStripePaymentِActivity.this.subscription.getId());
                        }

                        startActivityForResult(intent,1234);

                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                hideProgress();
                Log.e("Tag", t.getLocalizedMessage());
                Toast.makeText(MyStripePaymentِActivity.this, t.getLocalizedMessage() + "", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1234 && resultCode == RESULT_OK){

            getActiveSubscriptions(false);

        }

    }

    private void showEmailDialog(final boolean isGetPayment) {

        LayoutInflater li = LayoutInflater.from(MyStripePaymentِActivity.this);
        View prompt = li.inflate(R.layout.email_dailog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyStripePaymentِActivity.this);
        alertDialogBuilder.setView(prompt);
        final EditText email_address = (EditText) prompt.findViewById(R.id.email_address);
        alertDialogBuilder.setTitle("الرجاء ادخال البريد الالكتروني");
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("ارسال", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String email = email_address.getText().toString().trim();

                        if (isValidEmail(email)) {
                            if(!isGetPayment) {
                                doCreateCustomer(email + "");
                            }
                            else {
                                saveClientEmail(MyStripePaymentِActivity.this,email + "");

                                getActiveSubscriptions(isGetPayment);
                            }
                            dialog.cancel();

                        } else {
                            Toast.makeText(MyStripePaymentِActivity.this, "الرجاء كتابة البريد الالكتروني بشكل صحيح", Toast.LENGTH_LONG).show();
                        }

                    }
                });

        alertDialogBuilder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        alertDialogBuilder.show();

    }

    private void showSubscriptionDetailsDialog(Subscription subscription) {

        LayoutInflater li = LayoutInflater.from(MyStripePaymentِActivity.this);
        View prompt = li.inflate(R.layout.subscription_details_dailog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyStripePaymentِActivity.this);
        alertDialogBuilder.setView(prompt);
        final TextView start = (TextView) prompt.findViewById(R.id.txtStartDate);
        final TextView end = (TextView) prompt.findViewById(R.id.txtEndDate);
        final TextView email = (TextView) prompt.findViewById(R.id.txtEmail);

        email.setText("البريد الالكتروني: " + getClientEmail(MyStripePaymentِActivity.this) + "");

        start.setText("بداية الاشتراك: " + getDate(subscription.getCurrentPeriodStart()));
        end.setText("نهاية الاشتراك: " + getDate(subscription.getCurrentPeriodEnd()));

        alertDialogBuilder.setTitle("تفاصيل الاشتراك");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                        dialog.cancel();
                    }
                });

        if (!subscription.getCancelAtPeriodEnd())
        alertDialogBuilder.setNegativeButton("الغاء الاشتراك", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MyStripePaymentِActivity.this);
                builder.setMessage("هل انت متأكد من عملية الغاء الاشتراك؟ مع العلم سوف يستمر الاشتراك الحالي حتى نهاية تاريخه ولن يتم التجديد")
                        .setPositiveButton("نعم.", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                cancelSubscriptions();

                            }
                        })
                        .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        });

                builder.create().show();

                dialog.cancel();

            }
        });

        alertDialogBuilder.show();

    }

    private void showEmailVerfiyDialog(String email_address) {
        LayoutInflater li = LayoutInflater.from(MyStripePaymentِActivity.this);
        View prompt = li.inflate(R.layout.email_dailog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyStripePaymentِActivity.this);
        alertDialogBuilder.setView(prompt);
        final EditText email_dailog = (EditText) prompt.findViewById(R.id.email_address);
        email_dailog.setVisibility(View.GONE);
        final EditText code = (EditText) prompt.findViewById(R.id.code);

        code.setVisibility(View.VISIBLE);
        //final EditText pass = (EditText) prompt.findViewById(R.id.login_password);
        //user.setText(Login_USER); //login_USER and PASS are loaded from previous session (optional)
        //pass.setText(Login_PASS);
        alertDialogBuilder.setTitle("الرجاء ادخال الايميل");
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("تحقق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                        //  String password = pass.getText().toString();
                        // String username = user.getText().toString();
                        try {

//                            if ( username.length()<2 || password.length()<2)
//                            {
//                                Toast.makeText(getContext(),"Invalid username or password", Toast.LENGTH_LONG).show();
//                                showLoginDialog();
//                            }
//                            else
//                            {
//
//                            }
                        } catch (Exception e) {
                            Toast.makeText(MyStripePaymentِActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        alertDialogBuilder.show();

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
