import java.util.ArrayList;
import java.util.List;

public class Indicator {

    List<Double> medAbertura = new ArrayList<Double>();;

    List<Double> medFecha = new ArrayList<Double>();
    List<Double> medAlta = new ArrayList<Double>();
    List<Double> medBaixa = new ArrayList<Double>();
    List<Double> desvioPad = new ArrayList<Double>();

    public List<Double> getMedAbertura() {
        return medAbertura;
    }

    public Indicator(List<Double> medFecha, List<Double> medAlta, List<Double> medBaixa,
            List<Double> desvioPad) {
        this.medAbertura = medAbertura;
        this.desvioPad = desvioPad;
        this.medAlta = medAlta;
        this.medBaixa = medBaixa;
        this.medFecha = medFecha;
    }
}
