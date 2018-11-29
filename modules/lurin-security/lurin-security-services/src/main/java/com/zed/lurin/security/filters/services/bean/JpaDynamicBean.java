package com.zed.lurin.security.filters.services.bean;

import javax.ejb.Stateless;

/**
 * Stateless that sends the datasource assigned to an operator
 * @author Francisco Lujano
 */
@Stateless
public class JpaDynamicBean {
    private String jndiName;

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }
}
