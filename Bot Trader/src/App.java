
public class App {
        public static void main(String[] args) throws Exception {

                Corretora corretora = new Corretora("AUDUSD",
                                "F:/codes/Bot-Trader/Bot Trader/files/AUDUSD_M1_202208160000_202208170000.csv");

                corretora.addStocks("EURUSD",
                                "F:/codes/Bot-Trader/Bot Trader/files/EURUSD_M1_202208160001_202208170000.csv");

                corretora.addStocks("NZDUSDH1",
                                "F:/codes/Bot-Trader/Bot Trader/files/NZDUSD_M1_202208160000_202208170000.csv");

                corretora.addStocks("USDCAD",
                                "F:/codes/Bot-Trader/Bot Trader/files/USDCAD_M1_202208160000_202208170000.csv");

                Cliente cliente1 = new Cliente("Ludmila", 100.0, corretora, 1);
                Cliente cliente2 = new Cliente("Mateus", 100.0, corretora, 2);
                Cliente cliente3 = new Cliente("Pablo", 100.0, corretora, 3);
                Cliente cliente4 = new Cliente("Debora", 100.0, corretora, 4);
                Cliente cliente5 = new Cliente("Joao", 100.0, corretora, 5);
                Cliente cliente6 = new Cliente("Paulo", 100.0, corretora, 6);
                corretora.addCliente(cliente1);
                corretora.addCliente(cliente2);
                corretora.addCliente(cliente3);
                corretora.addCliente(cliente4);
                corretora.addCliente(cliente5);
                corretora.addCliente(cliente6);
                corretora.start();

        }
}
