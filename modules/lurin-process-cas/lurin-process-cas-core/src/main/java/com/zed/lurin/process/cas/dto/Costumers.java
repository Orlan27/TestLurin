
package com.zed.lurin.process.cas.dto;

/**
 *
 * @author Llince
 */
@Deprecated
public class Costumers implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int idPackage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(int idPackage) {
        this.idPackage = idPackage;
    }

}
