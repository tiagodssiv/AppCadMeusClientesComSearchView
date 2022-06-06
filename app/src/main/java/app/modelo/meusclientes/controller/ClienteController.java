package app.modelo.meusclientes.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import app.modelo.meusclientes.api.AppUtil;
import app.modelo.meusclientes.api.Globals;
import app.modelo.meusclientes.api.GlobalsListClinte;
import app.modelo.meusclientes.datamodel.ClienteDataModel;
import app.modelo.meusclientes.datasource.AppDataBase;
import app.modelo.meusclientes.model.Cliente;
import app.modelo.meusclientes.view.AlterarClientesFragment;


public class ClienteController extends AppDataBase implements ICrud<Cliente> {
    int valor=0;
    boolean delete_servidor = false;
    boolean alterar_servidor = false;
    ProgressDialog progressDialog;
    GlobalsListClinte sharedData = GlobalsListClinte.getInstance();
    String nome="";
    List<Cliente>  clientes = new ArrayList<>();
    ContentValues dadoDoObjeto;
    Cliente Obj = new Cliente();

    public ClienteController(Context context) {
        super(context);

        Log.d(AppUtil.TAG, "ClienteController: Conectado");
    }
    //pesquisa no servidor e devolve os dados numa lista


    public void alterarClienteServidor(Context context , Cliente cliente){
String alterar = "alterar";// manda para o arquivo no servidor informar ao php que a requisição é uma alteração no banco

    Ion.with(context).

            load(AppUtil.URL_WEB_SERVICE+"insertClient.php")
            .setBodyParameter("alterar",alterar)
            .setBodyParameter("id",String.valueOf(cliente.getId()))
           .setBodyParameter("nome",cliente.getNome())
            .setBodyParameter("email",cliente.getEmail())
            .setBodyParameter("telefone",cliente.getTelefone())
            .setBodyParameter("logradouro",cliente.getLogradouro())
            .setBodyParameter("cep",cliente.getCep())
            .setBodyParameter("numero",cliente.getNumero())
            .setBodyParameter("bairro",cliente.getBairro())
            .setBodyParameter("cidade",cliente.getCidade())
            .setBodyParameter("estado",cliente.getEstado())
            .setBodyParameter("datacadastro",cliente.getData_cadastro())






            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    String res =result.get("result").getAsString();
                 //  if(res.equals("OK")){
                      AppUtil.toast(context,"Dados Editados!"+result.get("result").getAsString());
                  //  }



                }
            });




}
    @Override
    public boolean incluir(Cliente obj ) {

        dadoDoObjeto = new ContentValues();
        // Key, Valor

        // dadoDoObjeto.put(ClienteDataModel.ID,obj.getId());
        // ID é chave primária da tabela cliente
        // é gerada automaticamente pelo SQLite a cada
        // novo registro adicionado
        // SQL ->>> INSERT INTO TABLE (... ... .. ) VALUES (### ### ###)
        dadoDoObjeto.put(ClienteDataModel.NOME,obj.getNome());
        dadoDoObjeto.put(ClienteDataModel.EMAIL,obj.getEmail());
        dadoDoObjeto.put(ClienteDataModel.TEL,obj.getTelefone());
        dadoDoObjeto.put(ClienteDataModel.CEP,obj.getCep());
        dadoDoObjeto.put(ClienteDataModel.LOGRADOURO,obj.getLogradouro());
        dadoDoObjeto.put(ClienteDataModel.NUMERO,obj.getNumero());
        dadoDoObjeto.put(ClienteDataModel.BAIRRO,obj.getBairro());
        dadoDoObjeto.put(ClienteDataModel.CIDADE,obj.getCidade());
        dadoDoObjeto.put(ClienteDataModel.ESTADO,obj.getEstado());
        dadoDoObjeto.put(ClienteDataModel.TERMOS_DE_UDO,obj.getTermos());
        dadoDoObjeto.put(ClienteDataModel.DATA_CADASTRO,obj.getData_cadastro());
        // Enviar os dados (dadoDoObjeto) para a classe AppDatabase
        // utilizando um método capaz de adicionar o OBJ no banco de
        // dados, tabela qualquer uma (Cliente)

        // Retorno sempre será FALSE ou VERDADEIROif()



        return insert(ClienteDataModel.TABELA, dadoDoObjeto);

    }


    //método cadastra no servidor web
    public void listarClientes(Context context){


        Ion.with(context).load(AppUtil.URL_WEB_SERVICE+"listarCliente.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                      for(int i=0; i < result.size();i++){

                            JsonObject obj =result.get(i).getAsJsonObject();
                            //

                            //   objCliente.setNome(obj.get("nome").getAsString());




                            Obj.setNome(obj.get("nome").getAsString());
                            Obj.setEmail(obj.get("email").getAsString());
                            Obj.setNumero(obj.get("numero").getAsString());
                            Obj.setTelefone(obj.get("telefone").getAsString());
                            Obj.setCidade(obj.get("cidade").getAsString());
                            Obj.setCep(obj.get("cep").getAsString());
                            Obj.setEstado(obj.get("estado").getAsString());
                            Obj.setData_cadastro(obj.get("datacadastro").getAsString());
                            Obj.setBairro(obj.get("bairro").getAsString());
                            Obj.setLogradouro(obj.get("logradouro").getAsString());
                            Obj.setId(Integer.parseInt(obj.get("id").getAsString()));
                            Obj.setTermos_de_uso(false);

                            incluir(Obj);



                        }

                    }
                });


//fim json

    }


    public int cadastrarServidor(Context context , Cliente cliente){

        Ion.with(context).

                load(AppUtil.URL_WEB_SERVICE+"insertClient.php")
                .setBodyParameter("nome",cliente.getNome())
                .setBodyParameter("email",cliente.getEmail())
                .setBodyParameter("telefone",cliente.getTelefone())
                .setBodyParameter("logradouro",cliente.getLogradouro())
                .setBodyParameter("cep",cliente.getCep())
                .setBodyParameter("numero",cliente.getNumero())
                .setBodyParameter("bairro",cliente.getBairro())
                .setBodyParameter("cidade",cliente.getCidade())
                .setBodyParameter("estado",cliente.getEstado())
                .setBodyParameter("datacadastro",cliente.getData_cadastro())






                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                     String res =result.get("result").getAsString();
                     int id = Integer.parseInt(result.get("ID").getAsString());
                     if(res.equals("OK")){
                     valor=id;
                     }



                    }
                });
        return valor;//retorna id do cliente cadastrado
    }
    public boolean deletarServidor(Context context,int idCliente){
        progressDialog=new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();


try{
    Ion.with(context).

            load(AppUtil.URL_WEB_SERVICE+"APISincronizarSistema.php")
            .setBodyParameter("id",String.valueOf(idCliente))

            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    String res =result.get("result").getAsString();

                    if(res.equals("OK")){
                        delete_servidor=true;

                    }

                }
            });

    progressDialog.dismiss();
}catch(Exception e){

    progressDialog.dismiss();



}

        return delete_servidor;
    }

    @Override
    public boolean deletar(int id) {
        return deleteByID(ClienteDataModel.TABELA,id);

    }

    @Override
    public boolean alterar(Cliente obj) {

        dadoDoObjeto = new ContentValues();
        // Key, Valor

        // ID é chave primária da tabela cliente
        // é gerada automaticamente pelo SQLite a cada
        // novo registro adicionado
        // Alterar
        // SQL ->>> UPDATE
        dadoDoObjeto.put(ClienteDataModel.ID,obj.getId());
        dadoDoObjeto.put(ClienteDataModel.NOME,obj.getNome());
        dadoDoObjeto.put(ClienteDataModel.EMAIL,obj.getEmail());
        dadoDoObjeto.put(ClienteDataModel.TEL,obj.getTelefone());
        dadoDoObjeto.put(ClienteDataModel.CEP,obj.getCep());
        dadoDoObjeto.put(ClienteDataModel.LOGRADOURO,obj.getLogradouro());
        dadoDoObjeto.put(ClienteDataModel.NUMERO,obj.getNumero());
        dadoDoObjeto.put(ClienteDataModel.BAIRRO,obj.getBairro());
        dadoDoObjeto.put(ClienteDataModel.CIDADE,obj.getCidade());
        dadoDoObjeto.put(ClienteDataModel.ESTADO,obj.getEstado());
        dadoDoObjeto.put(ClienteDataModel.TERMOS_DE_UDO,obj.isTermos_de_uso());
        dadoDoObjeto.put(ClienteDataModel.DATA_CADASTRO,obj.getData_cadastro());



        // Enviar os dados (dadoDoObjeto) para a classe AppDatabase
        // utilizando um método capaz de alterar o OBJ no banco de
        // dados, tabela qualquer uma (Cliente), respeitando o ID
        // ou PK (Primary Key)

        return update(ClienteDataModel.TABELA,dadoDoObjeto);

    }

    @Override
    public List<Cliente> listar() {

        return getAllClientes(ClienteDataModel.TABELA);

    }

}
