package app.modelo.meusclientes.api;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.widget.Toast;

import app.modelo.meusclientes.R;

public class AppUtil {
    public static final String URL_WEB_SERVICE="http://projetoteste.6te.net/";
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIME_OUT=15000;
    public static final String TAG = "DB_Crud";

    public static void toast(Context context , String texto){
        Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();



    }
public static void progresDialog(Context context , String mns){

    final ProgressDialog progressDialog ;
    progressDialog=new ProgressDialog(context);
    progressDialog.setMessage(mns);
    progressDialog.setCancelable(false);
    progressDialog.show();
    progressDialog.dismiss();



}
  /*  verificar se o aparelho possui internet usando Wifi ou Dados móveis, conforme o código a
  * obrigatório acrescentar as seguintes permiossoes :
  *
  *<uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

  * */
    public static boolean isConnected(Context cont){
        ConnectivityManager conmag = (ConnectivityManager)cont.getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conmag != null ) {
            conmag.getActiveNetworkInfo();

            //Verifica internet pela WIFI
            if (conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                return true;
            }

            //Verifica se tem internet móvel
            if (conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                return true;
            }
        }

        return false;
    }

//DIALOG COMUM
    public static void alertDialog(Context context,String msg){

        AlertDialog.Builder build;
        AlertDialog alert;
        build = new AlertDialog.Builder(context);
        build.setTitle("ALERTA");
        build.setMessage(msg);
        build.setCancelable(false);
        build.setIcon(R.mipmap.ic_launcher);

        build.setPositiveButton("OK", new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.cancel();
            }
        });



        alert = build.create();
        alert.show();



    }
    //DIALOG PERGUNTA
    public static void alertDialogPergunta(Context context,String msg){

        AlertDialog.Builder build;
        AlertDialog alert;
        build = new AlertDialog.Builder(context);
        build.setTitle("ALERTA");
        build.setMessage(msg);
        build.setCancelable(false);
        build.setIcon(R.mipmap.ic_launcher);

        build.setPositiveButton("SIM", new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

AppUtil.toast(context, "sim");

            }
        });

        build.setNegativeButton("CANCELAR", new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppUtil.toast(context, "nao");
                dialog.cancel();
            }
        });

        alert = build.create();
        alert.show();



    }
}
