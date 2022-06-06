package app.modelo.meusclientes.datamodel;

public class ClienteDataModel {

    // Modelo Objeto Relacional

    // Toda Classe Data Model tem esta estrutura

    // 5 - Queries de consulta gerais

    // 1 - Atributo nome da tabela
    public static final String TABELA = "clientes";

    // 2 - Atributos um para um com os nomes dos campos

    public static final String ID = "id"; // integer
    public static final String NOME = "nome"; // text
    public static final String EMAIL = "email"; // text
    public static final String TEL = "telefone"; // text
    public static final String CEP = "cep"; // integer
    public static final String LOGRADOURO = "logradouro"; // text
    public static final String NUMERO = "numero"; // text
    public static final String BAIRRO = "bairro"; // text
    public static final String CIDADE = "cidade"; // text
    public static final String ESTADO = "estado"; // text
    public static final String TERMOS_DE_UDO = "termos_de_uso"; // integer
    public static final String DATA_CADASTRO = "data_cadastro"; // text

    // 3 - Query para criar a tabela no banco de dados
    public static String queryCriarTabela = "";

    // // 4 - Método para gerar o Script para criar a tabela;

    public static String criarTabela(){

        // Concatenação de String

        queryCriarTabela += "CREATE TABLE "+TABELA+" ( ";
        queryCriarTabela += ID+" integer primary key autoincrement , ";
        queryCriarTabela += NOME+" text , "; // nome text
        queryCriarTabela += EMAIL+" text , ";
        queryCriarTabela += TEL+" text , ";
        queryCriarTabela += CEP+" text , "; // nome text
        queryCriarTabela += LOGRADOURO+" text , "; // nome text
        queryCriarTabela += NUMERO+" text , "; // nome text
        queryCriarTabela += BAIRRO+" text , "; // nome text
        queryCriarTabela += CIDADE+" text , "; // nome text
        queryCriarTabela += ESTADO+" text , "; // nome text
        queryCriarTabela += TERMOS_DE_UDO+" integer , ";
        queryCriarTabela += DATA_CADASTRO+" text ";

        queryCriarTabela += " ); ";

        // queryCriarTabela = "Parte 01 Parte 02 Parte 03 Parte 04"

        return queryCriarTabela;
    }



}
