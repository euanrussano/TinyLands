package com.sophia.tinylands.buildings.attributes;

import com.sophia.tinylands.PlayerManager;
import com.sophia.tinylands.resources.Resource;
import com.sophia.tinylands.resources.ResourceInstance;
import com.sophia.tinylands.resources.ResourceRepository;

public class Residential extends BuildingAttribute{

    private Integer maxNumberOfResidents = 0;
    private Integer currentNumberOfResidents = 0;

    @Override
    public void update(PlayerManager playerManager) {
        if (currentNumberOfResidents < maxNumberOfResidents){
            currentNumberOfResidents = maxNumberOfResidents;
            Resource populationResource = ResourceRepository.getInstance().findByName("Population");
            ResourceInstance populationInstance = new ResourceInstance();
            populationInstance.setResource(populationResource);
            populationInstance.setQuantity(currentNumberOfResidents);
            playerManager.addResourceInstance(populationInstance);
        }
    }

    @Override
    public BuildingAttribute clone() {
        Residential clone = new Residential();
        clone.maxNumberOfResidents = maxNumberOfResidents;
        clone.currentNumberOfResidents = currentNumberOfResidents;
        return clone;
    }
}
