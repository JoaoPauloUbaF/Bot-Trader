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
                ScheduledExecutorService executor;

                // Date data = DateUtilities.createDate(2022, 1, 3, 15, 00);
                Stock stock2 = new Stock("BIDI4",
                                "F:/codes/Bot-Trader/Bot Trader/files/NZDUSD_M1_202208010005_202208011314 copy.csv");

                Stock stock1 = new Stock("NZDUSDH1",
                                "F:/codes/Bot-Trader/Bot Trader/files/NZDUSD_M1_202208010005_202208011314 copy.csv");
                executor = Executors.newScheduledThreadPool(2);

                executor.scheduleAtFixedRate(stock1, 50, 100, TimeUnit.MILLISECONDS);
                executor.scheduleAtFixedRate(stock2, 50, 200, TimeUnit.MILLISECONDS);

        }
}
