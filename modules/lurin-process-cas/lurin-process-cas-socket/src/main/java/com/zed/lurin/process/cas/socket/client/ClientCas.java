
package com.zed.lurin.process.cas.socket.client;

import com.zed.lurin.process.cas.socket.connection.Connection;
import java.io.DataOutputStream;
import java.io.InputStream;

/**
 *
 * @author Llince
 */
public class ClientCas extends Connection {

    
    private String msgOut;

    public ClientCas(){
        super();
    }

    public void startClient() 
    {
        try {
            salidaServidor = new DataOutputStream(cs.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
    public void sendCommand(String command)
    {
        try {
            salidaServidor.writeUTF(command);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
    public String getResponseCas() {
        try {
            Thread.sleep(300);
            InputStream is = cs.getInputStream();
            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) != -1) {
                msgOut = new String(buffer, 0, read);
                break;
            }

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
        return msgOut;
    }
 
    public void closeClient() {
        try {
            cs.close();
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
    
}
