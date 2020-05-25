package br.com.projetoitau.contacorrente.utils;

public enum Status {

    ACTIVE(1, "ATIVO"),
    INATIVE(0, "INATIVO"),
    SUCCESS(1, "SUCESSO"),
    FAILED(0, "FALHA");

    private Integer code;
    private String message;

    Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
