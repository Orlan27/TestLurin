package com.zed.lurin.mail.services.impl;


import com.zed.lurin.mail.services.IMailServices;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * <p>Stateless where the methods that manage the java mail are implemented</p>
 * @author Francisco Lujano
 */
@Stateless
public class MailServicesImpl implements IMailServices {


    private static Logger LOGGER = Logger.getLogger(MailServicesImpl.class.getName());

    /**
     * Mail Session reference.
     */
    @Resource(lookup = "java:/mail/lurinMail")
    Session session;


    /**
     * method that send email
     *
     * @param addresses
     * @param topic
     * @param textMessage
     */
    @Override
    public void send(String addresses, String topic, String textMessage) {
        try {

            Message message = new MimeMessage(this.session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
            message.setSubject(topic);
            message.setContent(textMessage, "text/html; charset=utf-8");

            Transport.send(message);

        } catch (MessagingException e) {
            LOGGER.warn(String.format("Cannot send mail %s",e.getMessage()));
        }
    }
}
