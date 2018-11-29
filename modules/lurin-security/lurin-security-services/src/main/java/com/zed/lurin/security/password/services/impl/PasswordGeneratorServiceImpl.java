package com.zed.lurin.security.password.services.impl;

import com.zed.lurin.security.password.services.IPasswordGeneratorService;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import java.security.SecureRandom;

/**
 * <p>Stateless where the Methods that generate SHA hashing implemented</p>
 * @author Francisco Lujano
 * {@link IPasswordGeneratorService }
 */
@Stateless
public class PasswordGeneratorServiceImpl implements IPasswordGeneratorService {

    private SecureRandom random = new SecureRandom();
    
    /**
     * Method will generate random string based on the parameters
     * 
     * @param len
     *            the length of the random string
     * @param dic
     *            the dictionary used to generate the password
     * @return the random password
     */
    public String generatePassword(int len, String dic) {
    String result = "";
    for (int i = 0; i < len; i++) {
        int index = random.nextInt(dic.length());
        result += dic.charAt(index);
    }
    return result;
    }
}
