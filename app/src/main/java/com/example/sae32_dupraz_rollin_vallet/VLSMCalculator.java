package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class VLSMCalculator extends IPCalculator {
    private String SubnetList;
    private String address;
    private String mask;
    private String lang;
    private String result;
    private Boolean IPExceeded;
    private String addrBack;

    private ArrayList<Integer> ArraySubnetList;
    private ArrayList<Integer> ArrayMaskList;
    private ArrayList<String> ArrayNetworkList;

    public VLSMCalculator(String address, String mask, String SubnetList, String lang){
        super(address,mask);
        this.mask = Getmask(mask);
        this.address = NetworkAddressAlt(address,this.mask);
        this.SubnetList = SubnetList;
        this.lang = lang;
        this.ArraySubnetList = new ArrayList<>();
        this.ArrayMaskList = new ArrayList<>();
        this.ArrayNetworkList = new ArrayList<>();
        this.IPExceeded = false;

        //Ajout du nombre de chaque sous-réseau dans le tableau
        while(this.SubnetList.contains("/")){
            this.ArraySubnetList.add(Integer.valueOf(this.SubnetList.substring(0,this.SubnetList.indexOf("/"))));
            this.SubnetList = this.SubnetList.substring(this.SubnetList.indexOf("/")+1, this.SubnetList.length());
        }
        this.ArraySubnetList.add(Integer.valueOf(this.SubnetList));

        //tri ascendant du tableau
        Collections.sort(this.ArraySubnetList);
        Collections.reverse(this.ArraySubnetList);

        //Création du tableau contenant le masque de chaque sous-réseau
        int compteur=2;
        for(int i = 0; i < this.ArraySubnetList.size(); i++){
            compteur = 2;
            for(int a = 2; (int) (Math.pow(2,a)-2) <this.ArraySubnetList.get(i); a++){
                compteur +=1;
            }
            this.ArrayMaskList.add(i,(32-compteur));
        }

        //VLSM
        int byte1;
        int byte2;
        int byte3;
        int byte4;
        int beginIndex;
        int endIndex;
        this.addrBack = this.address;
        for(int i = 0; i < ArrayMaskList.size(); i++){
            if(i == 0){
                ArrayNetworkList.add(this.address);
            }
            else{
                beginIndex = 0;
                endIndex =this.address.indexOf(".");

                byte1 = Integer.valueOf(this.address.substring(beginIndex,endIndex));

                this.address = this.address.substring(endIndex+1,this.address.length());
                endIndex =this.address.indexOf(".");
                byte2 = Integer.valueOf(this.address.substring(beginIndex,endIndex));

                this.address = this.address.substring(endIndex+1,this.address.length());
                endIndex =this.address.indexOf(".");
                byte3 = Integer.valueOf(this.address.substring(beginIndex,endIndex));

                this.address = this.address.substring(endIndex+1,this.address.length());
                endIndex =this.address.length();
                byte4 = Integer.valueOf(this.address.substring(beginIndex,endIndex));

                if ( 0 < ArrayMaskList.get(i-1) && ArrayMaskList.get(i-1) < 8){
                    byte1 += Math.pow(2,8-ArrayMaskList.get(i-1)%8);
                }
                if ( 8 <= ArrayMaskList.get(i-1) && ArrayMaskList.get(i-1) < 16){
                    byte2 += Math.pow(2,8-ArrayMaskList.get(i-1)%8);
                }
                if ( 16 <= ArrayMaskList.get(i-1) && ArrayMaskList.get(i-1) < 24){
                    byte3 += Math.pow(2,8-ArrayMaskList.get(i-1)%8);
                }
                if ( 24 <= ArrayMaskList.get(i-1) && ArrayMaskList.get(i-1) < 32){
                    byte4 += Math.pow(2,8-ArrayMaskList.get(i-1)%8);
                }

                //Incrémente l'octet supérieur si l'octet actuel dépasse la valeur 255
                while (byte2 > 255 || byte3 > 255 || byte4 > 255){
                    if (byte2 > 255){
                        byte2 -= 256;
                        byte1 += 1;
                    }
                    if (byte3 > 255){
                        byte3 -= 256;
                        byte2 += 1;
                    }
                    if (byte4 > 255){
                        byte4 -= 256;
                        byte3 += 1;
                    }
                }

                ArrayNetworkList.add(byte1+"."+byte2+"."+byte3+"."+byte4);
                this.address = byte1+"."+byte2+"."+byte3+"."+byte4;
            }
        }
        this.address = this.addrBack;

        //Test si on a pas dépassé le bloc d'adresses alloué
        address = LastAddressAlt(this.address,this.mask);
        beginIndex = 0;
        endIndex =address.indexOf(".");
        byte1 = Integer.valueOf(address.substring(beginIndex,endIndex));
        address = address.substring(endIndex+1,address.length());
        endIndex =address.indexOf(".");
        byte2 = Integer.valueOf(address.substring(beginIndex,endIndex));
        address = address.substring(endIndex+1,address.length());
        endIndex =address.indexOf(".");
        byte3 = Integer.valueOf(address.substring(beginIndex,endIndex));
        address = address.substring(endIndex+1,address.length());
        endIndex =address.length();
        byte4 = Integer.valueOf(address.substring(beginIndex,endIndex));

        address=ArrayNetworkList.get(ArrayNetworkList.size()-1);
        beginIndex = 0;
        endIndex =address.indexOf(".");
        int byte1AddrResult = Integer.valueOf(address.substring(beginIndex,endIndex));
        address = address.substring(endIndex+1,address.length());
        endIndex =address.indexOf(".");
        int byte2AddrResult = Integer.valueOf(address.substring(beginIndex,endIndex));
        address = address.substring(endIndex+1,address.length());
        endIndex =address.indexOf(".");
        int byte3AddrResult = Integer.valueOf(address.substring(beginIndex,endIndex));
        address = address.substring(endIndex+1,address.length());
        endIndex =address.length();
        int byte4AddrResult = Integer.valueOf(address.substring(beginIndex,endIndex));

        if (byte1AddrResult>byte1 || byte2AddrResult>byte2 || byte3AddrResult>byte3 || byte4AddrResult>byte4){
            this.IPExceeded = true;
        }
        //Si il y'a un SR, l'ajout n'est pas fait donc on vérifie si le nombre d'adresses dépasse ce qui est demandé par l'utilisateur
        if(ArraySubnetList.size() == 1){
            if(ArraySubnetList.get(0) > (int) Math.pow(2, 32 - Double.parseDouble(this.mask))-2){
                this.IPExceeded = true;
            }
        }
        Log.d("tagtest", ArraySubnetList.get(0)+">"+((int) Math.pow(2, 32 - Double.parseDouble(this.mask))-2));


        //Stock le résultat dans result
        String result = "";
        if (IPExceeded){
            if (this.lang == "fr") {
                result = "Erreur : Il n'y a pas assez d'adresses dans le bloc";
            }
            if (this.lang == "en"){
                result = "Error : There isn't enough addresses in this bloc";
            }
        }
        else {
            if (this.lang == "fr") {
                for (int i = 0; i < ArrayNetworkList.size(); i++) {
                    result += "Sous-réseau : " + (i + 1) + " (" + ArraySubnetList.get(i) + " adresses)\n";
                    result += "Adresse Réseau : " + ArrayNetworkList.get(i) + "/" + ArrayMaskList.get(i) + "\n";
                    result += "Adresse Broadcast : " + BroadcastAddressAlt(ArrayNetworkList.get(i), String.valueOf(ArrayMaskList.get(i))) + "\n";
                    result += "Première Adresse : " + FirstAddressAlt(ArrayNetworkList.get(i), String.valueOf(ArrayMaskList.get(i))) + "\n";
                    result += "Dernière Adresse : " + LastAddressAlt(ArrayNetworkList.get(i), String.valueOf(ArrayMaskList.get(i))) + "\n";
                }
            }
            if (this.lang == "en"){
                for (int i = 0; i < ArrayNetworkList.size(); i++) {
                    result += "Subnet : " + (i + 1) + " (" + ArraySubnetList.get(i) + " addresses)\n";
                    result += "Network Address : " + ArrayNetworkList.get(i) + "/" + ArrayMaskList.get(i) + "\n";
                    result += "Broadcast Address : " + BroadcastAddressAlt(ArrayNetworkList.get(i), String.valueOf(ArrayMaskList.get(i))) + "\n";
                    result += "First Address : " + FirstAddressAlt(ArrayNetworkList.get(i), String.valueOf(ArrayMaskList.get(i))) + "\n";
                    result += "Last Address : " + LastAddressAlt(ArrayNetworkList.get(i), String.valueOf(ArrayMaskList.get(i))) + "\n";
                }
            }
        }
        this.result = result;
    }

    //renvoi le résultat
    public String getResult(){
        return this.result;
    }
}