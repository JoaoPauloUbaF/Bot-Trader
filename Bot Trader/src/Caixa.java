import java.util.Date;

public class Caixa {
    private String nomeCliente, simboloAtivo, operação, caixa;
    private Date dataHoraCompraVenda;
    private Double valor;

    public Caixa(String nomeCliente, String simboloAtivo, String operação, String caixa,
            Date dataHoraCompraVenda,
            Double valor) {
        this.nomeCliente = nomeCliente;
        this.simboloAtivo = simboloAtivo;
        this.operação = operação;
        this.dataHoraCompraVenda = dataHoraCompraVenda;
        this.valor = valor;
        this.caixa = caixa;
    }

    public Caixa() {

    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getSimboloAtivo() {
        return simboloAtivo;
    }

    public void setSimboloAtivo(String simboloAtivo) {
        this.simboloAtivo = simboloAtivo;
    }

    public String getOperação() {
        return operação;
    }

    public void setOperação(String operação) {
        this.operação = operação;
    }

    public String getCaixa() {
        return caixa;
    }

    public void setCaixa(String caixa) {
        this.caixa = caixa;
    }

    public Date getDataHoraCompraVenda() {
        return dataHoraCompraVenda;
    }

    public void setDataHoraCompraVenda(Date dataHoraCompraVenda) {
        this.dataHoraCompraVenda = dataHoraCompraVenda;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
