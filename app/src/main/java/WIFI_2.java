import android.util.Log;

import com.example.sae32_dupraz_rollin_vallet.WIFI;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class WIFI_2 extends WIFI {
    public WIFI_2(){

    }

    public getLocalHost extends void AsyncTask {
        try {
            Log.d("test", "avant");
            InetAddress local = InetAddress.getLocalHost();
            Log.d("test", "après");
            WIFITextIP2.setText(local.toString());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
