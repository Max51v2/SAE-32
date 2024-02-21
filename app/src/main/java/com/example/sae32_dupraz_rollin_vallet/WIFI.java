package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;

public class WIFI extends AppCompatActivity {

    boolean run = false;
    SharedPreferences preferences2;
    SharedPreferences.Editor editor;


    //On arrete la boucle quand on met l'application en pause
    protected void onPause(){
        super.onPause();
        run = false;
    }


    //Retour en arrière
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(0, 0);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);


        //_____________Variables/Objets_______________
        View WIFIBannerButtonHome = findViewById(R.id.WIFIBannerButtonHome);
        View WIFITextInterface = findViewById(R.id.WIFITextInterface);
        View WIFIButtonInterface = findViewById(R.id.WIFIButtonInterface);
        TextView WIFITextInterface2 = findViewById(R.id.WIFITextInterface2);
        View WIFITextInterface3 = findViewById(R.id.WIFITextInterface3);
        View WIFIScrollView = findViewById(R.id.WIFIScrollView);
        View WIFIButtonInterfaceClose = findViewById(R.id.WIFIButtonInterfaceClose);
        TextView WIFITextSSID = findViewById(R.id.WIFITextSSID);
        View WIFIWarningBackground = findViewById(R.id.WIFIWarningBackground);
        View WIFIWarningLogo = findViewById(R.id.WIFIWarningLogo);
        TextView WIFIWarningText = findViewById(R.id.WIFIWarningText);
        View WIFIButtonWarningClose = findViewById(R.id.WIFIButtonWarningClose);
        TextView WIFITextStatus = findViewById(R.id.WIFITextStatus);
        TextView WIFITextStandard = findViewById(R.id.WIFITextStandard);
        TextView WIFITextRSSI = findViewById(R.id.WIFITextRSSI);
        TextView WIFITextQuality = findViewById(R.id.WIFITextQuality);
        TextView WIFITextTxRx = findViewById(R.id.WIFITextTxRx);
        TextView WIFITextWarningInfo = findViewById(R.id.WIFITextWarningInfo);
        preferences2=getSharedPreferences("Save_Main",MODE_PRIVATE);
        editor=preferences2.edit();
        //____________________________________________


        //Changement d'activité (Main)
        WIFIBannerButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                run = false;
                finish();
                overridePendingTransition(0, 0);
            }
        });


        //###################### Thread 1 ######################
        //Infos
        final String[] resultFr = {""};
        final String[] resultEn = {""};
        Thread LocalHost = new Thread() {
            public void run() {
                //Récupération des informations sur les interfaces
                try {
                    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                    ArrayList<String> interfaceName = new ArrayList<>();
                    ArrayList<String> interfaceAddress = new ArrayList<>();
                    ArrayList<String> interfacemask = new ArrayList<>();
                    //ArrayList<String> interfaceMAC = new ArrayList<>();
                    ArrayList<Integer> size = new ArrayList<>();
                    String address;

                    while (interfaces.hasMoreElements()) {
                        NetworkInterface networkInterface = interfaces.nextElement();
                        //Nom interfaces
                        interfaceName.add(networkInterface.getName().toString());


                        //adresses interfaces
                        address = networkInterface.getInterfaceAddresses().get(0).toString();
                        if (address.contains("%")) {
                            address = address.substring(address.indexOf("/") + 1, address.indexOf("%"));
                        } else {
                            address = address.substring(address.indexOf("/") + 1);
                            address = address.substring(0, address.indexOf("/"));
                        }
                        interfaceAddress.add(address);


                        //masque
                        address = networkInterface.getInterfaceAddresses().get(0).toString();
                        address = address.substring(address.indexOf("/") + 1);
                        address = address.substring(address.indexOf("/") + 1, address.indexOf("[") - 1);
                        interfacemask.add(address);


                        //@MAC (Nécéssite d'être ROOT)
                        //address = Arrays.toString(networkInterface.getHardwareAddress());
                        //Log.d("test", Arrays.toString(networkInterface.getHardwareAddress()));
                        //interfaceMAC.add(address);


                        //Seconde adresse
                        if (networkInterface.getInterfaceAddresses().size() > 1) {
                            //adresses interfaces
                            address = networkInterface.getInterfaceAddresses().get(1).toString();
                            if (address.contains("%")) {
                                address = address.substring(address.indexOf("/") + 1, address.indexOf("%"));
                            } else {
                                address = address.substring(address.indexOf("/") + 1);
                                address = address.substring(0, address.indexOf("/"));
                            }
                            interfaceAddress.add(address);


                            //masque
                            address = networkInterface.getInterfaceAddresses().get(1).toString();
                            address = address.substring(address.indexOf("/") + 1);
                            address = address.substring(address.indexOf("/") + 1, address.indexOf("[") - 1);
                            interfacemask.add(address);
                            interfaceName.add("placeholder");


                            //@MAC
                            //address = Arrays.toString(networkInterface.getHardwareAddress());
                            //interfaceMAC.add(address);

                            size.add(2);
                        }
                        else {
                            size.add(1);
                        }
                    }


                    //On compte le nombre d'adresses
                    int iMax = 0;
                    for (int i = 0; i < size.size(); i++) {
                        iMax += size.get(i);
                    }


                    //Récupération des résultats en englais et français
                    for (int i = 0; i < iMax; i++) {
                        if (interfaceName.get(i).contains("placeholder")) {
                            resultFr[0] += "Adresse n°2 : \n=> " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                        } else {
                            resultFr[0] += "Interface : " + interfaceName.get(i).toString() + " \n";
                            //resultFr[0] += "Adresse MAC : " + interfaceMAC.get(i).toString() + " \n";
                            if (interfaceName.get(i + 1).contains("placeholder") && i < iMax - 1) {
                                resultFr[0] += "Adresse n°1 : \n=> " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n";
                            } else {
                                resultFr[0] += "Adresse : \n=> " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                            }
                        }

                        if (interfaceName.get(i).contains("placeholder")) {
                            resultEn[0] += "Address n°2 : \n=> " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                        } else {
                            resultEn[0] += "Interface : " + interfaceName.get(i).toString() + " \n";
                            //resultEn[0] += "MAC Address : " + interfaceMAC.get(i).toString() + " \n";
                            if (interfaceName.get(i + 1).contains("placeholder") && i < iMax - 1) {
                                resultEn[0] += "Address n°1 : \n=> " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n";
                            } else {
                                resultEn[0] += "Address : \n=> " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                            }
                        }
                    }
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        //###################### Fin Thread 1 ######################


        //###################### Thread 2 ######################
        Thread LocalHost2 = new Thread() {
            public void run() {
                //Langue
                Resources resources = getResources();
                Configuration configuration = resources.getConfiguration();
                configuration.getLocales();
                String lang = "";
                if (configuration.getLocales().toString().contains("[en")) {
                    lang = "en";
                }
                if (configuration.getLocales().toString().contains("[fr")) {
                    lang = "fr";
                }

                while (run) {
                    //Informations WIFI
                    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    WifiInfo infos = wifi.getConnectionInfo();


                    //SSID
                    String SSID = infos.getSSID();
                    SSID = SSID.substring(1, SSID.length() - 1);


                    //Etat WIFI
                    String str = "";
                    if (wifi.getWifiState() == 1) {
                        if (lang.equals("fr")) {
                            str = "Status : Desactivé";
                        }
                        if (lang.equals("en")) {
                            str = "Status : Disabled";
                        }
                    }
                    if (wifi.getWifiState() == 3) {
                        if (lang.equals("fr")) {
                            str = "Status : Activé";
                        }
                        if (lang.equals("en")) {
                            str = "Status : Enabled";
                        }
                    }


                    String connectionInfo = wifi.getConnectionInfo().toString();


                    //Stadard WIFI
                    String standard = connectionInfo.substring(connectionInfo.indexOf("standard:")+10,connectionInfo.indexOf("standard:")+11);
                    if (lang.equals("fr")) {
                        standard = "Standard : WIFI "+standard;
                    }
                    if (lang.equals("en")) {
                        standard = "Standard : WIFI "+standard;
                    }


                    //RSSI
                    String rssi = connectionInfo.substring(connectionInfo.indexOf("RSSI:")+6,connectionInfo.indexOf("RSSI:")+9);
                    if (rssi.contains(",")){
                        rssi = rssi.substring(0,rssi.indexOf(",")-1);
                    }
                    if (lang.equals("fr")) {
                        rssi = "RSSI : "+rssi+" dBm";
                    }
                    if (lang.equals("en")) {
                        rssi = "RSSI : "+rssi+" dBm";
                    }


                    //Qualité WIFI
                    //Broken (affiche 60 constamment)
                    //String score = connectionInfo.substring(connectionInfo.indexOf("score:")+7,connectionInfo.indexOf("score:")+9);
                    //if (score.contains(",")){
                    //    score = score.substring(0,score.indexOf(",")-1);
                    //}
                    //if (lang.equals("fr")) {
                    //    score = "Qualité : "+score+"%";
                    //}
                    //if (lang.equals("en")) {
                    //    score = "Quality : "+score+"%";
                    //}

                    //Fix
                    String rssiTemp = rssi.substring(7,rssi.length()-4);
                    String score = "";
                    if (Integer.valueOf(rssiTemp) > -50){
                        if (lang.equals("fr")) {
                            score = "Qualité : Excellent";
                        }
                        if (lang.equals("en")) {
                            score = "Quality : Excellent";
                        }
                    }
                    if (Integer.valueOf(rssiTemp) <= -50 && Integer.valueOf(rssiTemp) > -60){
                        if (lang.equals("fr")) {
                            score = "Qualité : Bonne";
                        }
                        if (lang.equals("en")) {
                            score = "Quality : Good";
                        }
                    }
                    if (Integer.valueOf(rssiTemp) <= -60 && Integer.valueOf(rssiTemp) > -70){
                        if (lang.equals("fr")) {
                            score = "Qualité : Moyen";
                        }
                        if (lang.equals("en")) {
                            score = "Quality : Fair";
                        }
                    }
                    if (Integer.valueOf(rssiTemp) <= -70){
                        if (lang.equals("fr")) {
                            score = "Qualité : Faible";
                        }
                        if (lang.equals("en")) {
                            score = "Quality : Weak";
                        }
                    }


                    //Tx et Rx
                    String Tx = connectionInfo.substring(connectionInfo.indexOf("Tx Link speed:")+15,connectionInfo.length()-1);
                    Tx = Tx.substring(0,Tx.indexOf(","));
                    String Rx = connectionInfo.substring(connectionInfo.indexOf("Rx Link speed:")+15,connectionInfo.length()-1);
                    Rx = Rx.substring(0,Rx.indexOf(","));
                    String speed = null;
                    if (lang.equals("fr")) {
                        speed = "Tx/Rx : "+Tx+"/"+Rx;
                    }
                    if (lang.equals("en")) {
                        speed = "Tx/Rx : "+Tx+"/"+Rx;
                    }


                    //Mise à jour des éléments de l'analyseur WIFI dans le Thread principal
                    String finalStr = str;
                    String finalSSID = SSID;
                    String finalStandard = standard;
                    String finalRssi = rssi;
                    String finalScore = score;
                    String finalSpeed = speed;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            WIFITextSSID.setText("SSID : " + finalSSID);
                            WIFITextStatus.setText(finalStr);
                            WIFITextStandard.setText(finalStandard);
                            WIFITextRSSI.setText(finalRssi);
                            WIFITextQuality.setText(finalScore);
                            WIFITextTxRx.setText(finalSpeed);
                        }
                    });


                    //Actualisation toutes les 100 ms
                    synchronized (this) {
                        try {
                            this.wait(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }

        };
        //###################### Fin Thread 2 ######################


        //Démarrage Thread 1 (Configuration des interfaces)
        LocalHost.start();


        //Démarrage Thread 2 (Analyseur WIFI)
        run = true;
        LocalHost2.start();


        //Langue
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.getLocales();
        String lang = "";
        if (configuration.getLocales().toString().contains("[en")) {
            lang = "en";
        }
        if (configuration.getLocales().toString().contains("[fr")) {
            lang = "fr";
        }


        //Changement de langue si l'activité est reconstruite (changement d'orientation ou dimensions)
        //Enregistrement de la langue actuelle
        Locale localeEn = new Locale("en");
        Locale localeFr = new Locale("fr");

        //Changement de la langue
        if (preferences2.getString("LangAct", "").equals("fr") && lang.equals("en")){
            configuration.setLocale(localeFr);
            recreate();
        } else if (preferences2.getString("LangAct", "").equals("en") && lang.equals("fr")) {
            configuration.setLocale(localeEn);
            recreate();
        }


        //Affichage des informations sur les interfaces lors du click sur le bouton des informations de configuration
        //Ouverture
        WIFIButtonInterface.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WIFITextInterface.setVisibility(View.INVISIBLE);
                WIFIButtonInterface.setVisibility(View.INVISIBLE);
                WIFITextInterface2.setVisibility(View.VISIBLE);
                WIFIButtonInterfaceClose.setVisibility(View.VISIBLE);
                WIFITextInterface3.setVisibility(View.VISIBLE);
                WIFIScrollView.setVisibility(View.VISIBLE);
                WIFITextSSID.setVisibility(View.INVISIBLE);
                WIFITextStatus.setVisibility(View.INVISIBLE);
                WIFITextStandard.setVisibility(View.INVISIBLE);
                WIFITextRSSI.setVisibility(View.INVISIBLE);
                WIFITextQuality.setVisibility(View.INVISIBLE);
                WIFITextTxRx.setVisibility(View.INVISIBLE);


                //Définission du contenu de la page
                Resources resources = getResources();
                Configuration configuration = resources.getConfiguration();
                configuration.getLocales();
                String lang = "";
                if (configuration.getLocales().toString().contains("[en")) {
                    WIFITextInterface2.setText(resultEn[0]);
                }
                if (configuration.getLocales().toString().contains("[fr")) {
                    WIFITextInterface2.setText(resultFr[0]);
                }
            }
        });

        //Fermeture
        WIFIButtonInterfaceClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WIFITextInterface.setVisibility(View.VISIBLE);
                WIFIButtonInterface.setVisibility(View.VISIBLE);
                WIFITextInterface2.setVisibility(View.INVISIBLE);
                WIFIButtonInterfaceClose.setVisibility(View.INVISIBLE);
                WIFITextInterface3.setVisibility(View.INVISIBLE);
                WIFIScrollView.setVisibility(View.INVISIBLE);
                WIFITextSSID.setVisibility(View.VISIBLE);
                WIFITextStatus.setVisibility(View.VISIBLE);
                WIFITextStandard.setVisibility(View.VISIBLE);
                WIFITextRSSI.setVisibility(View.VISIBLE);
                WIFITextQuality.setVisibility(View.VISIBLE);
                WIFITextTxRx.setVisibility(View.VISIBLE);

                WIFITextInterface2.setText("");
            }
        });


        //Warnings
        //Définission du contenu de l'avertissement
        //GPS
        Context context = this;
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        String str = "";
        if (isGpsEnabled == false) {
            if (lang.equals("fr")) {
                str = "Veuillez activer la localisation sur l'appareil \n\n";
            }
            if (lang.equals("en")) {
                str = "Please enable location on the device \n\n";
            }
        }

        //Localisation
        if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED | checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if (lang.equals("fr")) {
                str += "Veuillez redémarrer l'application et activer la localisation précise";
            }
            if (lang.equals("en")) {
                str += "Please restart the app and enable fine location";
            }
        }
        WIFIWarningText.setText(str);


        //Affichage ou non de l'avertissement
        //Affiché
        if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED | checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED | isGpsEnabled == false) {
            WIFIWarningBackground.setVisibility(View.VISIBLE);
            WIFIButtonInterface.setVisibility(View.INVISIBLE);
            WIFITextInterface.setVisibility(View.INVISIBLE);
            WIFITextSSID.setVisibility(View.INVISIBLE);
            WIFIWarningLogo.setVisibility(View.VISIBLE);
            WIFIWarningText.setVisibility(View.VISIBLE);
            WIFITextStatus.setVisibility(View.INVISIBLE);
            WIFITextStandard.setVisibility(View.INVISIBLE);
            WIFITextRSSI.setVisibility(View.INVISIBLE);
            WIFITextQuality.setVisibility(View.INVISIBLE);
            WIFITextTxRx.setVisibility(View.INVISIBLE);
            WIFIButtonWarningClose.setVisibility(View.VISIBLE);
            WIFITextWarningInfo.setVisibility(View.VISIBLE);
        }

        //Masqué
        else {
            WIFIWarningBackground.setVisibility(View.INVISIBLE);
            WIFIButtonInterface.setVisibility(View.VISIBLE);
            WIFITextInterface.setVisibility(View.VISIBLE);
            WIFITextSSID.setVisibility(View.VISIBLE);
            WIFIWarningLogo.setVisibility(View.INVISIBLE);
            WIFIWarningText.setVisibility(View.INVISIBLE);
            WIFITextStatus.setVisibility(View.VISIBLE);
            WIFITextStandard.setVisibility(View.VISIBLE);
            WIFITextRSSI.setVisibility(View.VISIBLE);
            WIFITextQuality.setVisibility(View.INVISIBLE);
            WIFITextQuality.setVisibility(View.VISIBLE);
            WIFITextTxRx.setVisibility(View.VISIBLE);
            WIFIButtonWarningClose.setVisibility(View.INVISIBLE);
            WIFITextWarningInfo.setVisibility(View.INVISIBLE);
        }


        //Fermeture de l'avertissement
        WIFIButtonWarningClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WIFIWarningBackground.setVisibility(View.INVISIBLE);
                WIFIButtonInterface.setVisibility(View.VISIBLE);
                WIFITextInterface.setVisibility(View.VISIBLE);
                WIFITextSSID.setVisibility(View.VISIBLE);
                WIFIWarningLogo.setVisibility(View.INVISIBLE);
                WIFIWarningText.setVisibility(View.INVISIBLE);
                WIFITextStatus.setVisibility(View.VISIBLE);
                WIFITextStandard.setVisibility(View.VISIBLE);
                WIFITextRSSI.setVisibility(View.VISIBLE);
                WIFITextQuality.setVisibility(View.INVISIBLE);
                WIFITextQuality.setVisibility(View.VISIBLE);
                WIFITextTxRx.setVisibility(View.VISIBLE);
                WIFIButtonWarningClose.setVisibility(View.INVISIBLE);
                WIFITextWarningInfo.setVisibility(View.INVISIBLE);
            }
        });


    }
    }