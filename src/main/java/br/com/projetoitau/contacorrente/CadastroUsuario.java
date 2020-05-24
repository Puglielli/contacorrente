package br.com.projetoitau.contacorrente;

public class CadastroUsuario {
    private String cpf;
    private String cnpj;
    private String nome;
    private char tipoDeCliente;
    private String endereco;
    private String profissao;
    private String razaoSocial;
    private String inscEstadual;
    private String numConta;

    public void CadastroUsuario() {

        this.tipoDeCliente= ' ';
        this.cpf = null;
        this.cnpj = null;
        this.nome = null;
        this.razaoSocial = null;
        this.endereco = null;
        this.profissao = null;
        this.numConta = null;
        this.inscEstadual= null;

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getTipoDeCliente() {
        return tipoDeCliente;
    }

    public void setTipoDeCliente(char tipoDeCliente) {
        this.tipoDeCliente = tipoDeCliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getInscEstadual() {
        return inscEstadual;
    }

    public void setInscEstadual(String inscEstadual) {
        this.inscEstadual = inscEstadual;
    }

    public String getNumConta() {
        return numConta;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }

    public CadastroUsuario() {
        if (tipoDeCliente == 'F') {
            System.out.printf("CPF: " + cpf + "\n" + "Nome: " + nome + "\n" + "Endereço: " + endereco + "\n" + "Profissão:" + profissao + "Número da Conta: " + numConta);
        } else {
            System.out.printf("CNPJ: " + cnpj + "\n" + "Razão Social: " + razaoSocial + "\n" + "Inscrição Estadual: " + inscEstadual + "\n"+"Número da conta: " + numConta);
        }

    }
    }