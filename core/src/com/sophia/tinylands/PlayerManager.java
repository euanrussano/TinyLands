package com.sophia.tinylands;

import com.sophia.tinylands.buildings.BuildingInstance;
import com.sophia.tinylands.buildings.attributes.BuildingAttribute;
import com.sophia.tinylands.resources.ResourceInstance;

import java.util.ArrayList;

public class PlayerManager{

    private ArrayList<ResourceInstance> resourceInstances;
    private ArrayList<BuildingInstance> buildingInstances;

    public PlayerManager() {
    }

    public void update() {
        for (BuildingInstance buildingInstance : buildingInstances){
            for (BuildingAttribute attribute : buildingInstance.getAttributes()){
                System.out.println(attribute);
                attribute.update(this);
            }
        }
    }

    public ResourceInstance getResourceInstance(String resourceName) {
        for (ResourceInstance resourceInstance : resourceInstances){
            if (resourceInstance.getResource().getName().equals(resourceName)){
                return resourceInstance;
            }
        }
        return null;
    }

    public void addBuildingInstance(BuildingInstance buildingInstance) {
        this.buildingInstances.add(buildingInstance);
    }

    public void substractFromResources(ResourceInstance resourceInstanceToSubtract) {
        for (ResourceInstance resourceInstance : resourceInstances){
            if (resourceInstance.getResource().getName().equals(resourceInstanceToSubtract.getResource().getName())){
                resourceInstance.subtract(resourceInstanceToSubtract);
            }
        }
    }

    private void addToResources(ResourceInstance resourceInstanceToAdd) {
        boolean hasThisResource = false;
        for (ResourceInstance resourceInstance : resourceInstances){
            if (resourceInstance.getResource().getName().equals(resourceInstanceToAdd.getResource().getName())){
                resourceInstance.add(resourceInstanceToAdd);
                hasThisResource = true;
            }
        }

        // if this resource is one that the player doesn't have, than add it.
        if (!hasThisResource){
            resourceInstances.add(resourceInstanceToAdd);
        }
    }

    public boolean hasEnoughResources(ArrayList<ResourceInstance> materialsToBuild) {
        for (ResourceInstance resourceInstanceToCompare : materialsToBuild){
            for (ResourceInstance resourceInstance : resourceInstances){
                if (resourceInstance.getResource().getName().equals(resourceInstanceToCompare.getResource().getName())){
                    if (resourceInstance.getQuantity() < (resourceInstanceToCompare.getQuantity())){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void addResourceInstances(ArrayList<ResourceInstance> resourceInstances) {
        for (ResourceInstance resourceInstance : resourceInstances){
            addToResources(resourceInstance);
        }
    }

    public void addResourceInstance(ResourceInstance resourceInstance) {
        addToResources(resourceInstance);
    }
}
