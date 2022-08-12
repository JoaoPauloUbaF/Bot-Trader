import java.util.ArrayList;
import java.util.List;

public class IndicatorCalc {
    private double sum = 0.0;
    private double standardDeviation = 0.0;
    private double mean = 0.0;
    private double res = 0.0;
    private double sq = 0.0;

    private Double oldValue;

    public double average(double value, double alpha) {
        if (oldValue == null) {
            oldValue = value;
            return value;
        }
        double newValue = oldValue + alpha * (value - oldValue);
        oldValue = newValue;
        return newValue;
    }

    public List<Double> expAverageToCsv(List<String> data, double alpha) {
        List<Double> result = new ArrayList<Double>();
        for (String value : data) {
            if (oldValue == null) {
                oldValue = Double.parseDouble(value);
                result.add(Double.parseDouble(value));
            }
            double newValue = oldValue + alpha * (Double.parseDouble(value) - oldValue);
            oldValue = newValue;
            result.add(oldValue);
        }
        return result;
    }

    public double getMedia(List<String> data) {
        int n = data.size();
        double[] args = new double[n];
        int m = 0;
        // System.out.println("Elements are:");
        for (String string : data) {
            args[m] = Double.parseDouble(string);
            m++;
            // System.out.println(Double.parseDouble(string));
        }

        for (int i = 0; i < n; i++) {
            sum = sum + args[i];
        }

        mean = sum / (n);
        return mean;
    }

    public List<Double> getMovingMediaSimple(List<String> data, int period) {
        int n = data.size();
        double[] args = new double[n];
        List<Double> movingAverage = new ArrayList<Double>();
        int m = 0;
        // System.out.println("Elements are:");
        for (String string : data) {
            args[m] = Double.parseDouble(string);
            m++;
            // System.out.println(Double.parseDouble(string));
        }
        sum = 0.0;
        mean = 0.0;

        int ratio = period;
        for (int i = 0; i <= (args.length - ratio); i++) {
            for (int j = i; j < period; j++) {
                sum = sum + args[j];
            }
            mean = sum / ratio;
            movingAverage.add(mean);
            // System.out.println("Media" + "" + ": " + mean);
            if (period <= args.length)
                period++;
            sum = 0;
        }

        // mean = sum / (n);
        return movingAverage;
    }

    public Double getMovingMediaSimpleChart(List<String> data, int period, int iteration) {
        int n = data.size();
        double[] args = new double[n];
        Double movingAverage = 0.0;
        int m = 0;
        // System.out.println("Elements are:");
        for (int i = iteration; i < period + iteration; i++) {
            args[m] = Double.parseDouble(data.get(i));
            m++;
            // System.out.println(Double.parseDouble(string));
        }

        sum = 0.0;
        mean = 0.0;
        for (int i = 0; i < n; i++) {
            sum = sum + args[i];
        }
        mean = sum / period;
        movingAverage = mean;
        // System.out.println("Media" + "" + ": " + mean);
        if (period <= args.length)
            period++;
        sum = 0;

        // mean = sum / (n);
        return movingAverage;
    }

    public double SD(List<String> data) { // https://acervolima.com/programa-java-para-calcular-o-desvio-padrao/#:~:text=O%20desvio%20padr%C3%A3o%20%C3%A9%20a,a%20raiz%20quadrada%20da%20vari%C3%A2ncia.
        sum = 0.0;
        mean = 0.0;
        int n = data.size();
        double[] args = new double[n];
        int m = 0;
        // System.out.println("Elements are:");
        for (String string : data) {
            args[m] = Double.parseDouble(string);
            m++;
            // System.out.println(Double.parseDouble(string));
        }

        for (int i = 0; i < n; i++) {
            sum = sum + args[i];
        }

        mean = sum / (n);

        for (int i = 0; i < n; i++) {

            standardDeviation = standardDeviation + Math.pow((args[i] - mean), 2);

        }

        sq = standardDeviation / n;
        res = Math.sqrt(sq);
        return res;
    }

}
