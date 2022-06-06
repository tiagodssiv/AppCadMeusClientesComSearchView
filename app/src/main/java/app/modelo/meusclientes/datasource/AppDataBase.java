package app.modelo.meusclientes.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import app.modelo.meusclientes.api.AppUtil;
import app.modelo.meusclientes.datamodel.ClienteDataModel;
import app.modelo.meusclientes.model.Cliente;

public class AppDataBase extends SQLiteOpenHelper {

    public static final String DB_NAME = "MeusClientes.sqlite";
    public static final int DB_VERSION = 1;

    SQLiteDatabase db;

    public AppDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        Log.d(AppUtil.TAG, "AppDataBase: Criando Banco de Dados");

        db = getWritableDatabase();


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ClienteDataModel.criarTabela());

        Log.d(AppUtil.TAG, "onCreate: Tabela Cliente criada... "+ClienteDataModel.criarTabela());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Método para incluir dados no banco de dados
     * @return
     */
    public boolean insert(String tabela, ContentValues dados){

        db = getWritableDatabase();

        boolean retorno = false;

        // Regra de negócio

        try {
            // O que deve ser realizado?
            // Salvar os dados

            retorno = db.insert(tabela,null,dados) > 0;




        }catch (Exception e){


            Log.d(AppUtil.TAG, "insert: "+e.getMessage());


        }


        return retorno; // FALSE ou TRUE
    }

    /**
     * Método para deletar dados no banco de dados
     * @return
     */
    public boolean deleteByID(String tabela, int id){

        db = getWritableDatabase();

        boolean retorno = false;

        // Regra de negócio

        try {
            // O que deve ser realizado?
            // Salvar os dados

           // retorno = db.insert(tabela,null,dados) > 0;
            retorno = db.delete(tabela,"id = ?",new String[] {String.valueOf(id)}) > 0;


        }catch (Exception e){


            Log.d(AppUtil.TAG, "delete: "+e.getMessage());


        }


        return retorno; // FALSE ou TRUE
    }

    /**
     * Método para alterar dados no banco de dados
     * @return
     */
    public boolean update(String tabela, ContentValues dados){

        db = getWritableDatabase();

        boolean retorno = false;

        // Regra de negócio

        try {
            // O que deve ser realizado?
            // Salvar os dados

            //
            //
            //  retorno = db.insert(tabela,null,dados) > 0;
            //  retorno = db.delete(tabela,                  "id = ?",new String[] {String.valueOf(id)}) > 0;
                retorno = db.update(tabela,dados,"id = ?",new String[] {String.valueOf(dados.get("id"))}) > 0;


        }catch (Exception e){


            Log.d(AppUtil.TAG, "update: "+e.getMessage());


        }

        return retorno; // FALSE ou TRUE
    }


    public List<Cliente> getAllClientes(String tabela) {

        db = getWritableDatabase();

        List<Cliente> clientes = new ArrayList<>();
        Cliente obj;

        String sql = "SELECT * FROM " + tabela+" ORDER BY id ";

        Cursor cursor;

        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){

            do{
                obj = new Cliente();

                obj.setId(cursor.getInt(cursor.getColumnIndex(ClienteDataModel.ID)));
                obj.setNome(cursor.getString(cursor.getColumnIndex(ClienteDataModel.NOME)));
                obj.setEmail(cursor.getString(cursor.getColumnIndex(ClienteDataModel.EMAIL)));
                obj.setData_cadastro(cursor.getString(cursor.getColumnIndex(ClienteDataModel.DATA_CADASTRO)));

                clientes.add(obj);



            }while (cursor.moveToNext());

        }


        return clientes;

    }

    //MÉDOTO A SEGUIR DELETA A TABELA DO BANCO SQLITE

    public void deletarTabela(String tabela){
        SQLiteDatabase db = getWritableDatabase();
try{
    String sql = "DROP TABLE IF EXISTS "+tabela+";";

    db.execSQL(sql);
    Log.i("excluir tabela","exclusao ok");

}catch(Exception e){
    Log.i("excluir tabela","exclusao não ok");

}

    }
    //fim metodo deletar tabela

// método cria a tabela no sqlite
    public void criarTabela(String queryCriarTabela){

        try{
            db.execSQL(queryCriarTabela);


        }catch(SQLiteCantOpenDatabaseException e){
            Log.e("CriarTabela","Erro"+e.getMessage());


        }

    }




}
