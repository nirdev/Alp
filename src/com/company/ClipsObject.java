package com.company;

import java.util.ArrayList;

/**
 * This class represents CLIPS object data structure
 * Each object represents a sensor.
 */
public class ClipsObject {

    String name;
    String type;
    String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}