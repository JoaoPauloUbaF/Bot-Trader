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
                Stock stock1 = new Stock("NZDUSDH1", "Bot Trader/files/NZDUSD_M1_202208010005_202208011314.csv");
                stock1.stockAnalysis(stock1);
                // Date data = DateUtilities.createDate(2022, 1, 3, 15, 00);
                Stock stock2 = new Stock("BIDI4", "Bot Trader/files/NZDUSD_M1_202208010005_202208011314.csv");
                // Stock stock3 = new Stock("EMBR3", "Bot Trader/files/Stock3.csv");
                // Stock stock4 = new Stock("NUBK1", "Bot Trader/files/Stock4.csv");
                // CSVReader csvr = new CSVReader();
                // csvr.readCSV("Bot Trader/files/NZDUSD_M1_202208010005_202208011314.csv");
                // List<String> datahora = new ArrayList<String>();
                // List<String> abertura = new ArrayList<String>();
                // datahora = csvr.getDatahora();
                // abertura = csvr.getAbertura();
                // for (String string : datahora) {
                // System.out.println(string);
                // }
        }
}
