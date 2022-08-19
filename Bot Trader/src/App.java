import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

import org.jfree.date.DateUtilities;
import org.jfree.ui.RefineryUtilities;

import java.awt.EventQueue;

public class App {
        public static void main(String[] args) throws Exception {

                Corretora corretora = new Corretora("BIDI4",
                                "F:/codes/Bot-Trader/Bot Trader/files/NZDUSD_M1_202208010005_202208011314 copy.csv");
                corretora.addStocks("NZDUSDH1",
                                "F:/codes/Bot-Trader/Bot Trader/files/NZDUSD_M1_202208010005_202208011314 copy.csv");

                Cliente cliente1 = new Cliente("Rubens", 100.0, corretora);

                corretora.addCliente(cliente1);
                corretora.start();

        }
}
