/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neat.genome.framework;

import java.util.Arrays;

/**
 *
 * @author Thales
 */
public class Cantor {
    public static int sheepPair(int a, int b){
        if (a > -1 || b > -1) {
            return (int)(0.5 * (a + b) * (a + b + 1) + b);
        }
        return -1;
    }
    
    public static long pair(long a, long b) {
        //Cantors pairing function only works for positive integers
        if (a > -1 || b > -1) {
            //Creating an array of the two inputs for comparison later
            long[] input = {a, b};

            //Using Cantors paring function to generate unique number
            long result = (long) (0.5 * (a + b) * (a + b + 1) + b);

            /*Calling depair function of the result which allows us to compare
             the results of the depair function with the two inputs of the pair
             function*/
            if (Arrays.equals(depair(result), input)) {
                return result; //Return the result
            } else {
                return -1; //Otherwise return rouge value
            }
        } else {
            return -1; //Otherwise return rouge value
        }
    }

    public static long[] depair(long z) {
        /*Depair function is the reverse of the pairing function. It takes a
         single input and returns the two corespoding values. This allows
         us to perform a check. As well as getting the orignal values*/

        //Cantors depairing function:
        long t = (int) (Math.floor((Math.sqrt(8 * z + 1) - 1) / 2));
        long x = t * (t + 3) / 2 - z;
        long y = z - t * (t + 1) / 2;
        return new long[]{x, y}; //Returning an array containing the two numbers
    }

    public static String dectohex(long dec) {
        //As the pair value can get quite large im converting it to hex
        return Long.toHexString(dec).toUpperCase();
    }

    public static long hextodec(String hex) {
        /*To get the two initial values from the hex value it needs to be 
         converted back to base 10. The value can then be depaired.*/
        return Long.parseLong(hex, 16);
    }
}
