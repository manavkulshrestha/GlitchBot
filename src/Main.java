import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String sK = "TEFceG8xlLRl9X4czFl8uB9YOoOT6OGixxdUK6ZLdbqxTWO5hA";

        JSONObject info = new JSONObject();

//        Bot drk1ng = new Bot("Dr.K1NG", pw);
        Bot drk1ng = new Bot("Dr.K1NG", sK, true);
        System.out.printf("SessionKey: %s\n", drk1ng.sessionKey);

        System.out.printf("Rank: $d\n", drk1ng.rank);
        System.out.printf("Reputation: $d\n", drk1ng.reputation);
        System.out.println(drk1ng.stats);
        System.out.printf("Money: $d\n", drk1ng.money);
        System.out.printf("Premium: $d\n", drk1ng.premium);

        for(String[] group: drk1ng.refreshIPs()) {
            System.out.println(drk1ng.zeroDay(group[0]));

        }
    }

    public static void rWait(int rangeStart, int rangeEnd) {
        try {
            Thread.sleep(new Random().nextInt(rangeEnd+1)+rangeStart);
        } catch(InterruptedException ex) {
            System.out.print(ex);
        }
    }

    public static void save(int[][] group) {

    }

    public static String[][] readFile(String fileName) {
        BufferedReader fileReader = null;
        String line;
        ArrayList<String[]> ips = new ArrayList<>();

        try {
            fileReader = new BufferedReader(new FileReader(fileName));

            while((line = fileReader.readLine()) != null)
                ips.add(line.split(","));
        } catch(Exception ex) {
            System.out.print(ex);
        } finally {
            try {
                fileReader.close();
            } catch(IOException ex) {
                System.out.print(ex);
            }
        }
        return ips.toArray(new String[ips.size()][3]);
    }

    public static String[][] sReadFile(String fileName) {
        BufferedReader fileReader = null;
        String line;
        ArrayList<String[]> ips = new ArrayList<>();

        try {
            fileReader = new BufferedReader(new FileReader(fileName));

            while((line = fileReader.readLine()) != null)
                ips.add(line.split(","));
        } catch(Exception ex) {
            System.out.print(ex);
        } finally {
            try {
                fileReader.close();
            } catch(IOException ex) {
                System.out.print(ex);
            }
        }
        return ips.toArray(new String[ips.size()][3]);
    }
}