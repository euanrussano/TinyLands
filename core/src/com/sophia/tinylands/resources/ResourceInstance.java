package com.sophia.tinylands.resources;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class ResourceInstance implements Json.Serializable {

    private Resource resource;
    private int quantity;

    public ResourceInstance() {
    }

    @Override
    public void write(Json json) {
        json.writeValue("class", "ResouceInstance");
        json.writeValue("resource", resource.getName());
        json.writeValue("quantity", quantity);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        String resourceName = jsonData.getString("name");
        int quantity = jsonData.getInt("quantity");
        this.resource = ResourceRepository.getInstance().findByName(resourceName);
        this.quantity = quantity;
    }

    public Resource getResource() {
        return this.resource;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void subtract(ResourceInstance resourceInstanceToSubtract) {
        this.quantity -= resourceInstanceToSubtract.quantity;
    }

    public void add(ResourceInstance resourceInstanceToAdd) {
        this.quantity += resourceInstanceToAdd.quantity;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
