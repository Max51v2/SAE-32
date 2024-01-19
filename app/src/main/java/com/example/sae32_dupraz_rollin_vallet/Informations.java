package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class  Informations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);

        View InformationsBannerButtonHome = findViewById(R.id.InformationsBannerButtonHome);

        //Changement d'activité (IP)
        InformationsBannerButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Informations.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });
    }
}