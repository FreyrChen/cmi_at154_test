package Hardware;

import android.content.Context;
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

    public int gpio_num(int bank, int gpio) {
        if (bank == 0) {
            return gpio;
        } else {
            return  (bank * 32 + gpio - 12);
        }
    }
    public boolean MIO1_status() {
        /*
        boolean result = true;
        int gpio1_d = gpio_num(2, 3),
                gpio2_u = gpio_num(8, 1),
                gpio3_d = gpio_num(2, 1),
                gpio4_u = gpio_num(8, 7),
                gpio5_d = gpio_num(8, 9),
                gpio6_d = gpio_num(2, 4),
                gpio7_d = gpio_num(2, 2),
                gpio8_d = gpio_num(2, 0),
                gpio9_d = gpio_num(8, 6),
                gpio10_u = gpio_num(7, 17),
                gpio11_u = gpio_num(7, 18),
                gpio12_d = gpio_num(8, 8),
                gpio13_u = gpio_num(0, 8),
                gpio14_u = gpio_num(0, 7),
                i2c3_sda = gpio_num(2, 17),
                i2c3_scl = gpio_num(2, 16),
                spi1_clk = gpio_num(7, 12),
                spi1_cs = gpio_num(7, 13),
                spi1_rxd = gpio_num(7, 14),
                spi1_txd = gpio_num(7, 15),
                gpios[] = {gpio1_d, gpio2_u, gpio3_d, gpio4_u, gpio5_d, gpio6_d,
                gpio7_d, gpio8_d, gpio9_d, gpio10_u, gpio11_u, gpio12_d, gpio13_u,
                gpio14_u, i2c3_sda, i2c3_scl, spi1_clk, spi1_cs, spi1_rxd, spi1_txd},
                i = 0;

        for (i = 0; i < gpios.length; i++) {
            this.OpenGPIO(gpios[i]);
        }

        for (i = 0; i < gpios.length; i+=2) {
            this.SetGPIODirection(gpios[i], "output");
        }

        for (i = 1; i < gpios.length; i+=2) {
            this.SetGPIODirection(gpios[i], "input");
        }

        for (i = 0; i < gpios.length; i+=2) {
            this.SetGPIOValue(gpios[i], 1);
        }

        for (i = 1; i < gpios.length; i+=2) {
            if (this.GetGPIOValue(gpios[i]) != 1) {
                result = false;
            }
        }

        for (i = 0; i < gpios.length; i++) {
            this.CloseGPIO(gpios[i]);
        }

        return result;
         */
        return  true;
    }

    public boolean USB1_status() {
        return true;
    }

    static {
        System.loadLibrary("hardware_lib");
    }

    public native int ReturnCOM1Result();
    public native int ReturnCOM2Result();
    public native int ReturnCOM3Result();
    public native int ReturnCOM4Result();

    public native int OpenGPIO(int gpio_num);
    public native int CloseGPIO(int gpio_num);
    public native int SetGPIODirection(int gpio_num, String directory);
    public native int GetGPIOValue(int gpio_num);
    public native int SetGPIOValue(int gpio_num, int value);
}
