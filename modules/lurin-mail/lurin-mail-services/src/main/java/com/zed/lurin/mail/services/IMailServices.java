package com.zed.lurin.mail.services;


import javax.ejb.Local;

/**
 * <p>Abstract Class where the methods that manage the java mail are implemented</p>
 * @author Francisco Lujano
 */
@Local
public interface IMailServices {

    /**
     * method that send email
     * @param addresses
     * @param topic
     * @param textMessage
     */
    void send(String addresses, String topic, String textMessage);
}
