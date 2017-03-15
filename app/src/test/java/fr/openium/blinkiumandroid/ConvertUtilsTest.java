package fr.openium.blinkiumandroid;

import org.junit.Test;

import java.util.Arrays;

import fr.openium.blinkiumandroid.utils.ConvertUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by ogoutet on 15/03/2017.
 */

public class ConvertUtilsTest {

    @Test
    public void testAsciiToBinWithSimpleChar(){
        // expect
        byte[] expected = {0,1,1,0,0,0,0,1};
        //when
        byte[] result = ConvertUtils.asciiToBin('a');
        //assert
        assertTrue(Arrays.equals(expected,result));
    }

    @Test
    public void testAsciiToBinNulChar(){
        // expect
        byte[] expected = {0,0,0,0,0,0,0,0};
        //when
        byte[] result = ConvertUtils.asciiToBin(Character.MIN_VALUE);
        //assert
        assertTrue(Arrays.equals(expected,result));
    }

    @Test
    public void testAsciiToBinMaxAsciiChar(){
        // expect
        byte[] expected = {0,1,1,1,1,1,1,1};
        //when
        byte[] result = ConvertUtils.asciiToBin((char)127);
        //assert
        assertTrue(Arrays.equals(expected,result));
    }

    @Test
    public void testAsciiToBinUnhadledChar(){
        // expect
        byte[] expected = {0,1,1,1,1,1,1,1};
        //when
        byte[] result = ConvertUtils.asciiToBin('\u24d4');
        //assert
        assertTrue(Arrays.equals(expected,result));
    }
}
