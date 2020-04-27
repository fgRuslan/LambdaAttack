/**
 * 
 */
package com.github.fgruslan.lambdaattack;

import com.github.steveice10.protocol.v1_15.data.message.Message;

/**
 * @author fgRuslan
 *
 */
public class Utils {
	public static String currentCaptcha;

	public static String extract() {
		String input = currentCaptcha;     //input string
		String lastDigits = "";     //substring containing last 7 characters

		if (input.length() > 7) 
		{
			lastDigits = input.substring(input.length() - 7);
		} 
		else
		{
			lastDigits = input;
		}

		return lastDigits;
	}

	public static boolean checkSpamTrigger(String message) {
		if(message.contains("flood") ||
				message.contains("spam") ||
				message.contains("спам") ||
				message.contains("флуд"))
			return true;
		return false;

	}
}
