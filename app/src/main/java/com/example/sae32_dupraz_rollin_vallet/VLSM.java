package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class VLSM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlsm);


        //_____________Variables/Objets_______________
        View VLSMBannerButtonHome = findViewById(R.id.VLSMBannerButtonHome);
        View VLSMButtonIP = findViewById(R.id.VLSMButtonIP);
        TextView VLSMTextAnswer = findViewById(R.id.VLSMTextAnswer);
        final EditText VLSMBoxIP =  (EditText) findViewById(R.id.VLSMBoxIP);
        final EditText VLSMBoxMask =  (EditText) findViewById(R.id.VLSMBoxMask);
        final EditText VLSMBoxSize =  (EditText) findViewById(R.id.VLSMBoxSize);
        //____________________________________________


        //Changement d'activité (Main)
        VLSMBannerButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(VLSM.this, MainActivity.class));
            }
        });

        VLSMButtonIP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Récupération du champ IP
                String address = String.valueOf(VLSMBoxIP.getText());
                String mask = String.valueOf(VLSMBoxMask.getText());
                String subnetlist = String.valueOf(VLSMBoxSize.getText());
                VLSMBoxIP.setText("");
                VLSMBoxMask.setText("");
                VLSMBoxSize.setText("");
                VLSMBoxIP.requestFocus();
                VLSMBoxMask.requestFocus();
                VLSMBoxSize.requestFocus();

                VLSMCalculator VLSM1 = new VLSMCalculator(address, mask, subnetlist);
                VLSMTextAnswer.setText(VLSM1.getResult());
            }
        });
    }
}