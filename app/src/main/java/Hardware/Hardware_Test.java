package Hardware;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;
import android.telephony.TelephonyManager;

import android.hardware.usb.UsbManager;

import androidx.core.content.ContextCompat;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;

public class Hardware_Test {
    private MediaPlayer mp;

    public Hardware_Test() {

    }

    public int COM1_status() {
        return this.ReturnCOM1Result();
    }

    public int COM2_status() {
        return this.ReturnCOM2Result();
    }

    public int COM3_status() {
        return this.ReturnCOM3Result();
    }

    public int COM4_status() {
        return this.ReturnCOM4Result();
    }

    public int wifi_status(Context context) {
        int wifiState = -1;
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        if(wifiManager != null){
            wifiState = wifiManager.getWifiState();
        }
        return wifiState;
    }

    public boolean ethernet_network_status(Context context) {
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

    public boolean lte_network_status(Context context) {
        int simstatus;
        boolean result = false;
        if (context != null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simstatus = telephonyManager.getSimState();

            switch (simstatus) {
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    result = false;
                    break;
                case TelephonyManager.SIM_STATE_ABSENT:
                    result = false;
                    break;
                case TelephonyManager.SIM_STATE_READY:
                    result = true;
                    break;
                default:
                    result = false;
                    break;
            }
        }
        return result;
    }

    public boolean sd_status(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //External Storage Emulated
            if (Environment.isExternalStorageEmulated()){
                if (ContextCompat.getExternalFilesDirs(context, null).length > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean usb_status(Context context) {
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> usbdevicelist = manager.getDeviceList();

        Iterator<UsbDevice> deviceiterator = usbdevicelist.values().iterator();

        while (deviceiterator.hasNext()) {
            UsbDevice device = deviceiterator.next();
            //Log.i("System.out", "device name : " + device.getDeviceName());
            if (device.getDeviceName().length() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean MIO1_status() {
        if (this.ReturnMIO1Result() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean USB1_status() {
        if (this.ReturnUSB1Result() == 0) {
            return false;
        } else {
            return true;
        }
    }

    static {
        System.loadLibrary("hardware_lib");
    }

    public native int ReturnCOM1Result();
    public native int ReturnCOM2Result();
    public native int ReturnCOM3Result();
    public native int ReturnCOM4Result();
    public native int ReturnMIO1Result();
    public native int ReturnUSB1Result();

    public native int OpenGPIO(int gpio_num);
    public native int CloseGPIO(int gpio_num);
    public native int SetGPIODirectory(int gpio_num, String[] directory);
    public native int GetGPIOValue(int gpio_num);
    public native int SetGPIOValue(int gpio_num, int value);
}
