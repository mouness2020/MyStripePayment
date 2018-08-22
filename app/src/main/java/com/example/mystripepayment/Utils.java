package com.example.mystripepayment;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {


    public static String getClientEmail(Context context) {


        MySharedPreference mySharedPreference = MySharedPreference.getInstance(context);
        return mySharedPreference.getData("email");


    }

    public static void saveClientEmail(Context context,String email) {


        MySharedPreference mySharedPreference = MySharedPreference.getInstance(context);
        mySharedPreference.saveData("email", email);

    }

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static String getDate(long timeStamp) {

        try {
            long da = timeStamp * 1000L;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Date netDate = (new Date(da));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "مجهول";
        }
    }


    public static void showMessage(String title, String message, Context context) {

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage(message).setTitle(title)
                    .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            // Create the AlertDialog object and return it
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
