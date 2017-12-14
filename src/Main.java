import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String pw = "scarletData117";
        String sK = "TEFceG8xlLRl9X4czFl8uB9YOoOT6OGixxdUK6ZLdbqxTWO5hA";

        JSONObject info = new JSONObject();

        String[] names = {"Jobe92D"};

//        Bot drk1ng = new Bot("Dr.K1NG", pw);
        Bot drk1ng = new Bot("Dr.K1NG", sK, true);
        System.out.printf("SessionKey: %s\n", drk1ng.sessionKey);

        for(String name: names) {
            String resp = drk1ng.zeroDay(name);
            rWait(1000, 3000);
            System.out.print(resp);
        }

        System.out.print(drk1ng.refreshIPs());
    }

    public static void rWait(int rangeStart, int rangeEnd) {
        try {
            Thread.sleep(new Random().nextInt(rangeEnd+1)+rangeStart);
        } catch(InterruptedException ex) {
            System.out.print(ex);
        }
    }
}