package com.example.campuseventtracker.model;

import java.io.Serializable;

public class Events implements Serializable {
    long id, date, user_id;
    String name, type, image, details, location;
    boolean isFavourite;

    public Events(long id, long date, long user_id, String name, String type, String image, String details, String location) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.type = type;
        this.image = image;
        this.details = details;
        this.location = location;
        this.user_id=user_id;
    }

    public Events(long date, long user_id, String name, String type, String image, String details, String location) {
        this.date = date;
        this.name = name;
        this.type = type;
        this.image = image;
        this.details = details;
        this.location = location;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getUser_id() {
        return user_id;
    }


    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}

