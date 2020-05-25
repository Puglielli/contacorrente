package br.com.projetoitau.contacorrente.utils;

import java.io.Serializable;
import java.text.DecimalFormat;

public class ValidateCPFCNPJ implements Serializable {

    private String cpfCnpj;
    private boolean isPJ;

    public ValidateCPFCNPJ() {
        super();
    }

    public ValidateCPFCNPJ(String cpfCnpj) {
        super();
        setCpfCnpj(cpfCnpj);
        this.isPJ = (this.cpfCnpj != null && this.cpfCnpj.length() == 14);
    }

    public ValidateCPFCNPJ(String cpfCnpj, String isPJorPF) {
        super();
        if (isPJorPF.equals("PJ")) {
            this.isPJ = true;
        } else {
            this.isPJ = false;
        }
        setCpfCnpj(cpfCnpj);
    }

    public String getCpfCnpj() {
        if (cpfCnpj != null) {
            if (isPJ) {
                return cpfCnpj.replaceAll("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})", "$1\\.$2\\.$3/$4-$5");
            }
            return cpfCnpj.replaceAll("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})", "$1\\.$2\\.$3-$4");
        }
        return null;
    }

    public void setCpfCnpj(String cpfCnpj) {
        if (cpfCnpj != null) {
            this.cpfCnpj = cpfCnpj.replaceAll("[^0-9]*", "");
            this.cpfCnpj = zerosLeft(this.cpfCnpj, isPJ ? 14 : 11);
        } else {
            this.cpfCnpj = null;
        }
    }

    public String getNumber() {
        return cpfCnpj;
    }

    public boolean isValid() {

        boolean isCnpj = cpfCnpj.length() == 14;
        boolean isCpf = cpfCnpj.length() == 11;

        int i;
        int j;   // just count
        int digit;      // A number digit
        int coeficient; // A coeficient
        int sum;        // The sum of (Digit * Coeficient)
        int[] foundDv = {0, 0}; // The found Dv1 and Dv2
        int dv1 = Integer.parseInt(String.valueOf(cpfCnpj.charAt(cpfCnpj.length() - 2)));
        int dv2 = Integer.parseInt(String.valueOf(cpfCnpj.charAt(cpfCnpj.length() - 1)));
        for (j = 0; j < 2; j++) {
            sum = 0;
            coeficient = 2;
            for (i = cpfCnpj.length() - 3 + j; i >= 0; i--) {
                digit = Integer.parseInt(String.valueOf(cpfCnpj.charAt(i)));
                sum += digit * coeficient;
                coeficient++;
                if (coeficient > 9 && isCnpj) {
                    coeficient = 2;
                }
            }
            foundDv[j] = 11 - sum % 11;
            if (foundDv[j] >= 10) {
                foundDv[j] = 0;
            }
        }
        return dv1 == foundDv[0] && dv2 == foundDv[1];
    }

    private String zerosLeft(String valor, int tamanho) {

        while (tamanho > valor.length()) {
            valor = "0" + valor;
        }
        return valor;
    }

    public boolean isPJ() {
        return isPJ;
    }

    @Override
    public String toString() {
        return getCpfCnpj();
    }

    public static void main(String[] args) {

        DecimalFormat formatDec = new DecimalFormat("00000000000");

        ValidateCPFCNPJ cpf = new ValidateCPFCNPJ(formatDec.format(new Long("25566739822")));
        System.out.println("cpf numero = " + cpf.getNumber());
        System.out.println("cpf formatado = " + cpf.getCpfCnpj());
        System.out.println("toString = " + cpf.toString());
    }
}
