package com.sophia.tinylands.buildings;

import com.sophia.tinylands.resources.ResourceInstance;
import com.sophia.tinylands.buildings.attributes.BuildingAttribute;

import java.util.ArrayList;

public class Building {
    private String name;
    private String texture;
    private ArrayList materialsToBuild;
    private ArrayList<BuildingAttribute> attributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public ArrayList<ResourceInstance> getMaterialsToBuild() {
        return materialsToBuild;
    }

    public void setMaterialsToBuild(ArrayList materialsToBuild) {
        this.materialsToBuild = materialsToBuild;
    }

    public ArrayList<BuildingAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<BuildingAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Building <" + name + ">";
    }
}
