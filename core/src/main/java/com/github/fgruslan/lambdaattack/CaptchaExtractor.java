/**
 * 
 */
package com.github.fgruslan.lambdaattack;

/**
 * @author fgRuslan
 *
 */
public class CaptchaExtractor {
	public static String currentCaptcha;
	
	public static String extract() {
		return currentCaptcha.replace("Введите первым делом каптчу. ", "");
		// Введите первым делом каптчу. (Чо правда?)
	}
}
