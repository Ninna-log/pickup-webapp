package com.pickupapp.pickupapp.model;

import org.apache.tomcat.jni.File;

import javax.persistence.Entity;

@Entity
public class Photo {
    private File photo;

    public Photo() {}

    public Photo(File photo) {
        this.photo = photo;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }
}
