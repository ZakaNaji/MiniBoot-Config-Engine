package com.znaji;

import java.util.*;

public class NaiveConfig {

    private final Map<String, String> properties;

    public NaiveConfig() {
        this.properties = new HashMap<>();
    }

    public NaiveConfig(Map<String, String> initialProperties) {
        this.properties = new HashMap<>();

        if (initialProperties != null) {
            for (Map.Entry<String, String> entry : initialProperties.entrySet()) {
                validateKey(entry.getKey());
                validateValue(entry.getValue());
                this.properties.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void put(String key, String value) {
        validateKey(key);
        validateValue(value);
        this.properties.put(key, value);
    }



    public Optional<String> get(String key) {
        validateKey(key);
        return Optional.ofNullable(this.properties.get(key));
    }

    public boolean contains(String key) {
        validateKey(key);
        return this.properties.containsKey(key);
    }

    public Map<String, String> asMap() {
        return Map.copyOf(properties);
    }

    private static void validateKey(String key) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (key.isBlank()) {
            throw new IllegalArgumentException("Key cannot be blank");
        }
    }

    private static void validateValue(String value) {
        Objects.requireNonNull(value, "Value cannot be null");
    }
}
