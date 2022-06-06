package app.modelo.meusclientes.api;

import java.util.ArrayList;
import java.util.List;

import app.modelo.meusclientes.model.Cliente;

public class Globals {


//List<Carrinho>listTeste=new ArrayList<>();

Cliente cliente = new Cliente();
    private static Globals instance = new Globals();
    private List<Cliente>listaCliente= new ArrayList<>();
    private int posicao=0;
    // Getter-Setters
    public static Globals getInstance() {
        return instance;
    }

    public static void setInstance(Globals instance) {
        Globals.instance = instance;
    }

    private int notification_index=0;


    public Globals() {

    }


    public int getValue() {
      //  return  notification_index;
        return posicao;
    }


    public void setValue(int positin) {

        //int id_carrinho,int id_banco,int qnt,int image,String titulo,Double preco,Double preco_itens
       // this.notification_index = notification_index;

        this.posicao=positin;
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