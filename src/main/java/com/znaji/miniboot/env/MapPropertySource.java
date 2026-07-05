package com.znaji.miniboot.env;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class MapPropertySource implements PropertySource {

    private final String name;
    private final Map<String, String> properties;

    public MapPropertySource(String name, Map<String, String> properties) {
        validateArg(name, "name");
        Objects.requireNonNull(properties, "properties must not be null");
        this.name = name;
        this.properties = new HashMap<>();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            validateArg(entry.getKey(), "property key");
            Objects.requireNonNull(entry.getValue(), "property value must not be null");
            this.properties.put(entry.getKey(), entry.getValue());
        }
    }
    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Optional<String> get(String key) {
        validateArg(key, "key");
        return Optional.ofNullable(properties.get(key));
    }

    @Override
    public boolean contains(String key) {
        validateArg(key, "key");
        return properties.containsKey(key);
    }

    @Override
    public Map<String, String> asMap() {
        return Map.copyOf(properties);
    }

    private static void validateArg(String arg, String name) {
        if (arg == null || arg.isBlank()) {
            throw new IllegalArgumentException(name + " must not be null or empty");
        }
    }
}
