package app.modelo.meusclientes.controller;

import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by marcomaddo on 23/02/2018.
 */

public class AplicativoController {

/*este método é importante para que o aplicativo saiba se o dispositivo do usuario existe
* os componentes o pacote da google ,que vai ser usado no aplicaativo.Se nao tiver instalado, oaplicativo não funciona e informa
* ao usuario para instalar
*
* Mas antes ,deve-se instalar a bibiloteca da google no aplicativo.Ex: se for usar o mapa da google , instale a biblioteca
* de mapa da ggole,qualquer biblioteca já tras as informações avaliação de disponiobilidade do pacote
* da google no celular do usuário
*
* Para ativar o médoto abaixo , basta or na tela desejava passar o activity no método.cOMO O MÉTODO RETRONA true ou false, deixe-o dentro
* de um if.Se caso for verdadeiro ,abra o palicativo, so for falso ,não inicialize o app
*
*
* */
    public static boolean verificarGooglePlayServices(Activity activity){

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int status=googleApiAvailability.isGooglePlayServicesAvailable(activity);
if(status != ConnectionResult.SUCCESS){
if(googleApiAvailability.isUserResolvableError(status)){


    googleApiAvailability.getErrorDialog(activity,status,2404).show();
}

return false;
}




        return true;
    }
}
