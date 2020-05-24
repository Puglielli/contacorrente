package br.com.projetoitau.contacorrente;

public class DadosDaConta extends  Banco{
        protected String cliente;
        protected int numConta;
        protected String agencia;
        protected int dac;
        protected double saldoConta;
        protected String tipoDeTransacao;

       private String abrirConta;
       private String cadastrarCliente;
        private boolean status;
    public Object CadastroUsuarioPF;
    public Object CadastroUsuarioPJ;

    public void CadastrarCliente(){
            if (status == false){
                System.out.println ("Não existe esse cadastro " + cadastrarCliente);
            }
    }
}

