package com.zed.messages.rest;

import javax.ws.rs.core.Response;

/**
 * <p>Abstract Class which defines the methods manage the channel map are implemented</p>
 * @author Francisco Lujano
 */
public interface IMessagesServicesRest {

    /**
     * <p>method that obtain all channel map</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getAllMessages();

}
