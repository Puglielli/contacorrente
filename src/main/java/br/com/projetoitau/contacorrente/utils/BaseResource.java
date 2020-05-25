package br.com.projetoitau.contacorrente.utils;

import java.util.Random;

public class BaseResource {

    public static String generateNewAccount() {
        return String.valueOf(new Random().nextInt(999999999 - 111111111) + 1 + 111111111).replaceAll("/\\D/g", "");
    }

    public static String generateNewAgency() {
        return String.valueOf(new Random().nextInt(9999 - 1111) + 1 + 1111);
    }

    public static String formatAccount(String num_conta) {
        num_conta = num_conta.replaceAll("/\\D/g", "");

        return  num_conta.replaceAll("([0-9]{8})([0-9]{1})", "$1-$2");
    }

    public static Integer getDac(String agency) {
        return Integer.parseInt(agency.replaceAll("([0-9]{3})([0-9]{1})", "$2"));
    }
}
