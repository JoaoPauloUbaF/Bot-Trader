import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class Cliente implements Runnable {

    private int i = 0;

    public Cliente(String nome, Double saldo, Corretora corretora) {
        this.nome = nome;
        this.saldo = saldo;
        this.minhaCorretora = corretora;
    }

    private Double saldo;
    private String nome;
    private Corretora minhaCorretora;
    private List<Caixa> movimentacoes = new ArrayList<Caixa>();
    private static ScheduledExecutorService executor;
    public Double curtaSimpAnt = 0.0;
    public Double intermSimpAnt = 0.0;
    public Double longaSimpAnt = 0.0;
    public Double curtaExpAnt = 0.0;
    public Double intermExpAnt = 0.0;
    public Double longaExpAnt = 0.0;

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

    public void BaixaSimplesCurtaInter(Double simpcurta, Double simpinter) {
        if (curtaSimpAnt == 0.0) {
            curtaSimpAnt = simpcurta;
        }

        if (intermSimpAnt == 0.0) {
            intermSimpAnt = simpinter;
        }
        if (curtaSimpAnt <= intermSimpAnt) {
            if (simpcurta >= simpinter) {
                minhaCorretora.Venda(this.minhaCorretora.getStocks().get(0), this, 1,
                        this.minhaCorretora.getStocks().get(0).opTime.get(i));
                // System.out.println(saldo);
            }
        } else if (curtaSimpAnt >= intermSimpAnt) {
            if (simpcurta <= simpinter) {
                if (saldo > 0) {
                    minhaCorretora.Compra(this.minhaCorretora.getStocks().get(0), this, 1,
                            this.minhaCorretora.getStocks().get(0).opTime.get(i));
                    // System.out.println(saldo);
                }
            }
        }
    }

    public void BaixaSimplesInterLonga(Double simplonga, Double simpinter) {
        if (intermSimpAnt == 0.0) {
            intermSimpAnt = simpinter;
        }

        if (intermSimpAnt == 0.0) {
            longaSimpAnt = simplonga;
        }
        if (intermSimpAnt <= longaSimpAnt) {
            if (simpinter >= simplonga) {
                minhaCorretora.Venda(this.minhaCorretora.getStocks().get(0), this, 1,
                        this.minhaCorretora.getStocks().get(0).opTime.get(i));
                // System.out.println(saldo);
            }
        } else if (intermSimpAnt >= longaSimpAnt) {
            if (simpinter <= simplonga) {
                minhaCorretora.Compra(this.minhaCorretora.getStocks().get(0), this, 1,
                        this.minhaCorretora.getStocks().get(0).opTime.get(i));
                // System.out.println(saldo);
            }
        }
    }

    public void BaixaSimplesCurtaInterLonga(Double simpcurta, Double simpinter, Double simplonga) {
        if (curtaSimpAnt == 0.0) {
            curtaSimpAnt = simpcurta;
        }

        if (intermSimpAnt == 0.0) {
            intermSimpAnt = simpinter;
        }
        if (intermSimpAnt == 0.0) {
            longaSimpAnt = simplonga;
        }

        if (curtaSimpAnt <= intermSimpAnt) {
            if (simpcurta >= simpinter) {
                if (intermSimpAnt <= longaSimpAnt) {
                    if (simpinter >= simplonga) {
                        minhaCorretora.Venda(this.minhaCorretora.getStocks().get(0), this, 1,
                                this.minhaCorretora.getStocks().get(0).opTime.get(i));
                        // System.out.println(saldo);
                    }
                }
            }
        } else if (curtaSimpAnt >= intermSimpAnt) {
            if (simpcurta <= simpinter) {
                if (intermSimpAnt >= longaSimpAnt) {
                    if (simpinter <= simplonga) {
                        if (saldo > 0) {
                            minhaCorretora.Compra(this.minhaCorretora.getStocks().get(0), this, 1,
                                    this.minhaCorretora.getStocks().get(0).opTime.get(i));
                            // System.out.println(saldo);
                        }
                    }
                }
            }
        }
    }

    public void BaixaExpCurtaInter(Double expcurta, Double expinterm) {
        if (curtaExpAnt == 0.0) {
            curtaExpAnt = expcurta;
        }

        if (intermExpAnt == 0.0) {
            intermExpAnt = expinterm;
        }
        if (curtaExpAnt <= intermExpAnt) {
            if (expcurta >= expinterm) {
                minhaCorretora.Venda(this.minhaCorretora.getStocks().get(0), this, 1,
                        this.minhaCorretora.getStocks().get(0).opTime.get(i));
                // System.out.println(saldo);
            }
        } else if (curtaExpAnt >= intermExpAnt) {
            if (expcurta <= expinterm) {
                if (saldo > 0) {
                    minhaCorretora.Compra(this.minhaCorretora.getStocks().get(0), this, 1,
                            this.minhaCorretora.getStocks().get(0).opTime.get(i));
                    // System.out.println(saldo);
                }
            }
        }
    }

    public void BaixaExpInterLonga(Double expinterm, Double explonga) {
        if (intermExpAnt == 0.0) {
            intermExpAnt = expinterm;
        }

        if (longaExpAnt == 0.0) {
            longaExpAnt = explonga;
        }
        if (intermExpAnt <= longaExpAnt) {
            if (expinterm >= explonga) {
                minhaCorretora.Venda(this.minhaCorretora.getStocks().get(0), this, 1,
                        this.minhaCorretora.getStocks().get(0).opTime.get(i));
                // System.out.println(saldo);
            }
        } else if (intermExpAnt >= longaExpAnt) {
            if (expinterm <= explonga) {
                minhaCorretora.Compra(this.minhaCorretora.getStocks().get(0), this, 1,
                        this.minhaCorretora.getStocks().get(0).opTime.get(i));
                // System.out.println(saldo);
            }
        }
    }

    public void analiseCompraVenda(Double indc1, Double indic2, Double indic3) {
        // if (curtaSimpAnt == 0.0) {
        // curtaSimpAnt = simpcurta;
        // }

        // if (intermSimpAnt == 0.0) {
        // intermSimpAnt = simpinter;
        // }
        // if (intermSimpAnt == 0.0) {
        // longaSimpAnt = simplonga;
        // }

        // if (curtaSimpAnt <= intermSimpAnt) {
        // if (simpcurta >= simpinter) {
        // if (intermSimpAnt <= longaSimpAnt) {
        // if (simpinter >= simplonga) {
        // minhaCorretora.Venda(this.minhaCorretora.getStocks().get(0), this, 1,
        // this.minhaCorretora.getStocks().get(0).opTime.get(i));
        // // System.out.println(saldo);
        // }
        // }
        // }
        // } else if (curtaSimpAnt >= intermSimpAnt) {
        // if (simpcurta <= simpinter) {
        // if (intermSimpAnt >= longaSimpAnt) {
        // if (simpinter <= simplonga) {
        // if (saldo > 0) {
        // minhaCorretora.Compra(this.minhaCorretora.getStocks().get(0), this, 1,
        // this.minhaCorretora.getStocks().get(0).opTime.get(i));
        // // System.out.println(saldo);
        // }
        // }
        // }
        // }
        // }
    }

    @Override
    public void run() {
        Double simpcurta = this.minhaCorretora.getStocks().get(0).getsimpCurta(i);
        Double simpinter = this.minhaCorretora.getStocks().get(0).getsimpInter(i);
        Double simplonga = this.minhaCorretora.getStocks().get(0).getsimpLonga(i);
        Double expcurta = this.minhaCorretora.getStocks().get(0).getexpCurta(i);
        Double expinter = this.minhaCorretora.getStocks().get(0).getexpInter(i);
        Double explonga = this.minhaCorretora.getStocks().get(0).getexpLonga(i);

        if (curtaSimpAnt == 0.0) {
            curtaSimpAnt = simpcurta;

        }
        if (intermSimpAnt == 0.0) {
            intermSimpAnt = simpinter;
        }
        if (curtaSimpAnt <= intermSimpAnt) {
            if (simpcurta >= simpinter) {
                minhaCorretora.Compra(this.minhaCorretora.getStocks().get(0), this, 1,
                        this.minhaCorretora.getStocks().get(0).opTime.get(i));
                System.out.println(saldo);
            }
        } else if (curtaSimpAnt >= intermSimpAnt) {
            if (simpcurta <= simpinter) {
                minhaCorretora.Venda(this.minhaCorretora.getStocks().get(0), this, 1,
                        this.minhaCorretora.getStocks().get(0).opTime.get(i));
                System.out.println(saldo);
            }
        }

        if (this.minhaCorretora.getStocks().get(0).getFlag()) {
            executor.shutdown();
        }
        curtaSimpAnt = simpcurta;
        intermSimpAnt = simpinter;
        i++;

    }
}
