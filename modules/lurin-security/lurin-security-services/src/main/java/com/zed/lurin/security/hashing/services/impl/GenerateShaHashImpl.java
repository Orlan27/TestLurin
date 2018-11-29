package com.zed.lurin.security.hashing.services.impl;

import com.zed.lurin.security.hashing.services.IGenerateShaHash;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import java.security.MessageDigest;

/**
 * <p>Stateless where the Methods that generate SHA hashing implemented</p>
 * @author Francisco Lujano
 * {@link IGenerateShaHash }
 */
@Stateless
public class GenerateShaHashImpl implements IGenerateShaHash {

    final static String SECRET_KEY = "ZGZzcjM0NTN3ZWZ3czQ1M3JlZndlcjM0c2Z3NDV3ZWZ3ZXRydw==";

    /*
     * Logger util
     */
    private static Logger LOGGER = Logger.getLogger(GenerateShaHashImpl.class.getName());


    /**
     * method that generates the hash in sha-512 for the user password
     *
     * @param text
     * @return
     */
    @Override
    public String generateSha512(String text) {
        return this.generateSha(text, MessageDigestAlgorithms.SHA_512);
    }

    /**
     * method that generates the hash in sha-512 for the user password
     * @param text
     * @param digestOption
     * @return
     */
    @Override
    public String generateSha(String text, String digestOption){

        String hexString;
        try{
            hexString = Hex.encodeHexString(this.encrypt(text, digestOption));
            return hexString;
        }catch (Exception e){
            LOGGER.error(String.format("Error Generating SHA-512 %s",e.getMessage()));
        }

        return null;
    }

    /**
     * method that decrypts the password
     *
     * @param encrypt
     * @return
     */
    @Override
    public String generatePlainText(String encrypt) {
        String plainText;
        try{
            plainText = this.decrypt(Hex.decodeHex(encrypt), MessageDigestAlgorithms.SHA_512);
            return plainText;
        }catch (Exception e){
            LOGGER.error(String.format("Error Decrypt SHA-512 %s",e.getMessage()));
        }
        return null;
    }


    /**
     *
     * method to encrypt a password with secret key
     *
     * @param text
     * @param digestOption
     * @return
     * @throws Exception
     */
    private byte[] encrypt(String text, String digestOption) throws Exception {
        final byte[] bytes = text.getBytes("UTF-8");
        final Cipher aes = getCipher(true, digestOption);
        final byte[] encrypt = aes.doFinal(bytes);
        return encrypt;
    }

    /**
     * method to decrypt a password and secret key
     * @param encrypt
     * @param digestOption
     * @return
     * @throws Exception
     */
    private String decrypt(byte[] encrypt, String digestOption) throws Exception {
        final Cipher aes = getCipher(false, digestOption);
        final byte[] bytes = aes.doFinal(encrypt);
        final String textPlain = new String(bytes, "UTF-8");
        return textPlain;
    }

    /**
     * method to get the cipher to encrypt or decrypt
     * @param isEncrypt
     * @param digestOption
     * @return
     * @throws Exception
     */
    private Cipher getCipher(boolean isEncrypt, String digestOption) throws Exception {

        final MessageDigest digest = MessageDigest.getInstance(digestOption);
        digest.update(SECRET_KEY.getBytes("UTF-8"));
        final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

        final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        if (isEncrypt) {
            aes.init(Cipher.ENCRYPT_MODE, key);
        } else {
            aes.init(Cipher.DECRYPT_MODE, key);
        }

        return aes;
    }
}
