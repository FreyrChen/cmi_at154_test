package com.example.cmi_at154_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.widget.ImageView;

import Hardware.Hardware_Test;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Hardware_Test hardware_test = new Hardware_Test();

    private ImageView COM1_image, COM2_image, COM3_image,
            COM4_image, USB_image, Ethernet_image,
            WIFI_image, LTE_image, SD_image, Audio_image,
            MIO1_image, USB1_image;

    private Thread Audio_thread;
    private MediaPlayer mp;

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
        Audio_image = (ImageView)findViewById(R.id.Audio_Result);
        MIO1_image = (ImageView)findViewById(R.id.MIO1_Result);
        USB1_image = (ImageView)findViewById(R.id.USB1_Result);

        this.com_test();
        this.usb_test();
        this.ethernet_test();
        this.wifi_test();
        this.lte_test();
        this.sd_test();
        this.Audio_test();
        this.MIO1_test();
        this.USB1_test();

        Audio_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                audio_start();
            }
        });
        Audio_thread.start();
    }

    // override
    protected void onDestroy() {
        audio_stop();

        if (Audio_thread != null) {
            Audio_thread = null;
        }

        super.onDestroy();
    }

    private void com_test() {
        if ((hardware_test.COM1_status()) == 0) {
            COM1_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        } else {
            COM1_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        }

        if (hardware_test.COM2_status() == 0) {
            COM2_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        } else {
            COM2_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        }

        if ((hardware_test.COM3_status()) == 0) {
            COM3_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        } else {
            COM3_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        }

        if ((hardware_test.COM4_status()) == 0) {
            COM4_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        } else {
            COM4_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        }
    }

    private void wifi_test(){
        switch (hardware_test.wifi_status(MainActivity.this)) {
            case -1:
                Log.i("System.out", "wifi error");
                WIFI_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
                break;
            case 0:
                Log.i("System.out", "wifi closeing...");
                WIFI_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
                break;
            case 1:
                Log.i("System.out", "wifi closed...");
                WIFI_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
                break;
            case 2:
                Log.i("System.out", "wifi opening...");
                WIFI_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
                break;
            case 3:
                Log.i("System.out", "wifi opened...");
                WIFI_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
                break;
            default:
                WIFI_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
                break;
        }
    }

    private void ethernet_test() {
        if (hardware_test.ethernet_network_status(MainActivity.this)){
            Ethernet_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        } else {
            Ethernet_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        }
    }

    private void lte_test() {
        if (hardware_test.lte_network_status(MainActivity.this)){
            LTE_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        } else {
            LTE_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        }
    }

    private void sd_test() {
        if (hardware_test.sd_status(MainActivity.this)) {
            SD_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        } else {
            SD_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        }
    }

    private void usb_test() {
        if (hardware_test.usb_status(MainActivity.this)) {
            USB_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        } else {
            USB_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        }
    }

    private void Audio_test() {
            Audio_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
    }

    private void MIO1_test() {
        if (hardware_test.MIO1_status()) {
            MIO1_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        } else {
            MIO1_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        }
    }

    private void USB1_test() {
        if (hardware_test.USB1_status()) {
            USB1_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        } else {
            USB1_image.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_dialog));
        }
    }

    private void audio_start() {
        if (mp != null) {
            mp.release();
        }
        mp = MediaPlayer.create(MainActivity.this, R.raw.magicwaltz);
        //mp.prepareAsync();
        mp.start();
    }

    private void audio_stop() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }

    }
}
