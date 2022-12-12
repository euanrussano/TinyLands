package com.sophia.tinylands.buildings;

import java.util.ArrayList;

public class BuildingRepository{
    private static BuildingRepository instance;
    private ArrayList<Building> buildings;

    public BuildingRepository() {
    }

    public void initialize(){
        instance = this;
    }

    public static BuildingRepository getInstance() {
        return instance;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public Building findByName(String name){
        for (Building building : buildings){
            if (building.getName().equals(name))
                return building;
        }
        return null;
    }

    public ArrayList<Building> findAll() {
        return this.buildings;
    }

//    @Override
//    public void write(Json json) {
//
//    }
//
//    @Override
//    public void read(Json json, JsonValue jsonData) {
//        buildings = new ArrayList<>();
//        Iterator<JsonValue> it = jsonData.get("buildings").iterator();
//        while (it.hasNext()){
//            JsonValue buildingObject = it.next();
//            Building building = new Building();
//            building.read(json, buildingObject);
//
//            Iterator<JsonValue> materialsToBuildIt = buildingObject.get("materialsToBuild").iterator();
//            ArrayList<ResourceInstance> materialsToBuild = new ArrayList<>();
//            while (materialsToBuildIt.hasNext()) {
//                JsonValue resourceInstanceData = materialsToBuildIt.next();
//                System.out.println(resourceInstanceData);
//                ResourceInstance resourceInstance = new ResourceInstance();
//                resourceInstance.read(json, resourceInstanceData);
//                materialsToBuild.add(resourceInstance);
//            }
//            Iterator<JsonValue> attributesIt = buildingObject.get("attributes").iterator();
//            ArrayList attributes = new ArrayList();
//            while (attributesIt.hasNext()) {
//
//            }
//            Building building = new Building();
//            building.setName(buildingName);
//            building.setTexture(textureName);
//            building.setMaterialsToBuild(materialsToBuild);
//            building.setAttributes(attributes);
//            buildings.add(building);
//        }
//        instance = this;
//    }
}
