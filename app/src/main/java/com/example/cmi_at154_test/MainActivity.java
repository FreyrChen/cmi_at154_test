package com.example.cmi_at154_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView COM1_image, COM2_image, COM3_image,
            COM4_image, USB_image, BT_image,
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
        BT_image = (ImageView)findViewById(R.id.BT_Result);
        WIFI_image = (ImageView)findViewById(R.id.WIFI_Result);
        LTE_image = (ImageView)findViewById(R.id.LTE_Result);
        SD_image = (ImageView)findViewById(R.id.SD_Result);

        this.setResult_image();
    }

    private void setResult_image() {
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
