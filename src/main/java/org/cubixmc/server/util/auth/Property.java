package org.cubixmc.server.util.auth;

public class Property {
    private final String name;
    private final String value;
    private final String signature;

    public Property(String name, String value) {
        this(name, value, null);
    }

    public Property(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public boolean isSigned() {
        return signature != null;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }
}
