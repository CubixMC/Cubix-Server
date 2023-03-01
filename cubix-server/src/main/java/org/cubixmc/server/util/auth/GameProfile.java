package org.cubixmc.server.util.auth;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameProfile {
    private final Map<String, Property> properties = new ConcurrentHashMap<>();
    private final String name;
    private final UUID uuid;

    public GameProfile(JsonObject jsonObject) {
//        if(jsonObject.get("name").getAsString().equalsIgnoreCase("lenis0012")) {
//            JsonParser parser = new JsonParser();
//            jsonObject = (JsonObject) parser.parse("{\"id\":\"16b355fb4d124bbf865bca8bb7e4fd8d\",\"name\":\"lenis0012\",\"properties\":[{\"name\":\"textures\",\"value\":\"eyJ0aW1lc3RhbXAiOjE0MTgzNTMzMzE3OTcsInByb2ZpbGVJZCI6IjE2YjM1NWZiNGQxMjRiYmY4NjViY2E4YmI3ZTRmZDhkIiwicHJvZmlsZU5hbWUiOiJsZW5pczAwMTIiLCJpc1B1YmxpYyI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRkZmYyMjUzMjNhZTg4MzRkMjE4ZGI2YTk0YmM1Zjg4ZGE0YWFmZjgzNWExYTk2ZjBkZmY0YTE2YzhhZDIifSwiQ0FQRSI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzhhZGI0NWRiZWZlMjQ2NTc0NWE2ZDFmY2VlOWI4YTcyMTc5MmUyMWM1ZWIxNTI0ZWY1MTZiYjZmYjE4OSJ9fX0=\",\"signature\":\"N2EebrC5CmdiJtYmVrq+CddUebYL2tBaW4r7/HrUO2DOocIPbEhUD/TBjfqKqocL9Oh7kmRFOWYWxx9B/BebMmqA3GEm5vW5+dG5xSON805QlNd478CjR8Mfaj20/Jmt1Keq4GJ6fOnI8Ap19Is1MdN4NdR/vKdoe/xrwpPWAIqhUH3PhGz9UrCANmQlUOZhxjOJb76NouXXpiZPkQVSFl3/6cD5CMglr7Ix7xHtxiLml1kr2u36TsvDYXiQTNrZ2AXaMX6COwm2JQwUxmcjStbdxcXJZ+/lpYlgoh/5uXbUIV527G9TIZpTN+3WjGw/YF41dSTWXTyeSfsgv4jKvDns4bzMdPSwcWRs0U6fFyBLOzRbVcBnKiYgh9DcbGA/9PNoYHQQQOX9lCLt2pEvtwuv+bGdF1yBvnrAL5/ey4qaILdO6QTsbV30fkqbCCKEDT41sqCt2YuiHBw1m7NH/02ML5haWYQ2Ua0p8Ic8Iyk9bLPX9AnFd+Ex0e159E4N0qJ/p6exYGcGMBPjYn1hHUo0WxH7ksABgdIGUr/0L++7Oteaor3Ff9NRSy8V35HPE0TXKQpp7kPHbDCGautcCuFz4zSSjfP5VUIfa0ThQ1twCpnMuN6OsvhM31Dd+PPsBY3DczNHSgDZ9frJu3U9R58K0ArO9GLn9SKA78fnJZA=\"}]}");
//        }
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
