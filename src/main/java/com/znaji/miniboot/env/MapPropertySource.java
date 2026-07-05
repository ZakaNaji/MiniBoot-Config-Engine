package com.znaji.miniboot.env;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class MapPropertySource implements EnumerablePropertySource {

    private final String name;
    private final Map<String, String> properties;

    public MapPropertySource(String name, Map<String, String> properties) {
        validateArg(name, "name");
        validateSourceProperties(properties);

        this.name = name;
        Map<String, String> validated = new HashMap<>();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            validateArg(entry.getKey(), "property key");
            validateValue(entry.getValue(), "property value");
            validated.put(entry.getKey(), entry.getValue());
        }

        this.properties = Map.copyOf(validated);
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
        return this.properties;
    }

    private static void validateArg(String arg, String name) {
        if (arg == null || arg.isBlank()) {
            throw new IllegalArgumentException(name + " must not be null or blank");
        }
    }

    private static void validateSourceProperties(Map<String, String> properties) {
        if (properties == null) {
            throw new IllegalArgumentException("properties must not be null");
        }
    }

    private static void validateValue(String value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " must not be null");
        }
    }
}
