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
        for (PropertySource source : sources.values()) {
            Optional<String> value = source.get(key);
            if (value.isPresent()) {
                return value;
            }
        }
        return Optional.empty();
    }

    public boolean contains(String key) {
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

        if (sources.containsKey(source.name())) {
            throw new IllegalArgumentException("PropertySource with name '" + source.name() + "' already exists");
        }
    }
}
