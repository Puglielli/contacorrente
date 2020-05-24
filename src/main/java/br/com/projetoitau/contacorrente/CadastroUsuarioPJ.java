package br.com.projetoitau.contacorrente;

public class CadastroUsuarioPJ extends  Banco{
    private String cnpj;
    private String razaoSocial;
    private String inscEstadual;
    private String endereco;
    public int numConta;

    public CadastroUsuarioPJ(String cnpj, String razaoSocial, String inscEstadual, String endereco, int numConta) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.inscEstadual = inscEstadual;
        this.endereco = endereco;
        this.numConta = numConta;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setRazaoSocial(String razaoSocial) {

        this.razaoSocial = razaoSocial;
    }

    public void setInscEstadual(String inscEstadual) {

        this.inscEstadual = inscEstadual;
    }

    public void setEndereco(String endereco) {

        this.endereco = endereco;
    }

    public void setNumConta(int numConta) {

        this.numConta = numConta;
    }

    public String getCnpj() {

        return cnpj;
    }

    public String getRazaoSocial() {

        return razaoSocial;
    }

    public String getInscEstadual() {
        return inscEstadual;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getNumConta() {

        return numConta;

    }

    public void ImprimirDados() {
        System.out.println("CNPJ: " + this.cnpj + "\n" + "Razão Social: " + this.razaoSocial + "\n" + "Inscrição Estadual: " + this.inscEstadual + "\n" + "Endereço: " + endereco + "\n" + "Número da Conta" + this.numConta + "\n");
    }
}