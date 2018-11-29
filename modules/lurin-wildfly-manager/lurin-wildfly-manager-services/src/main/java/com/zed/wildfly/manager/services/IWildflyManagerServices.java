package com.zed.wildfly.manager.services;

import com.zed.lurin.domain.jpa.DataSources;

/**
 * Abstract Class that contains the Wildfly Manager methods
 * @author Francisco Lujano
 */
public interface IWildflyManagerServices {

    /**
     * method that creates a datasource in the server environment
     * @param host
     * @param port
     * @param dataSources
     * @param prefixJndi
     * @return
     */
    boolean createDataSource(String host, int port, DataSources dataSources, String prefixJndi);

    /**
     * method that creates a datasource in the server environment for username and password
     * @param host
     * @param port
     * @param dataSources
     * @param prefixJndi
     * @return
     */
    boolean createDataSource(String host, int port, DataSources dataSources, String prefixJndi, String userName, String password);
}
