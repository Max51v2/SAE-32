package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class IP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);


        //_____________Variables/Objets_______________
        View MainBannerButtonHome = findViewById(R.id.MainBannerButtonHome);
        View IPButtonIP = findViewById(R.id.IPButtonIP);
        TextView IPNetworkAddressBox = findViewById(R.id.IPNetworkAddressBox);
        TextView IPBroadcastAddressBox = findViewById(R.id.IPBroadcastAddressBox);
        TextView IPRangeBox = findViewById(R.id.IPRangeBox);
        TextView IPAvailableAddressBox = findViewById(R.id.IPAvailableAddressBox);
        final EditText IPBoxIP =  (EditText) findViewById(R.id.IPBoxIP);
        final EditText IPBoxMask =  (EditText) findViewById(R.id.IPBoxMask);
        //____________________________________________


        //Changement d'activité (IP)
        MainBannerButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(IP.this, MainActivity.class));
            }
        });


        IPButtonIP.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                //Récupération du champ IP
                String address = String.valueOf(IPBoxIP.getText());
                String mask = String.valueOf(IPBoxMask.getText());
                IPBoxIP.setText("");
                IPBoxMask.setText("");
                IPBoxIP.requestFocus();
                IPBoxMask.requestFocus();

                //Envoi des valeurs dans le layout
                IPCalculator ip1;
                try {
                    ip1 = new IPCalculator(address, mask);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                IPNetworkAddressBox.setText(ip1.NetworkAddress());
                IPBroadcastAddressBox.setText(ip1.BroadcastAddress());
                IPRangeBox.setText(ip1.FirstAddress() + "-" + ip1.LastAddress());
                IPAvailableAddressBox.setText(ip1.NumberOfAddress());
            }
        });
    }
}