/**
 * 
 */
package com.github.fgruslan.lambdaattack;

import java.util.Random;

/**
 * @author Руслан
 *
 */
public class StringRandomizer {
	public static String randomizeString(Random rng, String characters, int length) {
		char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}//You were warned for flood: 

}
