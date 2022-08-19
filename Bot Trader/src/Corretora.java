import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Corretora extends Thread {

    private int i = 0;
    private List<Stock> stocks = new ArrayList<Stock>();
    private int operações = 1000;
    private boolean isC1Free = true;
    private boolean isC2Free = true;
    private List<Caixa> caixaGeral = new ArrayList<Caixa>();
    private ScheduledExecutorService executor;
    private List<Cliente> clientes = new ArrayList<Cliente>();
    private boolean fila = false;

    public Corretora(String stockSymbol, String filepath) {
        this.stocks.add(new Stock(stockSymbol, filepath));
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void addStocks(String stockSymbol, String filepath) {
        this.stocks.add(new Stock(stockSymbol, filepath));
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

    public Cliente getCliente(int index) {
        return clientes.get(index);
    }

    public void addCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public void Compra(Stock stock, Cliente cliente, int volume, Date hora) {
        if (isC1Free()) {
            setC1Free(false);
            setOperações();
            cliente.setSaldoCompra(volume * stock.getValor(hora));
            caixaGeral.add(
                    new Caixa(cliente.getNome(), stock.getStockSymbol(), "compra", "Caixa1", hora,
                            stock.getValor(hora)));
            // lerCaixaGeral();
            setC1Free(true);
        } else if (isC2Free()) {
            setC2Free(false);
            setOperações();
            cliente.setSaldoCompra(volume * stock.getValor(hora));
            caixaGeral.add(
                    new Caixa(cliente.getNome(), stock.getStockSymbol(), "compra", "Caixa2", hora,
                            stock.getValor(hora)));
            setC2Free(true);
        } else
            fila = true;
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
            cliente.setSaldoVenda(volume * stock.getValor(hora));
            caixaGeral.add(
                    new Caixa(cliente.getNome(), stock.getStockSymbol(), "venda", "Caixa2", hora,
                            stock.getValor(hora)));
            setC2Free(true);
        } else
            fila = true;
    }

    public void lerCaixaGeral() {
        System.out.println(caixaGeral.get(i).getNomeCliente() + " " + caixaGeral.get(i).getSimboloAtivo() + " "
                + caixaGeral.get(i).getOperação() + " " + caixaGeral.get(i).getValor() + " "
                + caixaGeral.get(i).getDataHoraCompraVenda());
        i++;
        // for (Caixa caixa : caixaGeral) {
        // System.out.println(caixa.getNomeCliente() + " " + caixa.getSimboloAtivo() + "
        // " + caixa.getOperação() + " "
        // + caixa.getValor() + " " + caixa.getDataHoraCompraVenda());
        // }
    }

    public void run() {
        executor = Executors.newScheduledThreadPool(3);
        executor.scheduleAtFixedRate(getStocks().get(0), 50, 10, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(getStocks().get(1), 50, 10, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(getCliente(0), 80, 10, TimeUnit.MILLISECONDS);
    }

}
