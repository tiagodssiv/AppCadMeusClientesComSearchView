package app.modelo.meusclientes.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.modelo.meusclientes.R;
import app.modelo.meusclientes.api.AppUtil;
import app.modelo.meusclientes.api.ClientesAdapter;
import app.modelo.meusclientes.api.Globals;
import app.modelo.meusclientes.controller.ClienteController;
import app.modelo.meusclientes.datamodel.ClienteDataModel;
import app.modelo.meusclientes.model.Cliente;


public class ListarClientesFragment extends Fragment {

    // Fragment - Classe responsável pela camada de VISÃO (Layout)
    View view;
    FragmentManager fragmentManager;
    List<Integer> idClienteServidor;
    EditText editPesquisarNome;
    String nome,telefone,email,bairro,datacadastro;
    ListView listView;
ClientesAdapter clientesAdapter;
    List<Cliente> clienteList;
   public static  List<Cliente> testeList;
int flag =0;
int id_cli=0;
    List<String> clientes;
    List <Integer> idClinte;

    ArrayAdapter<String> clienteAdapter;

    ArrayList<HashMap<String, String>> filtroClientes;

    ClienteController clienteController;
    AlertDialog.Builder builder;
    AlertDialog alert;
    Cliente objCliente;
    List<Cliente> teste = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Informa que este Fragment deseja adicionar itens de menu na ActionBar
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_listar_clientes, container, false);
        clientes = new ArrayList<>();
        idClinte = new ArrayList<>();
        idClienteServidor= new ArrayList<>();
        objCliente=new Cliente();
        setHasOptionsMenu(true);
       Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
      //  Menu menu = toolbar.getMenu();
      toolbar.setVisibility(View.VISIBLE);
        Cliente ob = new Cliente();



        toolbar.setNavigationIcon(R.drawable.ic_back); // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.toast(getContext(), " tete ");


            }
        });


        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
      //  ((AppCompatActivity) getActivity()).getSupportActionBar();
      //  ((YOUR_ACTIVITY) getActivity()).getDelegate().setSupportActionBar(toolbar);

        FloatingActionButton fab =   view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    Snackbar.make(view, "Action Button Clicado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/


                //refaz outra instancia de fragment
                FragmentManager frag = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=frag.beginTransaction();
                fragmentTransaction.replace(R.id.content_fragment, new CadastrarClientesFragment()).commit();


            }
        });


        TextView txtTitulo = view.findViewById(R.id.txtTitulo);

        txtTitulo.setText(R.string.listar_clientes_fragment);






        // Trocar a cor da propriedade texto (setTextColor)
      //  txtTitulo.setTextColor(ColorStateList.valueOf(Color.RED));

        clienteController = new ClienteController(getContext());

        listView = (ListView) view.findViewById(R.id.listView);

        editPesquisarNome = view.findViewById(R.id.editPesquisarNome);

        // clienteList = new ArrayList<>();
     //   clienteList = clienteController.listar();//uma lista recebe os dados retornados do método listar








        // TODO - Implementar regra de negócio na controladora da classe Cliente

    /*    for (Cliente obj: clienteList)/*clientesdList tem dados vindos do retorno do método listar*/ //{

           /* clientes.add(obj.getNome());//uma lista que recebe uma string nome vindo da lista clientesList
            idClinte.add(obj.getId());//uma lista que recebe uma int id vindo da lista clientesList
        }*/
      //clienteAdapter = new ArrayAdapter<String>(getContext(), R.layout.listar_cliente_item, R.id.txtItemLista, clientes);

      //  clienteAdapter.notifyDataSetChanged();

       // clientesAdapter=new ClientesAdapter(getContext(),clienteList);/*ADAPTER CUSTOMIZADO */


        /* esse adapter ,que vai povoar a listview é simples ,mas tem que crair um layoutzinho com um item .
Nesse adapter informa-se o contexto, nome do layout e do campo que vai ser exibido na ListView e a lista que contém os dados
que povoarão a ListView/*
IMportante: para atualizar a lista sempre que adicionar um dado ou remover ,devemos fazer assim;
  clienteAdapter.notifyDataSetChanged();

  para remover item é assim:lista.remove(i posicao);
  para adiconar item é assim:lista.add( adcicionar o tipo de dato que foi declarado ex:"bola");
 */

        if(AppUtil.isConnected(getContext())){


            final ProgressDialog progressDialog ;
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("Carregando lista , favor agrarde...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Ion.with(getContext()).load(AppUtil.URL_WEB_SERVICE+"listarCliente.php")
                    //incorpora progressDialog ao feedback
                    .as(new TypeToken<List<Cliente>>(){})
                    .setCallback(new FutureCallback<List<Cliente>>() {
                        @Override
                        public void onCompleted(Exception e, List<Cliente> listaCliente) {
                            try{


                                //sincronizar os bancos de dados é importante excluir a tabela e recriar e  repovoar com os do banco de dados do servidso
                                //porém ,é importante cadastrar os mesmos dados logo assim que cadastra no sqlite

                                //esse laço apaga os dados do sqlite

                                clienteController.deletarTabela(ClienteDataModel.TABELA);
                                clienteController.criarTabela(ClienteDataModel.criarTabela());
                                //laço abaixo insere todos os dados do banco do servidor no sqlite
                                for (Cliente obj: listaCliente)/*clientesdList tem dados vindos do retorno do método listar*/ {
                                    objCliente.setId(obj.getId());
                                    objCliente.setNome(obj.getNome());
                                    objCliente.setEmail(obj.getEmail());
                                    objCliente.setTelefone(obj.getTelefone());
                                    objCliente.setLogradouro(obj.getLogradouro());
                                    objCliente.setCep(obj.getCep());
                                    objCliente.setNumero(obj.getNumero());
                                    objCliente.setBairro(obj.getBairro());
                                    objCliente.setCidade(obj.getCidade());
                                    objCliente.setEstado(obj.getEstado());
                                    objCliente.setData_cadastro(obj.getData_cadastro());

                                    /*pega o modelo de dados de cliente e recebe os da lista que vem do servidor e
                                     * cadastra pelo informando o objeto de cliente */
                                    //é importante setar com o msmo objeto do tetodo get
                                    idClienteServidor.add(obj.getId());
                                    clienteController.incluir(objCliente);

                                }
                                //o laço abaixo
                                for(Cliente Objcliente:clienteController.listar()) {
                                  clienteList=clienteController.listar();
                                    clientes.add(Objcliente.getNome());//uma lista que recebe uma string nome vindo da lista clientesList
                                   idClinte.add(Objcliente.getId());//uma lista que recebe uma int id vindo da lista clientesList
                                }
                                clienteAdapter = new ArrayAdapter<String>(getContext(), R.layout.listar_cliente_item, R.id.txtItemLista, clientes);



                                progressDialog.dismiss();
                                listView.setAdapter(clienteAdapter/*clienteAdapter*//*clientesAdapter*/);
// listview

                                //aqui é um filtro de dados  da listview
                                editPesquisarNome.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence filtro, int start, int count, int after) {
//Ao screver no edittext  informa  mostra o item digitado , se tiver na lista
                                        ListarClientesFragment.this.clienteAdapter.getFilter().filter(filtro);



                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        ListarClientesFragment.this.clienteAdapter.getFilter().filter(s);
                                    }



                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });


                                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                                        builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("ALERTA");
                                        builder.setMessage("Deseja DELETAR este registro?");
                                        builder.setCancelable(true);
                                        builder.setIcon(R.mipmap.ic_launcher);

                                        builder.setPositiveButton("SIM", new Dialog.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                try{
                                                    clienteController.deletar(idClinte.get(i));
                                                    clienteController.deletarServidor(getContext(),idClienteServidor.get(i));
                                                  //  clientes.remove(i);
                                                   // clienteAdapter.notifyDataSetChanged();
                                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                                    fragmentTransaction.replace(R.id.content_fragment, new ListarClientesFragment() ,"NewFragmentTag");
                                                    fragmentTransaction.commit();
                                                }catch (Exception e){}


                                                //FragmentManager frag = getActivity().getSupportFragmentManager();
                                                //FragmentTransaction fragmentTransaction = frag.beginTransaction();
                                               // fragmentTransaction.replace(R.id.content_fragment, new ListarClientesFragment()).commit();

                                            }
                                        });

                                        builder.setNegativeButton("CANCELAR", new Dialog.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.cancel();
                                            }
                                        });

                                        alert = builder.create();
                                        alert.show();

                                        return true;


                                    }
                                });


                                //unico click abre tela editar

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {




                                        Globals sharedData = Globals.getInstance();

                                        sharedData.setValue(position);

//String n = sharedData.getValue();
                                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.content_fragment, new AlterarClientesFragment() ,"NewFragmentTag");
                                        fragmentTransaction.commit();
                                    }{




/*
                                       AlterarClientesFragment fragment = new AlterarClientesFragment();

                                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                        bundle.putString("ano", String.valueOf(idClinte.get(i)));
                                        fragment.setArguments(bundle);
                                        fragmentTransaction.replace(R.id.content_fragment, new AlterarClientesFragment() ,"NewFragmentTag");
                                        fragmentTransaction.commit();
*/


                                    }
                                });




                            }catch(Exception exception){

                                progressDialog.dismiss();

                            }


                        }
                    });



        }else{

            AppUtil.alertDialog(getContext(),"Precisa estar n/  conectado `a internet");

        }



       // listView.setAdapter(clienteAdapter/*clienteAdapter*//*clientesAdapter*/);



        return view;
    }




}
