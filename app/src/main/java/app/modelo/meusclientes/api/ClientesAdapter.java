package app.modelo.meusclientes.api;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.modelo.meusclientes.R;
import app.modelo.meusclientes.controller.ClienteController;
import app.modelo.meusclientes.model.Cliente;

public class ClientesAdapter extends ArrayAdapter<Cliente> {
private List<Cliente> clientes =null;
private Context context;
Button btnApagar,btnEditar,btnVisualizar;
ClienteController clienteController;
    AlertDialog.Builder builder;
    AlertDialog alert;

    public ClientesAdapter(Context context, List<Cliente>cliente ){

        super(context, 0, cliente);
        this.clientes=cliente;
        this.context=context;
    }
public void atualizarLista(ArrayList<Cliente>lista){

      this.clientes.clear();
        this.clientes.addAll(lista);
        notifyDataSetChanged();

}

    @Override
    public View getView(int position,
                        View convertView, ViewGroup parent) {

        Cliente p = clientes.get(position);//põe no modelo os dados da linha correspondente a position


       if(convertView==null){
           convertView= LayoutInflater.from(context).inflate(R.layout.listar_cliente_item,null);

                   TextView tex1 = (TextView) convertView.findViewById(R.id.txtItemLista);
           tex1.setText(p.getNome());
          // TextView tex2 = (TextView) convertView.findViewById(R.id.data);
         //  tex2.setText(p.getData_cadastro());
          /* btnApagar=(Button)convertView.findViewById(R.id.btnApagar);
           btnEditar=(Button)convertView.findViewById(R.id.btnEditar);
           btnVisualizar=(Button)convertView.findViewById(R.id.btnVisualizar);
           btnApagar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {


                   builder = new AlertDialog.Builder(getContext());
                   builder.setTitle("ALERTA");
                   builder.setMessage("Deseja DELETAR este registro?");
                   builder.setCancelable(true);
                   builder.setIcon(R.mipmap.ic_launcher);

                   builder.setPositiveButton("SIM", new Dialog.OnClickListener() {

                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           // Deletar o registro

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





               }
           });
*/




       }
       return convertView;

    }
}
