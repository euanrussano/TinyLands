package com.sophia.tinylands.buildings;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.awt.*;

public class BuildingButton extends ImageButton {


    private String buildingName;

    public BuildingButton(Drawable imageUp) {
        super(imageUp);
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingName() {
        return this.buildingName;
    }
}
