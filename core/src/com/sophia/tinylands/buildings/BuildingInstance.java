package com.sophia.tinylands.buildings;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.sophia.tinylands.buildings.attributes.BuildingAttribute;

import java.util.ArrayList;

public class BuildingInstance implements Json.Serializable{

    private Building building;
    private float x;
    private float y;

    private ArrayList<BuildingAttribute> attributes = new ArrayList<>();

    public BuildingInstance() {
    }

    @Override
    public void write(Json json) {
        json.writeValue("building", building.getName());
        json.writeValue("x", x);
        json.writeValue("y", y);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        String buildingName = jsonData.getChild("building").asString();
        this.building = BuildingRepository.getInstance().findByName(buildingName);
        for (BuildingAttribute attribute : this.building.getAttributes()){
            this.attributes.add(attribute.clone());
        }
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y= y;
    }

    public Building getBuilding() {
        return this.building;
    }

    public void addAttributes(ArrayList<BuildingAttribute> attributes) {
        for (BuildingAttribute attribute : attributes){
            this.attributes.add(attribute.clone());
        }
    }

    public ArrayList<BuildingAttribute> getAttributes() {
        return this.attributes;
    }
}
