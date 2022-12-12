package com.sophia.tinylands.resources;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.Iterator;

public class ResourceRepository implements Json.Serializable{

    private static ResourceRepository instance;
    private ArrayList<Resource> resources;

    public ResourceRepository() {
    }

    public static ResourceRepository getInstance() {
        return instance;
    }

    @Override
    public void write(Json json) {

    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        resources = new ArrayList<>();
        Iterator<JsonValue> it = jsonData.get("resources").iterator();
        while (it.hasNext()){
            JsonValue resourceObject = it.next();
            String resourceName = resourceObject.getString("name");
            Resource resource = new Resource();
            resource.setName(resourceName);
            resources.add(resource);
        }
        instance = this;
    }

    public Resource findByName(String resourceName) {
        for (Resource resource : resources){
            if (resource.getName().equals(resourceName))
                return resource;
        }
        return null;
    }

    public ArrayList<Resource> findAll() {
        return this.resources;
    }
}
