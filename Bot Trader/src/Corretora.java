import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Corretora extends Thread {

    private List<Stock> stocks = new ArrayList<Stock>();
    private int operações = 1000;
    private boolean isC1Free = true;
    private boolean isC2Free = true;
    private List<Caixa> caixaGeral = new ArrayList<Caixa>();

    public Corretora(Stock stock) {
        this.stocks.add(stock);
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void addStocks(Stock stock) {
        this.stocks.add(stock);
    }

    public boolean isC2Free() {
        return isC2Free;
    }

    public void setC2Free(boolean isC2Free) {
        this.isC2Free = isC2Free;
    }

    public boolean isC1Free() {
        return isC1Free;
    }

    public void setC1Free(boolean isC1Free) {
        this.isC1Free = isC1Free;
    }

    public void setOperações() {
        this.operações--;
    }

    public void Compra(Stock stock, Cliente cliente, int volume, Date hora) {
        if (isC1Free()) {
            setC1Free(false);
            setOperações();
            cliente.setSaldoCompra(volume * stock.getValor(hora));
            caixaGeral.add(
                    new Caixa(cliente.getNome(), stock.getStockSymbol(), "compra", "Caixa1", hora,
                            stock.getValor(hora)));
            setC1Free(true);
        } else if (isC2Free()) {
            setC2Free(false);
            setOperações();
            cliente.setSaldoCompra(volume * stock.getValor(hora));
            caixaGeral.add(
                    new Caixa(cliente.getNome(), stock.getStockSymbol(), "compra", "Caixa2", hora,
                            stock.getValor(hora)));
            setC2Free(true);
        }
    }

    public void Venda(Stock stock, Cliente cliente, int volume, Date hora) {
        if (isC1Free()) {
            setC1Free(false);
            setOperações();
            cliente.setSaldoVenda(volume * stock.getValor(hora));
            caixaGeral.add(
                    new Caixa(cliente.getNome(), stock.getStockSymbol(), "venda", "Caixa1", hora,
                            stock.getValor(hora)));
            setC1Free(true);
        } else if (isC2Free()) {
            setC2Free(false);
            setOperações();
            cliente.setSaldoCompra(volume * stock.getValor(hora));
            caixaGeral.add(
                    new Caixa(cliente.getNome(), stock.getStockSymbol(), "venda", "Caixa2", hora,
                            stock.getValor(hora)));
            setC2Free(true);
        }
    }

}
