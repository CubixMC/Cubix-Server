package org.cubixmc.server.util.auth;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class GameProfile {
    private final Map<String, Property> properties = Maps.newConcurrentMap();
    private final String name;
    private final UUID uuid;

    public GameProfile(JsonObject jsonObject) {
        this.name = jsonObject.get("name").getAsString();
        this.uuid = uuid(jsonObject.get("id").getAsString());
        if(jsonObject.has("properties")) {
            parseProperties(jsonObject.getAsJsonArray("properties"));
        }
    }

    public GameProfile(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean hasProperties() {
        return !properties.isEmpty();
    }

    public Property getProperty(String name) {
        return properties.get(name);
    }

    public Collection<Property> getProperties() {
        return Collections.unmodifiableCollection(properties.values());
    }

    private void parseProperties(JsonArray json) {
        for(int i = 0; i < json.size(); i++) {
            JsonObject object = json.get(i).getAsJsonObject();
            String name = object.get("name").getAsString();
            String value = object.get("value").getAsString();
            String signature = object.has("signature") ? object.get("signature").getAsString() : null;
            properties.put(name, new Property(name, value, signature));
        }
    }

    private UUID uuid(String id) {
        return UUID.fromString(id.substring(0, 8) + '-'
                + id.substring(8, 12) + '-'
                + id.substring(12, 16) + '-'
                + id.substring(16, 20) + '-'
                + id.substring(20));
    }
}
