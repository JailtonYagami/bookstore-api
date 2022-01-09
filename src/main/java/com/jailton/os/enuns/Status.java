package com.jailton.os.enuns;

public enum Status {
    ABERTO(0, "ABERTO"),
    ANDAMENTO(1, "ANDAMENTO"),
    ENCERRADO(2, "ENCERRADO");

    private Integer cod;
    private String descricao;

    Status(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Metodo static pra nao precisar estanciar uma nova prioridade para chamar o metodo
     *
     * @param cod
     * @return
     */
    public static Status toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Status x : Status.values()) {
            if (cod.equals((x.getCod()))) {
                return x;
            }
        }
        throw new IllegalArgumentException("Status inválida!!" + cod);
    }
}
