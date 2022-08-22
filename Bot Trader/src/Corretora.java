import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Corretora extends Thread {

    private int i = 0;
    private List<Stock> stocks = new ArrayList<Stock>();
    private int operacoes = 1000;
    private boolean isC1Free = true;
    private boolean isC2Free = true;
    private List<Caixa> caixaGeral = new ArrayList<Caixa>();
    private ScheduledExecutorService executor;
    private List<Cliente> clientes = new ArrayList<Cliente>();
    public boolean fila = false;

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

    public void setoperacoes() {
        this.operacoes--;
    }

    public Cliente getCliente(int index) {
        return clientes.get(index);
    }

    public void addCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public void Compra(Stock stock, Cliente cliente, int volume, Date hora) {
        if (operacoes > 0) {
            if (isC1Free()) {
                setC1Free(false);
                setoperacoes();
                cliente.setSaldoCompra(volume * stock.getValor(hora));
                caixaGeral.add(
                        new Caixa(cliente.getNome(), stock.getStockSymbol(), "Compra", "Caixa1", hora,
                                stock.getValor(hora)));
                cliente.movimentacoes.add(
                        new Caixa(cliente.getNome(), stock.getStockSymbol(), "Venda", "Caixa2", hora,
                                stock.getValor(hora)));
                setC1Free(true);
                fila = false;
                lerCaixaGeral();
            } else if (isC2Free()) {
                setC2Free(false);
                setoperacoes();
                cliente.setSaldoCompra(volume * stock.getValor(hora));
                caixaGeral.add(
                        new Caixa(cliente.getNome(), stock.getStockSymbol(), "Compra", "Caixa2", hora,
                                stock.getValor(hora)));
                cliente.movimentacoes.add(
                        new Caixa(cliente.getNome(), stock.getStockSymbol(), "Venda", "Caixa2", hora,
                                stock.getValor(hora)));

                setC2Free(true);
                fila = false;
                lerCaixaGeral();
            }

        } else

        {
            System.out.println("Operações Esgotadas!");
        }
    }

    public void Venda(Stock stock, Cliente cliente, int volume, Date hora) {
        if (operacoes > 0) {
            if (isC1Free()) {
                setC1Free(false);
                setoperacoes();
                cliente.setSaldoVenda(volume * stock.getValor(hora));
                caixaGeral.add(
                        new Caixa(cliente.getNome(), stock.getStockSymbol(), "Venda", "Caixa1", hora,
                                stock.getValor(hora)));
                cliente.movimentacoes.add(
                        new Caixa(cliente.getNome(), stock.getStockSymbol(), "Venda", "Caixa2", hora,
                                stock.getValor(hora)));

                setC1Free(true);
                fila = false;
                lerCaixaGeral();
            } else if (isC2Free()) {
                setC2Free(false);
                setoperacoes();
                cliente.setSaldoVenda(volume * stock.getValor(hora));
                caixaGeral.add(
                        new Caixa(cliente.getNome(), stock.getStockSymbol(), "Venda", "Caixa2", hora,
                                stock.getValor(hora)));
                cliente.movimentacoes.add(
                        new Caixa(cliente.getNome(), stock.getStockSymbol(), "Venda", "Caixa2", hora,
                                stock.getValor(hora)));

                setC2Free(true);
                fila = false;
                lerCaixaGeral();
            } else if (!isC1Free() && !isC2Free()) {
                Venda(stock, cliente, volume, hora);
            }

        } else

        {
            System.out.println("Operações Esgotadas!");
        }
    }

    public void lerCaixaGeral() {
        // for (Caixa caixa : caixaGeral) {
        // System.out.println(caixa.getNomeCliente() + " " + caixa.getSimboloAtivo() + "
        // "
        // + caixa.getOperação()
        // + " " + caixa.getValor()
        // + " "
        // + caixa.getCaixa()
        // + " "
        // + caixa.getDataHoraCompraVenda());
        // }
        // System.out.println("-------------------------------------------");
        System.out.println(caixaGeral.get(caixaGeral.size() - 1).getNomeCliente() + " "
                + caixaGeral.get(caixaGeral.size() - 1).getSimboloAtivo() + " "
                + caixaGeral.get(caixaGeral.size() - 1).getOperação() + " "
                + caixaGeral.get(caixaGeral.size() - 1).getValor() + " "
                + caixaGeral.get(caixaGeral.size() - 1).getCaixa() + " "
                + caixaGeral.get(caixaGeral.size() - 1).getDataHoraCompraVenda());

    }

    public void run() {
        executor = Executors.newScheduledThreadPool(10);
        executor.scheduleAtFixedRate(getStocks().get(0), 50, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(getStocks().get(1), 50, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(getStocks().get(2), 50, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(getStocks().get(3), 50, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(getCliente(0), 75, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(getCliente(1), 75, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(getCliente(2), 75, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(getCliente(3), 75, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(getCliente(4), 75, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(getCliente(5), 75, 1000, TimeUnit.MILLISECONDS);
        System.out.println("Caixa Geral");
    }

}
