import java.sql.Date;

public class Corretora {

    private String[] ativos = { "ativo1", "ativo2", "ativo3", "ativo4" };
    private int operações = 1000;
    private boolean isC1Free = true;
    private boolean isC2Free = true;

    public Corretora() {

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

    public void Caixa1(String ativo, boolean venda, Date datacompra, int volume) {
        setOperações();

    }

}
