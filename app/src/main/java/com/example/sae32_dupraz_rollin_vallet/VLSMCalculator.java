package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

public class VLSMCalculator extends IPCalculator {
    private String SubnetList;
    private String address;
    private String mask;

    private ArrayList<Integer> ArraySubnetList;
    private ArrayList<Integer> ArrayMaskList;
    private ArrayList<String> ArrayNetworkList;

    public VLSMCalculator(String address, String mask, String SubnetList){
        super(address,mask);
        this.mask = Getmask(mask);
        this.address = NetworkAddressAlt(address,this.mask);
        this.SubnetList = SubnetList;
        this.ArraySubnetList = new ArrayList<>();
        this.ArrayMaskList = new ArrayList<>();
        this.ArrayNetworkList = new ArrayList<>();

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
        String addrBack = this.address;
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

                if ( 0 < ArrayMaskList.get(i) && ArrayMaskList.get(i) < 8){
                    byte1 += Math.pow(2,8-ArrayMaskList.get(i)%8);
                }
                if ( 8 <= ArrayMaskList.get(i) && ArrayMaskList.get(i) < 16){
                    byte2 += Math.pow(2,8-ArrayMaskList.get(i)%8);
                    if (byte2 > 255){
                        byte2 -= 256;
                        byte1 += 1;
                    }
                }
                if ( 16 <= ArrayMaskList.get(i) && ArrayMaskList.get(i) < 24){
                    byte3 += Math.pow(2,8-ArrayMaskList.get(i-1)%8);
                    if (byte3 > 255){
                        byte3 -= 256;
                        byte2 += 1;
                    }
                }
                if ( 24 <= ArrayMaskList.get(i) && ArrayMaskList.get(i-1) < 32){
                    byte4 += Math.pow(2,8-ArrayMaskList.get(i-1)%8);
                    if (byte4 > 255){
                        byte4 -= 256;
                        byte3 += 1;
                    }
                }
                ArrayNetworkList.add(byte1+"."+byte2+"."+byte3+"."+byte4);
                this.address = byte1+"."+byte2+"."+byte3+"."+byte4;
            }
        }
        this.address = addrBack;

        for(int i=0; i<ArrayNetworkList.size();i++){
            System.out.println("Sous-réseau "+(i+1)+" :");
            System.out.println("Adresse Réseau : "+ArrayNetworkList.get(i)+"/"+ArrayMaskList.get(i));
            System.out.println("Adresse Broadcast : "+BroadcastAddressAlt(ArrayNetworkList.get(i), String.valueOf(ArrayMaskList.get(i))));
            System.out.println("Première adresse : "+FirstAddressAlt(ArrayNetworkList.get(i), String.valueOf(ArrayMaskList.get(i))));
            System.out.println("Dernière adresse : "+LastAddressAlt(ArrayNetworkList.get(i), String.valueOf(ArrayMaskList.get(i))));
        }
    }
}