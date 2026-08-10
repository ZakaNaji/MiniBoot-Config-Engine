package com.znaji.miniboot.env;

import java.util.*;

public final class PropertySources {
    private final SequencedMap<String, PropertySource> sources = new LinkedHashMap<>();

    public void addFirst(PropertySource source) {
        validateSource(source);
        sources.putFirst(source.name(), source);
    }


    public void addLast(PropertySource source) {
        validateSource(source);
        sources.putLast(source.name(), source);
    }

    public Optional<String> get(String key) {
        validateKey(key);

        for (PropertySource source : sources.values()) {
            Optional<String> value = source.get(key);
            if (value.isPresent()) {
                return value;
            }
        }
        return Optional.empty();
    }

    public boolean contains(String key) {
        validateKey(key);

        for (PropertySource source : sources.values()) {
            if (source.contains(key)) {
                return true;
            }
        }
        return false;
    }

    public List<PropertySource> asList() {
        return List.copyOf(sources.values());
    }

    public int size() {
        return sources.size();
    }

    private void validateSource(PropertySource source) {
        if (source == null ) {
            throw new IllegalArgumentException("PropertySource cannot be null");
        }

        String sourceName = source.name();

        if (sourceName == null || sourceName.isBlank()) {
            throw new IllegalArgumentException("PropertySource name must not be null or blank");
        }

        if (sources.containsKey(sourceName)) {
            throw new IllegalArgumentException("PropertySource with name '" + sourceName + "' already exists");
        }
    }

    private static void validateKey(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Key must not be null or blank");
        }
    }
}
