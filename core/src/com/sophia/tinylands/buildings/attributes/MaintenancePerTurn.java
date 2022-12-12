package com.sophia.tinylands.buildings.attributes;

import com.sophia.tinylands.PlayerManager;
import com.sophia.tinylands.resources.ResourceInstance;

import java.util.ArrayList;

public class MaintenancePerTurn extends BuildingAttribute{

    public ArrayList<ResourceInstance> resourceInstances;

    @Override
    public void update(PlayerManager playerManager) {
        for (ResourceInstance resourceInstance : resourceInstances){
            playerManager.substractFromResources(resourceInstance);
        }
    }

    @Override
    public BuildingAttribute clone() {
        MaintenancePerTurn clone = new MaintenancePerTurn();
        clone.resourceInstances = resourceInstances;
        return clone;
    }
}
