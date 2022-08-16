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

import org.jfree.date.DateUtilities;
import org.jfree.ui.RefineryUtilities;

import java.awt.EventQueue;

public class App {
        public static void main(String[] args) throws Exception {
                Stock stock1 = new Stock("NZDUSDH1", "Bot Trader/files/Stock1.csv");
                stock1.stockAnalysis(stock1);
                // Date data = DateUtilities.createDate(2022, 1, 3, 15, 00);
                Stock stock2 = new Stock("BIDI4", "Bot Trader/files/Stock2.csv");
                Stock stock3 = new Stock("EMBR3", "Bot Trader/files/Stock3.csv");
                Stock stock4 = new Stock("NUBK1", "Bot Trader/files/Stock4.csv");
        }
}
