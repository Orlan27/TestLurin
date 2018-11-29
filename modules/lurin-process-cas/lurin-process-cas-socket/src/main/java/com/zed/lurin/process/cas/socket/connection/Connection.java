package com.zed.lurin.process.cas.socket.connection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Llince
 */
public class Connection {

    protected Socket cs;
    protected DataOutputStream salidaServidor;

    public boolean connection(String HOST, String PORT) {
        try {
            cs = new Socket(HOST, Integer.valueOf(PORT));
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
