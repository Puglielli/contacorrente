package br.com.projetoitau.contacorrente;

public class CadastroUsuarioPF {
    private String cpf;
    private String nome;
    private String endereco;
    private String profissao;
    private int numConta;

    public CadastroUsuarioPF(String cpf, String nome, String endereco, String profissao, int numConta){
        this.cpf = null;
        this.nome = null;
        this.endereco = null;
        this.profissao = null;
        this.numConta = 0;

    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public void setNumConta(int numConta) {
        this.numConta = numConta;
    }
    public String getCpf(){
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getProfissao() {
        return profissao;
    }

    public int getNumConta() {
        return numConta;
    }
    public void ImprimirDados() {
        System.out.println("CPF: " + this.cpf + "\n" + "Nome: " + this.nome + "\n" +"Profissão: " +this.profissao+"\n" + "Endereço: " + endereco + "\n" + "Número da Conta" + this.numConta + "\n");
    }
}
