package app.modelo.meusclientes.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.modelo.meusclientes.R;
import app.modelo.meusclientes.api.AppUtil;
import app.modelo.meusclientes.controller.ClienteController;
import app.modelo.meusclientes.datamodel.ClienteDataModel;
import app.modelo.meusclientes.model.Cliente;


public class CadastrarClientesFragment extends Fragment {

    // Fragment - Classe responsável pela camada de VISÃO (Layout)
    // Fragment - Classe responsável pela camada de VISÃO (Layout)
    // Declaração GLOBAL de atributos - variáveis globais
    View view;

    TextView txtTitulo;
    FragmentManager fragmentManager;
    EditText editNomeCompleto;
    EditText editTelefone;
    EditText editEmail;
    EditText editCep;
    EditText editLogradouro;
    EditText editNumero, editBairro, editCidade, editEstado;

    CheckBox chkTermosDeUso;
    Button btnCancelar, btnSalvar;

    Cliente novoCliente;
    ClienteController clienteController;

    public CadastrarClientesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.cadastrarclientes, container, false);

        iniciarComponentesDeLayout();

        escutarEventosDosBotoes();

        return view;
    }
    private void escutarEventosDosBotoes() {

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(AppUtil.isConnected(getContext())) {
                    //VERIFICA CONEXAO
                    // Considerando que o usuário
                    // digitou todos os dados
                    boolean isDadosOK = true;

                    // False verdadeiro
                    if (TextUtils.isEmpty(editNomeCompleto.getText())) {
                        isDadosOK = false;
                        editNomeCompleto.setError("Digite o nome completo...");
                        editNomeCompleto.requestFocus();
                    }

                    if (TextUtils.isEmpty(editTelefone.getText())) {
                        isDadosOK = false;
                        editTelefone.setError("Digite o telefone...");
                        editTelefone.requestFocus();
                    }

                    if (TextUtils.isEmpty(editEmail.getText())) {
                        isDadosOK = false;
                        editEmail.setError("Digite o email...");
                        editEmail.requestFocus();
                    }

                    if (TextUtils.isEmpty(editCep.getText())) {
                        isDadosOK = false;
                        editCep.setError("Digite o cep...");
                        editCep.requestFocus();
                    }

                    if (TextUtils.isEmpty(editLogradouro.getText())) {
                        isDadosOK = false;
                        editLogradouro.setError("Digite o logradouro (av, rua...)");
                        editLogradouro.requestFocus();
                    }

                    if (TextUtils.isEmpty(editNumero.getText())) {
                        isDadosOK = false;
                        editNumero.setError("Digite o número...");
                        editNumero.requestFocus();
                    }

                    if (TextUtils.isEmpty(editBairro.getText())) {
                        isDadosOK = false;
                        editBairro.setError("Digite o bairro...");
                        editBairro.requestFocus();
                    }

                    if (TextUtils.isEmpty(editCidade.getText())) {
                        isDadosOK = false;
                        editCidade.setError("Digite a cidade...");
                        editCidade.requestFocus();
                    }

                    if (TextUtils.isEmpty(editEstado.getText())) {
                        isDadosOK = false;
                        editEstado.setError("Digite o estado (UF)");
                        editEstado.requestFocus();
                    }


                    if (isDadosOK) {

                        // popular os dados no objeto cliente.

                        novoCliente.setNome(editNomeCompleto.getText().toString());
                        novoCliente.setTelefone(editTelefone.getText().toString());
                        novoCliente.setEmail(editEmail.getText().toString());
                        // Cast
                        novoCliente.setCep(editCep.getText().toString());

                        novoCliente.setLogradouro(editLogradouro.getText().toString());
                        novoCliente.setNumero(editNumero.getText().toString());
                        novoCliente.setBairro(editBairro.getText().toString());
                        novoCliente.setCidade(editCidade.getText().toString());
                        novoCliente.setEstado(editEstado.getText().toString());

                        boolean check = novoCliente.isTermos_de_uso();
                        // passar data e hora atual para string
                        Date dataAtual = new Date();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String dataFormatada = dateFormat.format(dataAtual);

                        novoCliente.setData_cadastro(dataFormatada);
                        //-----'
                        if (check) {
                            novoCliente.setTermos(1);
                        } else {
                            novoCliente.setTermos(0);
                        }
                        if (clienteController.incluir(novoCliente) == true) {
                            clienteController.cadastrarServidor(getContext(), novoCliente);




                            FragmentManager frag = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = frag.beginTransaction();
                            fragmentTransaction.replace(R.id.content_fragment, new ListarClientesFragment()).commit();


                            // clienteController.criarTabela(ClienteDataModel.criarTabelaSqlite());

                            //AppUtil.toast(getContext(), "Cadastrado com sucesso!");
                            // AppUtil.toast(getContext(), "id"+clienteController.cadastrarServidor(getContext(),novoCliente));


                        } else {
                            AppUtil.toast(getContext(), "NÃO CADASTRADO!");
                        }


                    } else {
                        // Notificar o usuário
                        // TOAST+"nome "+novoCliente.getNome()
                        // Push Notification
                        // AlertDialog
                        AppUtil.toast(getContext(), "Preencha os campos!");

                        Log.e("log_add_cliente", "onClick: Dados incorreto....");
                    }

                }else{

                    AppUtil.alertDialog(getContext(),"Não está conectado à internet!");


                }
//FIM VERIFICAÇÃO CONEXAO INTERNET
            }








        });
    }

    private void iniciarComponentesDeLayout() {

        txtTitulo = view.findViewById(R.id.txtTitulo);
        txtTitulo.setText(R.string.novoCliente);

        editNomeCompleto = view.findViewById(R.id.editNomeCompleto);
        editTelefone = view.findViewById(R.id.editTelefone);
        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(editTelefone, smf);
        editTelefone.addTextChangedListener(mtw);


        editEmail = view.findViewById(R.id.editEmail);
        editCep = view.findViewById(R.id.editCep);
        SimpleMaskFormatter cep = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher Cep= new MaskTextWatcher(editCep, cep);
        editCep.addTextChangedListener(Cep);

        editLogradouro = view.findViewById(R.id.editLogradouro);
        editNumero = view.findViewById(R.id.editNumero);
        editBairro = view.findViewById(R.id.editBairro);
        editCidade = view.findViewById(R.id.editCidade);
        editEstado = view.findViewById(R.id.editEstado);

        chkTermosDeUso = view.findViewById(R.id.chkTermosDeUso);

        btnCancelar = view.findViewById(R.id.btnCancelar);
        btnSalvar = view.findViewById(R.id.btnSalvar);

        novoCliente = new Cliente();

        clienteController = new ClienteController(getContext());


    }


}
