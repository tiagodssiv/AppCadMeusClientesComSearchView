package app.modelo.meusclientes.api;

import java.util.ArrayList;
import java.util.List;

import app.modelo.meusclientes.model.Cliente;

public class GlobalsListClinte {


    List<Cliente> listTeste=new ArrayList<>();

    Cliente car = new Cliente();
    private static GlobalsListClinte  instance = new GlobalsListClinte ();

    // Getter-Setters
    public static GlobalsListClinte getInstance() {
        return instance;
    }

    public static void setInstance(GlobalsListClinte  instance) {
        GlobalsListClinte.instance = instance;
    }

    private int notification_index=0;


    public GlobalsListClinte () {

    }


    public /*int*/List<Cliente> getValue() {
        //  return  notification_index;
        return listTeste;
    }


    public void setValue(List<Cliente>list) {

        //int id_carrinho,int id_banco,int qnt,int image,String titulo,Double preco,Double preco_itens
        // this.notification_index = notification_index;
        this.listTeste=list;
    }




}
/*MEIO PARA ACESSAR ESTA CLASSE :
Globals sharedData = Globals.getInstance();
SETAR DADOS NESTA CLASSE
sharedData.setValue("kundan");
RECUPERAR DADOS
String n = sharedData.getValue();
*
*/