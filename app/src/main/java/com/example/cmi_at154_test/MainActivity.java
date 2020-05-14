package com.example.cmi_at154_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import android.widget.ImageView;

import Hardware.Hardware_Test;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Hardware_Test hardware_test = new Hardware_Test();

    private ImageView COM1_image, COM2_image, COM3_image,
            COM4_image, USB_image, Ethernet_image,
            WIFI_image, LTE_image, SD_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        COM1_image = (ImageView)findViewById(R.id.COM1_Result);
        COM2_image = (ImageView)findViewById(R.id.COM2_Result);
        COM3_image = (ImageView)findViewById(R.id.COM3_Result);
        COM4_image = (ImageView)findViewById(R.id.COM4_Result);
        USB_image = (ImageView)findViewById(R.id.USB_Result);
        Ethernet_image = (ImageView)findViewById(R.id.Ethernet_Result);
        WIFI_image = (ImageView)findViewById(R.id.WIFI_Result);
        LTE_image = (ImageView)findViewById(R.id.LTE_Result);
        SD_image = (ImageView)findViewById(R.id.SD_Result);

        this.com_test();
        this.wifi_test();
        this.ethernet_test();
    }

    private void com_test() {
        if ((ReturnCOM1Result()) == 0) {
            COM1_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        } else {
            COM1_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        }

        if ((ReturnCOM2Result()) == 0) {
            COM2_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        } else {
            COM2_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        }

        if ((ReturnCOM3Result()) == 0) {
            COM3_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        } else {
            COM3_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        }

        if ((ReturnCOM4Result()) == 0) {
            COM4_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        } else {
            COM4_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        }
    }

    private void wifi_test(){
        switch (hardware_test.wifi_status(MainActivity.this)) {
            case -1:
                Log.i("System.out", "wfi error");
                WIFI_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
                break;
            case 0:
                Log.i("System.out", "wfi closeing...");
                WIFI_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
                break;
            case 1:
                Log.i("System.out", "wfi closed...");
                WIFI_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
                break;
            case 2:
                Log.i("System.out", "wfi opening...");
                WIFI_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
                break;
            case 3:
                Log.i("System.out", "wfi opened...");
                WIFI_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
                break;
            default:
                break;
        }
    }

    private void ethernet_test() {
        if (hardware_test.mobile_network_status(MainActivity.this)){
            Ethernet_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        } else {
            Ethernet_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        }
    }

    static {
        System.loadLibrary("hardware_lib");
    }

    public native int ReturnCOM1Result();
    public native int ReturnCOM2Result();
    public native int ReturnCOM3Result();
    public native int ReturnCOM4Result();
    public native int ReturnGPSResult();
    public native int ReturnWIFIResult();
    public native int ReturnUSBResult();
    public native int Return4GResult();
}
