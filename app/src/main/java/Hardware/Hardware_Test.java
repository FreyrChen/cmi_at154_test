package Hardware;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.security.AccessControlContext;
import java.security.AccessController;

public class Hardware_Test {

    public Hardware_Test() {

    }

    public int wifi_status(Context context) {
        int wifiState = -1;
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        if(wifiManager != null){
            wifiState = wifiManager.getWifiState();
        }
        return wifiState;
    }

    public boolean mobile_network_status(Context context) {
        NetworkInfo.State eth_status;
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo[] networkInfos = manager.getAllNetworkInfo();
            if (networkInfos != null) {
                for (int i = 0; i < networkInfos.length; i++) {
                    if (networkInfos[i].getType() == ConnectivityManager.TYPE_ETHERNET) {
                        eth_status =  networkInfos[i].getState();
                        if (eth_status == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
