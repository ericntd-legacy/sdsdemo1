package com.example.eric.sdsdemo1.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/*
 * Utility class to display dialogs
 */
public class DialogUtils {
    /*
     * display a dialog showing the given error message
     */
    public static void displayError(Activity activity, String errorMessage) {
        final Activity tmp = activity;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Oops");
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tmp.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}