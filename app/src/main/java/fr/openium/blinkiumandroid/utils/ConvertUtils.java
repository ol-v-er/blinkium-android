package fr.openium.blinkiumandroid.utils;

import android.util.Log;

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

    public static byte[] encode(String s){
        byte[] startCode = { 1, 0, 1};
        byte[] header = generateHeader(s);
        byte[] body = stringToBin(s);
        byte[] result = new byte[startCode.length + header.length + body.length];

        System.arraycopy(startCode, 0, result, 0, startCode.length);
        System.arraycopy(header, 0, result, startCode.length, header.length);
        System.arraycopy(body, 0, result, startCode.length+header.length, body.length);

        return result;
    }

    public static byte[] generateHeader(String s){
        int dataLen = (short) s.length();
        int dataSize = 1;
        byte[] header = new byte[8+4];

        byte[] len = decToBin(dataLen, 8);
        byte[] size = decToBin(dataSize, 4);

        System.arraycopy(len, 0, header, 0, 8);
        System.arraycopy(size, 0, header, 8, 4);

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
