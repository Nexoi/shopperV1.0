package com.seeu.filesystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix = "com.seeu.storage")
@PropertySource("classpath:application.properties")
@Component
public class StorageProperties {

    /**
     * Folder location for storing files
     */
//    private String location = "templates/upload-dir";

    @Value("${com.seeu.storage.location}")
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
