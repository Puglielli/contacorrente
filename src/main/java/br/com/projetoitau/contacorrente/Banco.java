package br.com.projetoitau.contacorrente;

public class Banco {
    String banco;
    int numConta;
    int agencia;

    public Banco(String banco, int numConta, int agencia){
        this.banco = banco;
        this.numConta = numConta;
        this.agencia = agencia;

    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public void setNumConta(int numConta) {
        this.numConta = numConta;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }
     public String getBanco(){
        return banco;
     }

    public int getNumConta() {
        return numConta;
    }

    public int getAgencia() {
        return agencia;
    }
    public void imprimeDados() {
        System.out.println("Nome do Banco: "+ this.getBanco() + "\n"+ "Numero Conta: " +this.getNumConta() + "\n"+"Número da Agência: " + this.getAgencia());
        banco.imprimeDados();
}