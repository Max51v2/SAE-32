package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.widget.TextView;

public class  Informations extends AppCompatActivity {


    //Retour en arrière
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(0, 0);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);


        //_____________Variables/Objets_______________
        TextView InformationsMail1 = findViewById(R.id.InformationsMail1);
        TextView InformationsMail2 = findViewById(R.id.InformationsMail2);
        View InformationsBannerButtonHome = findViewById(R.id.InformationsBannerButtonHome);
        //____________________________________________


        //Changement d'activité (IP)
        InformationsBannerButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });


        //Liens adresses mail
        InformationsMail1.setMovementMethod(LinkMovementMethod.getInstance());
        InformationsMail2.setMovementMethod(LinkMovementMethod.getInstance());
    }

}