package com.zed.lurin.security.password.services;


public interface IPasswordGeneratorService {
	
    /** different dictionaries used */
    static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    static final String NUMERIC = "0123456789";
    static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";
    
    /**
     * Method will generate random string based on the parameters
     * 
     * @param len
     *            the length of the random string
     * @param dic
     *            the dictionary used to generate the password
     * @return the random password
     */
	String generatePassword(int len, String dic);
}
