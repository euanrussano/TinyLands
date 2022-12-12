package com.sophia.tinylands.buildings;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BuildingFactory extends Actor {


    private final BuildingRepository buildingManager;
    private final TextureAtlas spritesheet;

    public BuildingFactory(BuildingRepository buildingManager, TextureAtlas spritesheet) {
        this.buildingManager = buildingManager;
        this.spritesheet = spritesheet;
    }

    public Group createBuilding(String buildingName) {
        System.out.println(buildingName);
        Building buildingModel = buildingManager.findByName(buildingName);
        Group building = new Group();
        building.setName(buildingName);
        TextureRegion buildingTextureRegion = spritesheet.findRegion(buildingModel.getTexture());
        Actor buildingImage = new Image(buildingTextureRegion);
        buildingImage.setName(Image.class.getSimpleName());
        building.setSize(buildingTextureRegion.getRegionWidth(), buildingTextureRegion.getRegionHeight());
        building.addActor(buildingImage);
        return building;
    }
}
