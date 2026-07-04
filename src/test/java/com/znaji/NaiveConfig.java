package com.znaji;

import java.util.*;

public class NaiveConfig {

    private final Map<String, String> properties;

    public NaiveConfig() {
        this.properties = new HashMap<>();
    }

    public NaiveConfig(Map<String, String> initialProperties) {
        if (initialProperties == null) {
            this.properties = new HashMap<>();
        } else {
            this.properties = new HashMap<>(Map.copyOf(initialProperties));
        }
    }

    public void put(String key, String value) {
        Objects.requireNonNull(key, "Key cannot be null");
        Objects.requireNonNull(value, "Value cannot be null");
        if (key.isBlank()) {
            throw new IllegalArgumentException("Key cannot be blank");
        }
        this.properties.put(key, value);
    }

    public Optional<String> get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return Optional.ofNullable(this.properties.get(key));
    }

    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return this.properties.containsKey(key);
    }

    public Map<String, String> asMap() {
        return Map.copyOf(properties);
    }
}
