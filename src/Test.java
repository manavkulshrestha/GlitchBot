import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {
    public static void main(String[] args) {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

        System.out.print(timeStamp);
    }
}
