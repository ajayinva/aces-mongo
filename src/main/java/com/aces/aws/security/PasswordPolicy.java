package com.aces.aws.security;
/**
 *
 * @author aagarwal
 *
 */
public class PasswordPolicy {	
	/**
	 *
	 */
	private static int maxLength = 16;
	/**
	 *
	 */
	private static  int minLength = 8;
	/**
	 *
	 */
	private static  int numberOfNumeric = 1;
	/**
	 *
	 */
	private static  int numberOfLower = 1;
	/**
	 *
	 */
	private static  int numberOfUpper = 1;
	/**
	 *
	 */
	private PasswordPolicy(){
		
	}
	/**
	 * 
	 * @param password
	 * @return
	 */
	public static boolean isValid(String password){
		char[] newPasswordArray = password.toCharArray();
		int numericChars = 0;		
		int smallCaseChars = 0;
		int upperCaseChars = 0;
		for(int i = 0;i<newPasswordArray.length;i++){
			if(newPasswordArray[i] >='0' && newPasswordArray[i]<='9'){
				numericChars++;
			}
			else if(newPasswordArray[i] >='a' && newPasswordArray[i]<='z'){
				smallCaseChars++;
			} else if(newPasswordArray[i] >='A' && newPasswordArray[i]<='Z'){
				upperCaseChars ++;
			}
		}
		if(numericChars < numberOfNumeric || smallCaseChars < numberOfLower || upperCaseChars < numberOfUpper || newPasswordArray.length < minLength || newPasswordArray.length > maxLength) {
			return false;		
		}
		return true;
	}
}
