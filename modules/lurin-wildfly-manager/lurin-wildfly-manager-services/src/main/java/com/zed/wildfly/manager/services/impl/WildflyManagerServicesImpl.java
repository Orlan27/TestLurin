package com.zed.wildfly.manager.services.impl;


import com.zed.lurin.domain.jpa.DataSources;
import com.zed.lurin.domain.jpa.SourceType;
import com.zed.wildfly.manager.services.IWildflyManagerServices;
import org.apache.log4j.Logger;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import javax.ejb.Stateless;
import javax.security.auth.callback.*;
import javax.security.sasl.RealmCallback;
import java.io.IOException;

/**
 * Class that contains the Wildfly Manager methods
 * @author Francisco Lujano
 */
@Stateless
public class WildflyManagerServicesImpl implements IWildflyManagerServices {

    private static Logger LOGGER = Logger.getLogger(WildflyManagerServicesImpl.class.getName());


    /**
     * method that creates a datasource in the server environment
     * @param host
     * @param port
     * @param dataSources
     * @param prefixJndi
     * @return
     */
    @Override
    public boolean createDataSource(String host, int port, DataSources dataSources, String prefixJndi){
        boolean isCreateDS = false;



        if(dataSources.getSourceType().getSourceType().equals(SourceType.TYPE.JNDI.toString())
                || dataSources.getSourceType().getSourceType().equals(SourceType.TYPE.JDBC.toString())){
            try (ModelControllerClient client = ModelControllerClient.Factory
                    .create(host, port)) {

                ModelNode op = generateDataSource(dataSources, prefixJndi);

                client.execute(op);

                isCreateDS = true;

            }catch (Exception e) {
                LOGGER.error(e);
            }
        }

        return isCreateDS;
    }

    /**
     * method that creates a datasource in the server environment for username and password
     *
     * @param host
     * @param port
     * @param dataSources
     * @param prefixJndi
     * @param userName
     * @param password    @return
     */
    @Override
    public boolean createDataSource(String host, int port, DataSources dataSources, String prefixJndi, String userName, String password) {
        boolean isCreateDS = false;

        if(dataSources.getSourceType().getSourceType().equals(SourceType.TYPE.JNDI.toString())
                || dataSources.getSourceType().getSourceType().equals(SourceType.TYPE.JDBC.toString())){

            try (ModelControllerClient client = ModelControllerClient.Factory.create(host, port, new CallbackHandler() {
                @Override
                public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
                    for (Callback current : callbacks) {
                        if (current instanceof NameCallback) {
                            NameCallback ncb = (NameCallback) current;
                            ncb.setName(userName);
                        } else if (current instanceof PasswordCallback) {
                            PasswordCallback pcb = (PasswordCallback) current;
                            pcb.setPassword(password.toCharArray());
                        } else if (current instanceof RealmCallback) {
                            RealmCallback rcb = (RealmCallback) current;
                            rcb.setText(rcb.getDefaultText());
                        } else {
                            throw new UnsupportedCallbackException(current);
                        }
                    }
                }
            }) ) {

                ModelNode op = generateDataSource(dataSources, prefixJndi);

                client.execute(op);

                isCreateDS = true;

            }catch (Exception e) {
                LOGGER.error(e);
            }
        }


        return isCreateDS;
    }

    /**
     * Method commons for generate DataSource
     * @param dataSources
     * @param prefixJndi
     * @return
     */
    private ModelNode generateDataSource(DataSources dataSources, String prefixJndi) {
        ModelNode op = new ModelNode();
        op.get("operation").set("add");
        ModelNode address = op.get("address");
        address.add("subsystem", "datasources");
        address.add("data-source", dataSources.getJndiName());
        op.get("jndi-name").set(prefixJndi+dataSources.getJndiName());
        op.get("name").set(dataSources.getJndiName());
        op.get("driver-name").set("oracle");
        op.get("connection-url").set(String.format("jdbc:oracle:thin:@%s:%s:%s", dataSources.getIp(), dataSources.getPort(), dataSources.getSid()));

        op.get("user-name").set(dataSources.getUserName());
        op.get("password").set(dataSources.getPassword());
        op.get("jta").set("false");
        op.get("use-ccm").set("false");
        op.get("check-valid-connection-sql").set("select 1 from dual");
        op.get("validate-on-match").set("false");
        op.get("background-validation").set("false");
        op.get("share-prepared-statements").set("false");
        op.get("max-pool-size").set("100");
        op.get("enabled").set(true);
        return op;
    }


}
