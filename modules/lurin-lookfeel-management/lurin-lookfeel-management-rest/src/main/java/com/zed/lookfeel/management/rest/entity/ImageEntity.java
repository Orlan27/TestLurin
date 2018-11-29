package com.zed.lookfeel.management.rest.entity;

import javax.ws.rs.FormParam;

public class ImageEntity {

    @FormParam("name")
    private String name;

    @FormParam("file")
    private byte[] file;
    
    public String getName() {
    	return this.name;
    }

    public void setName(String name) {
    	this.name = name;
    }
    
    public byte[] getFile() {
    	return this.file;
    }

    public void setFile(byte[] file) {
    	this.file = file;
    }

}
