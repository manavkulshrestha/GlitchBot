import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String pw = "";
        String sK = "";

        Bot drk1ng;
        boolean useSessionKey = false;

        if(useSessionKey)
            drk1ng = new Bot("Dr.K1NG", sK, true);
        else
            drk1ng = new Bot("Dr.K1NG", pw);


        System.out.printf("SessionKey: %s\n", drk1ng.sessionKey);

        System.out.printf("Rank: %d\n", drk1ng.rank);
        System.out.printf("Reputation: %d\n", drk1ng.reputation);
        System.out.println(drk1ng.stats);
        System.out.printf("Money: %s\n", drk1ng.money);
        System.out.printf("Premium: %d\n\n", drk1ng.premium);

        System.out.printf("Withdrawing %s from bank\n", drk1ng.bank);
        drk1ng.withdraw(drk1ng.bank);

        String response, lastHack="NONE";
        int moneyThresh = 1000000000, hackCountThresh = 10, hackTimeoutThresh = 500;
        String[] upgradeSofts = {"VPN", "ZeroDay", "SDK"};

        for(int sIndex=0, hackTimeoutCount=0; hackTimeoutCount<hackTimeoutThresh; sIndex++) {
            while(Integer.parseInt(drk1ng.money)<moneyThresh && hackTimeoutCount<hackTimeoutThresh) {
                for(int hackCount=0; hackCount<hackCountThresh && hackTimeoutCount<hackTimeoutThresh;) {
                    for(String[] group: drk1ng.refreshIPs()) {
                        if(group[2].equals("TRUE")) {
                            rWait(500, 1500);
                            response = drk1ng.zeroDay(group[0]);
                            System.out.println(response);
                            if(response.contains("Success"))
                                hackCount++;
                            hackTimeoutCount = 0;
                            lastHack = getTime();
                        } else {
                            hackTimeoutCount++;
                            System.out.printf("Can't hack IP (timeout) : %d\n", hackTimeoutCount);
                        }
                    }
                }
                drk1ng.updateBankInfo();
                System.out.println("\nFetching updated bank info...\n");
            }
            sIndex %= upgradeSofts.length;
            System.out.println("Upgrading: "+upgradeSofts[sIndex]);
            do {
                rWait(100, 200);
                response = drk1ng.upgradeSoftware(upgradeSofts[sIndex], 10);
                System.out.println(response);
            } while(!response.contains("!"));
            drk1ng.updateInfo();
            System.out.println("Fetching updated info...");
            System.out.printf("Rank: %d\n", drk1ng.rank);
            System.out.printf("Reputation: %d\n\n", drk1ng.reputation);
        }

        System.out.printf("Depositing %s to bank\n", drk1ng.money);
        drk1ng.deposit(drk1ng.money);

        System.out.printf("Last successful hack was at %s\n", lastHack);
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

    public static String getTime() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}