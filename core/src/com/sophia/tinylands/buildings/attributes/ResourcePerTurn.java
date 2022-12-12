package com.sophia.tinylands.buildings.attributes;

import com.sophia.tinylands.PlayerManager;
import com.sophia.tinylands.resources.ResourceInstance;

import java.util.ArrayList;

public class ResourcePerTurn extends BuildingAttribute{

    public ArrayList<ResourceInstance> resourceInstances;
    @Override
    public void update(PlayerManager playerManager) {
        playerManager.addResourceInstances(resourceInstances);
    }

    @Override
    public BuildingAttribute clone() {
        ResourcePerTurn clone = new ResourcePerTurn();
        clone.resourceInstances = resourceInstances;
        return clone;
    }
}
