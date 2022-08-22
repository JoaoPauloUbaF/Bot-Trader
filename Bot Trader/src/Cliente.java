import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class Cliente implements Runnable {

    private int i = 0;
    private int profile;

    public Cliente(String nome, Double saldo, Corretora corretora, int profile) {
        this.nome = nome;
        this.saldo = saldo;
        this.minhaCorretora = corretora;
        this.comprado.add(false);
        this.comprado.add(false);
        this.comprado.add(false);
        this.comprado.add(false);
        this.vendido.add(false);
        this.vendido.add(false);
        this.vendido.add(false);
        this.vendido.add(false);
        this.profile = profile;
    }

    private Double saldo;
    private String nome;
    private Corretora minhaCorretora;
    public List<Caixa> movimentacoes = new ArrayList<Caixa>();
    private static ScheduledExecutorService executor;
    private List<Boolean> comprado = new ArrayList<Boolean>();
    private List<Boolean> vendido = new ArrayList<Boolean>();

    public String getNome() {
        return nome;
    }

    //

    public void setSaldoVenda(double d) {
        this.saldo += d;
    }

    public void setSaldoCompra(double d) {
        this.saldo -= d;
    }

    public void analiseSimplesCurtaInter(Double simpcurta, Double simpinter, double curtaSimpAnt,
            double intermSimpAnt, int stockIndex) {
        if (curtaSimpAnt <= intermSimpAnt && !vendido.get(stockIndex)) {
            if (simpcurta >= simpinter) {
                if (comprado.get(stockIndex)) {
                    comprado.set(stockIndex, false);
                }
                vendido.set(stockIndex, true);
                this.minhaCorretora.Venda(this.minhaCorretora.getStocks().get(
                        stockIndex), this, 1,
                        this.minhaCorretora.getStocks().get(stockIndex).opTime.get(i));

            }
        } else if (curtaSimpAnt >= intermSimpAnt && !comprado.get(stockIndex)) {
            if (simpcurta <= simpinter) {
                if (saldo > 0) {
                    if (vendido.get(stockIndex)) {
                        vendido.set(stockIndex, false);
                    }
                    comprado.set(stockIndex, true);
                    this.minhaCorretora.Compra(this.minhaCorretora.getStocks().get(
                            stockIndex), this, 1,
                            this.minhaCorretora.getStocks().get(stockIndex).opTime.get(i));

                }
            }
        }
    }

    public void analiseSimplesInterLonga(Double simpinter, Double simplonga, double intermSimpAnt,
            Double longaSimpAnt, int stockIndex) {

        if (intermSimpAnt <= longaSimpAnt && !vendido.get(stockIndex)) {
            if (simpinter >= simplonga) {
                if (comprado.get(stockIndex)) {
                    comprado.set(stockIndex, false);
                }
                vendido.set(stockIndex, true);
                this.minhaCorretora.Venda(this.minhaCorretora.getStocks().get(
                        stockIndex), this, 1,
                        this.minhaCorretora.getStocks().get(stockIndex).opTime.get(i));

            }
        } else if (intermSimpAnt >= longaSimpAnt && !comprado.get(stockIndex)) {
            if (simpinter <= simplonga) {
                if (saldo > 0) {
                    if (vendido.get(stockIndex)) {
                        vendido.set(stockIndex, false);
                    }
                    comprado.set(stockIndex, true);
                    this.minhaCorretora.Compra(this.minhaCorretora.getStocks().get(
                            stockIndex), this, 1,
                            this.minhaCorretora.getStocks().get(stockIndex).opTime.get(i));

                }
            }
        }
    }

    public void analiseSimplesCurtaInterLonga(Double simpcurta, Double simpinter, Double simplonga, double curtaSimpAnt,
            double intermSimpAnt, Double longaSimpAnt, int stockIndex) {

        if (curtaSimpAnt <= intermSimpAnt && !vendido.get(stockIndex)) {
            if (simpcurta >= simpinter) {
                if (intermSimpAnt <= longaSimpAnt) {
                    if (comprado.get(stockIndex)) {
                        comprado.set(stockIndex, false);
                    }
                    vendido.set(stockIndex, true);
                    this.minhaCorretora.Venda(this.minhaCorretora.getStocks().get(
                            stockIndex), this, 1,
                            this.minhaCorretora.getStocks().get(stockIndex).opTime.get(i));

                }
            }
        } else if (curtaSimpAnt >= intermSimpAnt) {
            if (simpcurta <= simpinter) {
                if (intermSimpAnt >= longaSimpAnt) {
                    if (simpinter <= simplonga) {
                        if (saldo > 0) {
                            if (vendido.get(stockIndex)) {
                                vendido.set(stockIndex, false);
                            }
                            comprado.set(stockIndex, true);
                            this.minhaCorretora.Compra(this.minhaCorretora.getStocks().get(
                                    stockIndex), this, 1,
                                    this.minhaCorretora.getStocks().get(stockIndex).opTime.get(i));

                        }
                    }
                }
            }
        }
    }

    @Override
    public void run() {

        if (this.minhaCorretora.getStocks().get(0).getFlag()) {
            executor.shutdown();
        }

        Double simpcurta1 = this.minhaCorretora.getStocks().get(0).getsimpCurta(i);
        Double simpinter1 = this.minhaCorretora.getStocks().get(0).getsimpInter(i);
        Double simplonga1 = this.minhaCorretora.getStocks().get(0).getsimpLonga(i);
        Double expcurta1 = this.minhaCorretora.getStocks().get(0).getexpCurta(i);
        Double expinter1 = this.minhaCorretora.getStocks().get(0).getexpInter(i);
        Double explonga1 = this.minhaCorretora.getStocks().get(0).getexpLonga(i);
        Double curtaSimpAnt1 = 0.0;
        Double intermSimpAnt1 = 0.0;
        Double longaSimpAnt1 = 0.0;
        Double curtaExpAnt1 = 0.0;
        Double intermExpAnt1 = 0.0;
        Double longaExpAnt1 = 0.0;

        Double simpcurta2 = this.minhaCorretora.getStocks().get(1).getsimpCurta(i);
        Double simpinter2 = this.minhaCorretora.getStocks().get(1).getsimpInter(i);
        Double simplonga2 = this.minhaCorretora.getStocks().get(1).getsimpLonga(i);
        Double expcurta2 = this.minhaCorretora.getStocks().get(1).getexpCurta(i);
        Double expinter2 = this.minhaCorretora.getStocks().get(1).getexpInter(i);
        Double explonga2 = this.minhaCorretora.getStocks().get(1).getexpLonga(i);
        Double curtaSimpAnt2 = 0.0;
        Double intermSimpAnt2 = 0.0;
        Double longaSimpAnt2 = 0.0;
        Double curtaExpAnt2 = 0.0;
        Double intermExpAnt2 = 0.0;
        Double longaExpAnt2 = 0.0;

        Double simpcurta3 = this.minhaCorretora.getStocks().get(2).getsimpCurta(i);
        Double simpinter3 = this.minhaCorretora.getStocks().get(2).getsimpInter(i);
        Double simplonga3 = this.minhaCorretora.getStocks().get(2).getsimpLonga(i);
        Double expcurta3 = this.minhaCorretora.getStocks().get(2).getexpCurta(i);
        Double expinter3 = this.minhaCorretora.getStocks().get(2).getexpInter(i);
        Double explonga3 = this.minhaCorretora.getStocks().get(2).getexpLonga(i);
        Double curtaSimpAnt3 = 0.0;
        Double intermSimpAnt3 = 0.0;
        Double longaSimpAnt3 = 0.0;
        Double curtaExpAnt3 = 0.0;
        Double intermExpAnt3 = 0.0;
        Double longaExpAnt3 = 0.0;

        Double simpcurta4 = this.minhaCorretora.getStocks().get(3).getsimpCurta(i);
        Double simpinter4 = this.minhaCorretora.getStocks().get(3).getsimpInter(i);
        Double simplonga4 = this.minhaCorretora.getStocks().get(3).getsimpLonga(i);
        Double expcurta4 = this.minhaCorretora.getStocks().get(3).getexpCurta(i);
        Double expinter4 = this.minhaCorretora.getStocks().get(3).getexpInter(i);
        Double explonga4 = this.minhaCorretora.getStocks().get(3).getexpLonga(i);
        Double curtaSimpAnt4 = 0.0;
        Double intermSimpAnt4 = 0.0;
        Double longaSimpAnt4 = 0.0;
        Double curtaExpAnt4 = 0.0;
        Double intermExpAnt4 = 0.0;
        Double longaExpAnt4 = 0.0;

        if (i == 0) {
            curtaSimpAnt1 = this.minhaCorretora.getStocks().get(0).getsimpCurta(i);
            intermSimpAnt1 = this.minhaCorretora.getStocks().get(0).getsimpInter(i);
            longaSimpAnt1 = this.minhaCorretora.getStocks().get(0).getsimpLonga(i);
            curtaExpAnt1 = this.minhaCorretora.getStocks().get(0).getexpCurta(i);
            intermExpAnt1 = this.minhaCorretora.getStocks().get(0).getexpInter(i);
            longaExpAnt1 = this.minhaCorretora.getStocks().get(0).getexpLonga(i);

            curtaSimpAnt2 = this.minhaCorretora.getStocks().get(1).getsimpCurta(i);
            intermSimpAnt2 = this.minhaCorretora.getStocks().get(1).getsimpInter(i);
            longaSimpAnt2 = this.minhaCorretora.getStocks().get(1).getsimpLonga(i);
            curtaExpAnt2 = this.minhaCorretora.getStocks().get(1).getexpCurta(i);
            intermExpAnt2 = this.minhaCorretora.getStocks().get(1).getexpInter(i);
            longaExpAnt2 = this.minhaCorretora.getStocks().get(1).getexpLonga(i);

            curtaSimpAnt3 = this.minhaCorretora.getStocks().get(2).getsimpCurta(i);
            intermSimpAnt3 = this.minhaCorretora.getStocks().get(2).getsimpInter(i);
            longaSimpAnt3 = this.minhaCorretora.getStocks().get(2).getsimpLonga(i);
            curtaExpAnt3 = this.minhaCorretora.getStocks().get(2).getexpCurta(i);
            intermExpAnt3 = this.minhaCorretora.getStocks().get(2).getexpInter(i);
            longaExpAnt3 = this.minhaCorretora.getStocks().get(2).getexpLonga(i);

            curtaSimpAnt4 = this.minhaCorretora.getStocks().get(3).getsimpCurta(i);
            intermSimpAnt4 = this.minhaCorretora.getStocks().get(3).getsimpInter(i);
            longaSimpAnt4 = this.minhaCorretora.getStocks().get(3).getsimpLonga(i);
            curtaExpAnt4 = this.minhaCorretora.getStocks().get(3).getexpCurta(i);
            intermExpAnt4 = this.minhaCorretora.getStocks().get(3).getexpInter(i);
            longaExpAnt4 = this.minhaCorretora.getStocks().get(3).getexpLonga(i);
        } else {
            curtaSimpAnt1 = this.minhaCorretora.getStocks().get(0).getsimpCurta(i - 1);
            intermSimpAnt1 = this.minhaCorretora.getStocks().get(0).getsimpInter(i - 1);
            longaSimpAnt1 = this.minhaCorretora.getStocks().get(0).getsimpLonga(i - 1);
            curtaExpAnt1 = this.minhaCorretora.getStocks().get(0).getexpCurta(i - 1);
            intermExpAnt1 = this.minhaCorretora.getStocks().get(0).getexpInter(i - 1);
            longaExpAnt1 = this.minhaCorretora.getStocks().get(0).getexpLonga(i - 1);

            curtaSimpAnt2 = this.minhaCorretora.getStocks().get(1).getsimpCurta(i - 1);
            intermSimpAnt2 = this.minhaCorretora.getStocks().get(1).getsimpInter(i - 1);
            longaSimpAnt2 = this.minhaCorretora.getStocks().get(1).getsimpLonga(i - 1);
            curtaExpAnt2 = this.minhaCorretora.getStocks().get(1).getexpCurta(i - 1);
            intermExpAnt2 = this.minhaCorretora.getStocks().get(1).getexpInter(i - 1);
            longaExpAnt2 = this.minhaCorretora.getStocks().get(1).getexpLonga(i - 1);

            curtaSimpAnt3 = this.minhaCorretora.getStocks().get(2).getsimpCurta(i - 1);
            intermSimpAnt3 = this.minhaCorretora.getStocks().get(2).getsimpInter(i - 1);
            longaSimpAnt3 = this.minhaCorretora.getStocks().get(2).getsimpLonga(i - 1);
            curtaExpAnt3 = this.minhaCorretora.getStocks().get(2).getexpCurta(i - 1);
            intermExpAnt3 = this.minhaCorretora.getStocks().get(2).getexpInter(i - 1);
            longaExpAnt3 = this.minhaCorretora.getStocks().get(2).getexpLonga(i - 1);

            curtaSimpAnt4 = this.minhaCorretora.getStocks().get(3).getsimpCurta(i - 1);
            intermSimpAnt4 = this.minhaCorretora.getStocks().get(3).getsimpInter(i - 1);
            longaSimpAnt4 = this.minhaCorretora.getStocks().get(3).getsimpLonga(i - 1);
            curtaExpAnt4 = this.minhaCorretora.getStocks().get(3).getexpCurta(i - 1);
            intermExpAnt4 = this.minhaCorretora.getStocks().get(3).getexpInter(i - 1);
            longaExpAnt4 = this.minhaCorretora.getStocks().get(3).getexpLonga(i - 1);

        }
        if (this.profile == 1) {
            analiseSimplesCurtaInter(simpcurta1, simpinter1, curtaSimpAnt1, intermSimpAnt1, 0);
            analiseSimplesCurtaInter(simpcurta2, simpinter2, curtaSimpAnt2, intermSimpAnt2, 1);
            analiseSimplesCurtaInter(simpcurta3, simpinter3, curtaSimpAnt3, intermSimpAnt3, 2);
            analiseSimplesCurtaInter(simpcurta4, simpinter4, curtaSimpAnt4, intermSimpAnt4, 3);
        }
        if (profile == 2) {
            analiseSimplesInterLonga(simpinter1, simplonga1, intermSimpAnt1, longaSimpAnt1, 0);
            analiseSimplesInterLonga(simpinter2, simplonga2, intermSimpAnt2, longaSimpAnt2, 1);
            analiseSimplesInterLonga(simpinter3, simplonga3, intermSimpAnt3, longaSimpAnt3, 2);
            analiseSimplesInterLonga(simpinter4, simplonga4, intermSimpAnt4, longaSimpAnt4, 3);
        }
        if (profile == 3) {
            analiseSimplesCurtaInterLonga(simpcurta1, simpinter1, simplonga1, curtaSimpAnt1, intermSimpAnt1,
                    longaSimpAnt1, 0);
            analiseSimplesCurtaInterLonga(simpcurta2, simpinter2, simplonga2, curtaSimpAnt2, intermSimpAnt2,
                    longaSimpAnt2, 1);
            analiseSimplesCurtaInterLonga(simpcurta3, simpinter3, simplonga3, curtaSimpAnt3, intermSimpAnt3,
                    longaSimpAnt3, 2);
            analiseSimplesCurtaInterLonga(simpcurta4, simpinter4, simplonga4, curtaSimpAnt4, intermSimpAnt4,
                    longaSimpAnt4, 3);
        }
        if (this.profile == 4) {
            analiseSimplesCurtaInter(expcurta1, expinter1, curtaExpAnt1, intermExpAnt1, 0);
            analiseSimplesCurtaInter(expcurta2, expinter2, curtaExpAnt2, intermExpAnt2, 1);
            analiseSimplesCurtaInter(expcurta3, expinter3, curtaExpAnt3, intermExpAnt3, 2);
            analiseSimplesCurtaInter(expcurta4, expinter4, curtaExpAnt4, intermExpAnt4, 3);
        }
        if (profile == 5) {
            analiseSimplesInterLonga(expinter1, explonga1, intermExpAnt1, longaExpAnt1, 0);
            analiseSimplesInterLonga(expinter2, explonga2, intermExpAnt2, longaExpAnt2, 1);
            analiseSimplesInterLonga(expinter3, explonga3, intermExpAnt3, longaExpAnt3, 2);
            analiseSimplesInterLonga(expinter4, explonga4, intermExpAnt4, longaExpAnt4, 3);
        }
        if (profile == 6) {
            analiseSimplesCurtaInterLonga(
                    expcurta1, expinter1,
                    explonga1, curtaExpAnt1, intermExpAnt1,
                    longaExpAnt1, 0);
            analiseSimplesCurtaInterLonga(
                    expcurta2,
                    expinter2,
                    explonga2, curtaExpAnt2, intermExpAnt2,
                    longaExpAnt2, 1);
            analiseSimplesCurtaInterLonga(
                    expcurta3,
                    expinter3,
                    explonga3, expcurta3, intermExpAnt3,
                    longaExpAnt3, 2);
            analiseSimplesCurtaInterLonga(expcurta4,
                    expinter4,
                    explonga4, curtaExpAnt4, intermExpAnt4,
                    longaExpAnt4, 3);

        }

        // analiseSimplesInterLonga(simpinter1, simplonga1, intermSimpAnt1,
        // longaSimpAnt1, 1);
        // analiseSimplesCurtaInter(simpcurta2, simpinter2, curtaSimpAnt2,
        // intermSimpAnt2, 2);

        curtaSimpAnt1 = simpcurta1;
        intermSimpAnt1 = simpinter1;
        longaSimpAnt1 = simplonga1;
        curtaExpAnt1 = expcurta1;
        intermExpAnt1 = expinter1;
        longaExpAnt1 = explonga1;

        curtaSimpAnt2 = simpcurta2;
        intermSimpAnt2 = simpinter2;
        longaSimpAnt2 = simplonga2;
        curtaExpAnt2 = expcurta2;
        intermExpAnt2 = expinter2;
        longaExpAnt2 = explonga2;

        curtaSimpAnt3 = simpcurta3;
        intermSimpAnt3 = simpinter3;
        longaSimpAnt3 = simplonga3;
        curtaExpAnt3 = expcurta3;
        intermExpAnt3 = expinter3;
        longaExpAnt3 = explonga3;

        curtaSimpAnt4 = simpcurta4;
        intermSimpAnt4 = simpinter4;
        longaSimpAnt4 = simplonga4;
        curtaExpAnt4 = expcurta4;
        intermExpAnt4 = expinter4;
        longaExpAnt4 = explonga4;

        i++;

    }
}
