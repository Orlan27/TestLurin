
package com.zed.lurin.process.cas.socket.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Llince
 */
public class ClientCasTest {
    
    public ClientCasTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of startClient method, of class ClientCas.
     */
    @Test
    public void testStartClient() {
        ClientCas cli = new ClientCas(); 
        System.out.println("Iniciando cliente\n");
        boolean conect = cli.connection("localhost","1234");
        if(conect){
        String commnads="command_id:1002";
        commnads=(byte)commnads.length()+commnads;
        cli.startClient();
        cli.sendCommand(DatatypeConverter.printHexBinary(commnads.getBytes()));
        System.out.println("Mensage: \n"+cli.getResponseCas());
        try {
            Thread.sleep(3000);
            System.out.println("Close cliente\n");
            cli.closeClient();
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientCasTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    
}
