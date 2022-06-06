package app.modelo.meusclientes.view;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

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

import app.modelo.meusclientes.R;
import app.modelo.meusclientes.api.AppUtil;
import app.modelo.meusclientes.controller.AplicativoController;
import app.modelo.meusclientes.controller.ClienteController;
import app.modelo.meusclientes.datamodel.ClienteDataModel;
import app.modelo.meusclientes.model.Cliente;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Gerenciamento dos fragmentos
    FragmentManager fragmentManager;

    // Gerenciamento do menu drawer
    NavigationView navigationView;

    // Gerenciamento do menu action bar
    Menu menu;

    // Gerenciamento de cada item do menu drawer
    MenuItem nav_preto;
    MenuItem nav_vermelho;
    MenuItem nav_azul;
    MenuItem cad_client;
    MenuItem  list_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//SincronizarSistema sincronizarSistema = new SincronizarSistema();
       // sincronizarSistema.execute();
/*
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.getMenu();
        setSupportActionBar(toolbar);



      /*

       //utilização da toolbar
       Toolbar actionBarToolBar = (Toolbar) findViewById(R.id.my_toobar);

setSupportActionBar(actionBarToolBar);
actionBarToolBar.setNavigationIcon(R.drawable.icon);
actionBarToolBar.setNavigationContextDescription(getResources().getString(R.string.desc);
actionBarToolBar.setLogo(R.drawable.other_icon);
actionBarToolBar.setLogoDescription(getResources().getString(R.string.other_desc);
actionBarToolBar.inflateMenu(R.menu.fragment_menu);

// manipular os itens

toolbar.setOnMenuItemClickListener {
        when (it.itemId) {
                R.id.action_add -> {
                    // do something
                    true
                }
                R.id.action_update -> {
                    // do something
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
}





*/



        // drawer_Layout é o layout padrão do aplicativo
     /*   DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

        // nav_view contém o layout do menu
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        // content_fragment usado para receber os layouts dos fragmentos
        Boolean aplicativo =AplicativoController.verificarGooglePlayServices(this);
        if(aplicativo==true){
            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ListarClientesFragment()).commit();


        }else{

           AppUtil.toast(this, "Seu dispositivo não tem suporte!");

        }


        ClienteController clienteController = new ClienteController(getBaseContext());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // TODO: opter ID para a opção selecionada no MENU DRAWER
        if (id == R.id.nav_preto) {

            menu = navigationView.getMenu();

            nav_preto = menu.findItem(R.id.nav_preto);
            nav_preto.setTitle("Preto Ativo");

            nav_vermelho = menu.findItem(R.id.nav_vermelho);
            nav_vermelho.setTitle("Vermelho");

            nav_azul = menu.findItem(R.id.nav_azul);
            nav_azul.setTitle("Azul");

            navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ModeloPretoFragment()).commit();

        } else if (id == R.id.nav_vermelho) {

            menu = navigationView.getMenu();

            nav_preto = menu.findItem(R.id.nav_preto);

            nav_preto.setTitle("Preto");

            nav_vermelho = menu.findItem(R.id.nav_vermelho);
            nav_vermelho.setTitle("Vermelho Ativado");

            nav_azul = menu.findItem(R.id.nav_azul);
            nav_azul.setTitle("Azul");

            // TODO: Mudar a cor de todos os itens do menu programaticamente
            navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ModeloVermelhoFragment()).commit();

        } else if (id == R.id.nav_azul) {

            menu = navigationView.getMenu();

            nav_preto = menu.findItem(R.id.nav_preto);
            nav_preto.setTitle("Preto");

            nav_vermelho = menu.findItem(R.id.nav_vermelho);
            nav_vermelho.setTitle("Vermelho");

            nav_azul = menu.findItem(R.id.nav_azul);
            nav_azul.setTitle("Azul Ativado");

            navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ModeloAzulFragment()).commit();

        }

        else if (id == R.id.cad_client) {

            menu = navigationView.getMenu();

            cad_client = menu.findItem(R.id.nav_azul);
            cad_client.setTitle(R.string.cad_clientes);

          navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new CadastrarClientesFragment()).commit();

        }
        else if (id == R.id.list_client) {

            menu = navigationView.getMenu();

            list_client = menu.findItem(R.id.nav_azul);
            list_client.setTitle(R.string.list_clientes);

            navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ListarClientesFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//classe ansinc




    private class SincronizarSistema extends AsyncTask<String,String,String> {
        ClienteController clienteController;

        URL url=null;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        Uri.Builder builder;




        public SincronizarSistema(){

            this.builder = new Uri.Builder();
            builder.appendQueryParameter("app","clientes");

        }


        @Override
        protected String doInBackground(String... strings) {
            try{
                url=new URL(AppUtil.URL_WEB_SERVICE);
                Log.e("servidor","linkServidor"+url);

            }catch(Exception error){

                Log.e("servidor","linkServidor"+error.getMessage()+url);

            }

            try{
                conn= (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(AppUtil.CONNECTION_TIMEOUT);
                conn.setReadTimeout(AppUtil.READ_TIME_OUT);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("charset","utf-8");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                String query = builder.build().getEncodedQuery();
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();



            }catch (IOException e){
                Log.e("servidor","IOException"+e.getMessage());
            }

            //recebe a resposta do servidor

            try{
                int response_code = conn.getResponseCode();
                if (response_code==HttpURLConnection.HTTP_OK){
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){

                        result.append(line);
                    }
                    return (result.toString());

                }else{ return("Erro de Conexão");}
            }catch (IOException e){

                Log.e("servidor","IOException"+e.getMessage());
                return e.toString();
            }finally {
                conn.disconnect();
            }





        }

        @Override
        protected void onPreExecute(){

            progressDialog.setMessage("Sincronizando, favor agrarde...");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }





        @Override
        protected void onPostExecute(String result)
        {

            try {

                JSONArray jArray = new JSONArray(result);

                if (jArray.length() != 0) {

                    // Todo: Criar método para deletar tabela na datasource
                    clienteController.deletarTabela(ClienteDataModel.TABELA);
                    clienteController.criarTabela(ClienteDataModel.criarTabela());
                    // ClienteController.deletarTabela(MediaEscolarDataModel.getTABELA());
                    //  controller.criarTabela(MediaEscolarDataModel.criarTabela());



                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject jsonObject = jArray.getJSONObject(i);

                        Cliente obj = new Cliente();

                        obj.setId(jsonObject.getInt("id"));//nome de cada item quem vem da lista do php.é o mesmo do banco
                        obj.setData_cadastro(jsonObject.getString("datacadastro"));
                        obj.setEmail(jsonObject.getString("email"));
                        obj.setTelefone(jsonObject.getString("telefone"));
                        obj.setNome(jsonObject.getString("nome"));
                        obj.setBairro(jsonObject.getString("bairro"));
                        obj.setEstado(jsonObject.getString("estado"));
                        obj.setLogradouro(jsonObject.getString("logradouro"));
                        obj.setCep(jsonObject.getString("cep"));
                        obj.setNumero(jsonObject.getString("numero"));
                        obj.setCidade(jsonObject.getString("cidade"));
                        // obj.setTermosDeUso(jsonObject.getBoolean("termos_de_uso"));
                       // clienteController.incluir(obj);

                    }

                } else {

                    AppUtil.toast(MainActivity.this,"Nenhum registro encontrado no momento...");

                }

            } catch ( JSONException e) {

                Log.e("WebService", "Erro JSONException " + e.getMessage());

            } finally {

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

        }


    }






    //fim



}
