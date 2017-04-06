package fr.openium.blinkiumandroid.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Kevin on 10/03/2017.
 */

public class ConvertUtils {

    public static Blink_State[] byteArrayToBlinkStateArray(byte[] array){
        Blink_State[] result = new Blink_State[array.length];

        for(int i = 0; i < array.length; i++){
            if(array[i] == 0){
                result[i] = Blink_State.BLACK;
            }
            else if (array[i] == 1){
                result[i] = Blink_State.WHITE;
            }
        }

        return result;
    }

    public static byte[] encode(ArrayList<String> s){
        byte[] startCode = { 1, 0, 1};
        byte[] nullChar = { 0, 0, 0, 0, 0, 0, 0, 0 };
        byte[] header = generateHeader(s);


        int len = 0, actualIndex = 0;
        for(int i = 0; i < s.size(); i++){
            len += (s.get(i).length()+1);
        }
        byte[] body = new byte[len*8];
        for(int i = 0; i < s.size(); i++){
            byte[] temp = stringToBin(s.get(i));
            System.arraycopy(temp, 0, body, actualIndex, temp.length);
            actualIndex += temp.length;
            System.arraycopy(nullChar, 0, body, actualIndex, 8);
            actualIndex += 8;
        }

        byte[] synchro = new byte[60];
        byte[] result = new byte[startCode.length + header.length + body.length + synchro.length];

        for(int i = 0; i < synchro.length; i++){
            synchro[i] = (byte) (i % 2);
        }

        System.arraycopy(startCode, 0, result, 0, startCode.length);
        System.arraycopy(synchro, 0, result, startCode.length, synchro.length);
        System.arraycopy(header, 0, result, startCode.length+synchro.length, header.length);
        System.arraycopy(body, 0, result, startCode.length+header.length+synchro.length, body.length);

        return result;
    }

    public static byte[] generateHeader(ArrayList<String> s){
        int dataLen = 0;

        for(int i = 0; i < s.size(); i++){
            dataLen += s.get(i).length();
        }

        if(dataLen > Math.pow(2, 12)) dataLen = 0;

        byte[] header = decToBin(dataLen, 12);

        return header;
    }

    public static byte[] asciiToBin(char c){
        return decToBin((int)c, 8);
    }


    public static byte[] decToBin(int dec, int size){
        byte[] result = new byte[size];
        int temp = dec;

        //Algorithme de conversion Decimal -> Binaire
        //Divisions succéssives par 2 et remplissage du tableau result avec le reste de ces divisions
        for(int i = size-1; i >=0; i--){
            result[i] = (byte) (temp % 2);
            temp = temp / 2;
        }

        return result;
    }

    public static byte[] stringToBin(String s){
        byte[] charBin;
        byte[] result = new byte[s.length()*8];
        //Conversion de la chaine
        for(int i = 0; i < s.length(); i++){
            //Tableau qui contiendra temporairement les 8 bits du caractère
            charBin = asciiToBin(s.charAt(i));

            System.arraycopy(charBin, 0, result, i*8, 8);

        }
        return result;
    }
}
