public class Cliente {
    private Double saldo;
    private String nome;

    public String getNome() {
        return nome;
    }

    public Cliente(String nome, Double saldo) {
        this.nome = nome;
        this.saldo = saldo;
    }

    public void setSaldoVenda(double d) {
        this.saldo -= d;
    }

    public void setSaldoCompra(double d) {
        this.saldo += d;
    }
}
