import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Bot {
    public String username;
    public String sessionKey;
    public String money;
    public String premium;
    public String rank;
    public String reputation;
    public HashMap<String, String> stats = new HashMap();
    public JSONObject basicReq = new JSONObject();

    public Bot(String username, String sessionKey, boolean isSessionKey) {
        this.username = username;
        this.sessionKey = sessionKey;

        buildBasicReq();
        updateInfo();
    }

    public Bot(String username, String password) {
        JSONObject plainPW = new JSONObject();
        try {
            plainPW.put("Input", password);
        } catch(JSONException ex) {
            System.out.print(ex);
        }

        password = request("V2/Misc/Encryption/", plainPW);

        JSONObject loginInfo = new JSONObject();
        try {
            loginInfo.put("Username", username);
            loginInfo.put("Password", password);
        } catch(JSONException ex) {
            System.out.print(ex);
        }

        String sessionKey = request("V2/User/Login", loginInfo);

        this.username = username;
        this.sessionKey = sessionKey;

        buildBasicReq();
        updateInfo();

    }

    public String request(String addOn, JSONObject data) {
        try {
            String dataString = "Data=" + URLEncoder.encode(data.toString(), "UTF-8");
            HttpURLConnection conn = (HttpURLConnection) new URL("http://api.glitchednet.com/" + addOn).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(dataString);
            writer.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String retData = "";
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    writer.close();
                    reader.close();
                    return retData.replace("<br />", "\n").replace("<br >", "\n").replace("<br>", "\n");
                }
                retData = new StringBuilder(String.valueOf(retData)).append(line).toString();
            }
        } catch (Exception ex) {
            return "Error Connecting to Server: "+ex;
        }
    }

    public void buildBasicReq() {
        try {
            this.basicReq.put("Username", this.username);
            this.basicReq.put("SessionKey", this.sessionKey);
        } catch(JSONException ex) {
            System.out.print(ex+"5");
        }
    }

    public void updateInfo() {
        JSONObject info = this.basicReq;
        try {
            info.put("AppVersion", "1.30");
        } catch(JSONException ex) {
            System.out.print(ex);
        }

        JSONObject jInfo = new JSONObject();

        try{
            jInfo = new JSONObject(request("V2/User/GetUserInfo", info));
        } catch(JSONException ex) {
            System.out.print(ex);
        }

        String[] statNames = {
                "BruteForce", "Rootkit", "ZeroDay", "DDOS", "Firewall", "IPS", "HDS",
                "VPN", "SDK", "Scan", "Antivirus", "RAM", "Keylogger", "Cryptography"
        };
        int count = 0;
        for(String name: statNames) {
            try {
                this.stats.put(name, (String) jInfo.get(name));
            } catch(JSONException ex) {
                System.out.print(ex);
            }
        }

        try {
            this.money = (String) jInfo.get("Money");
            this.premium = (String) jInfo.get("Premium");
            this.rank = Integer.toString((int)jInfo.get("Rank"));
            this.reputation = (String) jInfo.get("Reputation");
        } catch(JSONException ex) {
            System.out.print(ex);
        }
    }

    public String[][] refreshIPs() {
        String[][] ips = new String[9][3];
        JSONArray jArr = new JSONArray();

        try {
            jArr = new JSONArray(request("V2/Remote/GetIPs", this.basicReq));
            for(int i=0; i<jArr.length(); i++) {
                JSONObject jObject = jArr.getJSONObject(i);

                ips[i][0] = (String) jObject.get("Username");
                ips[i][1] = (String) jObject.get("IP");
                ips[i][2] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            }
        } catch(JSONException ex) {
            System.out.print(ex);
        }
        return ips;
    }

    public String scan(String attack, String targetIP) {
        JSONObject info = this.basicReq;
        try {
            info.put("Target", targetIP);
        } catch(JSONException ex) {
            System.out.print(ex);
        }

        return request("V2/Remote/ScanIP", info);
    }

    public String zeroDay(String targetName) {
        JSONObject info = this.basicReq;
        try {
            info.put("Target", targetName);
        } catch(JSONException ex) {
            System.out.print(ex);
        }

        return request("V2/Attack/ZeroDayAttack", info);
    }

    public String upgradeSoftware(String software, int level) {
        JSONObject info = this.basicReq;
        try {
            info.put("Software", software);
            info.put("UpgradeBy", Integer.toString(level));
        } catch(JSONException ex) {
            System.out.print(ex);
        }

        return request("V2/Software/UpgradeSoftware", info);
    }
}

