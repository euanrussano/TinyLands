package com.sophia.tinylands.buildings.attributes;

import com.sophia.tinylands.PlayerManager;

public abstract class BuildingAttribute {
    public abstract void update(PlayerManager playerManager);

    public abstract BuildingAttribute clone();
}
