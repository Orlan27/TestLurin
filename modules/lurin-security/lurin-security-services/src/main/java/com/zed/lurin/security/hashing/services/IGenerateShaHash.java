package com.zed.lurin.security.hashing.services;

public interface IGenerateShaHash {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.security.services/GenerateShaHashImpl!com.zed.lurin.security.hashing.services.IGenerateShaHash";

    /**
     * method that generates the hash in sha-512 for the user password
     * @param text
     * @return
     */
    String generateSha512(String text);
    /**
     * method that generates the hash in sha-512 for the user password
     * @param text
     * @param digestOption
     * @return
     */
    String generateSha(String text, String digestOption);

    /**
     * method that decrypts the password
     * @param encrypt
     * @return
     */
    String generatePlainText(String encrypt);
}
