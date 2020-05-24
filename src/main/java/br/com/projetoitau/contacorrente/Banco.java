package br.com.projetoitau.contacorrente;


public class Banco {
    private String banco;
    private int numConta;
    private int agencia;

    public Banco() {
        String banco;
        int numConta;
        int agencia;
    }

        public void imprimeDados (String banco, int numConta, int agencia) {
            System.out.println("Nome do Banco: " + this.banco + "\n" + "Numero Conta: " + numConta + "\n" + "Número da Agência: " + this.agencia);

        }
    }

