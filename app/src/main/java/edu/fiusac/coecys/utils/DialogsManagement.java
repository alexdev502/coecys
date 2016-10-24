package edu.fiusac.coecys.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import edu.fiusac.coecys.R;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class DialogsManagement {

    public AlertDialog alertNoInternet(Context context) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(context.getString(R.string.sin_conexion));
        builder1.setMessage(context. getString(R.string.sin_conexion_mensaje));
        builder1.setCancelable(true);
        builder1.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alert11 = builder1.create();
        return  alert11;
    }
}
