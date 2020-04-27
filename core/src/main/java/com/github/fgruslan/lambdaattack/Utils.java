/**
 * 
 */
package com.github.fgruslan.lambdaattack;

import com.github.steveice10.mc.protocol.data.message.Message;

/**
 * @author fgRuslan
 *
 */
public class Utils {
	public static String currentCaptcha;
	
	public static String extract() {
		return currentCaptcha.replace("Введите первым делом каптчу ", "");
		// Введите первым делом каптчу. 
	}
	
	public boolean checkSpamTrigger(Message message) {
		if(message.getFullText().contains("flood") ||
				message.getFullText().contains("spam") ||
				message.getFullText().contains("спам") ||
				message.getFullText().contains("флуд"))
			return true;
		return false;
		
	}
}
