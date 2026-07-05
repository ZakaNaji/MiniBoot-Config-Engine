package com.znaji.miniboot.env;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapPropertySourceTest {

    private Map<String, String> sourceProperties;

    @BeforeEach
    void setUp() {
        sourceProperties = new HashMap<>();
    }

    @Test
    @DisplayName("exposes its name")
    void exposesName() {
        sourceProperties.put("key1", "value1");
        MapPropertySource source = new MapPropertySource("testSource", sourceProperties);
        assertEquals("testSource", source.name());
    }

    @Test
    @DisplayName("returns the correct value for a given key")
    void returnsCorrectValue() {
        sourceProperties.put("key1", "value1");
        MapPropertySource source = new MapPropertySource("testSource", sourceProperties);
        assertTrue(source.get("key1").isPresent());
        assertEquals("value1", source.get("key1").get());
    }

    @Test
    @DisplayName("returns empty for a non-existent key")
    void returnsEmptyForNonExistentKey() {
        MapPropertySource source = new MapPropertySource("testSource", sourceProperties);
        assertTrue(source.get("nonExistentKey").isEmpty());
    }

    @Test
    @DisplayName("contains returns true for existing keys")
    void containsMethodBehavior() {
        sourceProperties.put("key1", "value1");
        MapPropertySource source = new MapPropertySource("testSource", sourceProperties);
        assertTrue(source.contains("key1"));
    }

    @Test
    @DisplayName("contains returns false for non-existing keys")
    void containsMethodBehaviorForNonExistingKeys() {
        MapPropertySource source = new MapPropertySource("testSource", sourceProperties);
        assertFalse(source.contains("nonExistentKey"));
    }

    @Test
    @DisplayName("constructor copies input map")
    void constructorCopiesInputMap() {
        sourceProperties.put("key1", "value1");
        MapPropertySource source = new MapPropertySource("testSource", sourceProperties);
        sourceProperties.put("key2", "value2"); // Modify the original map after construction
        sourceProperties.put("key1", "newValue"); // Modify the original map after construction

        assertEquals("value1", source.get("key1").get()); // The source should
        assertFalse(source.contains("key2")); // The source should not contain the new key
    }

    @Test
    @DisplayName("asMap returns an unmodifiable copy of the properties")
    void asMapReturnsUnmodifiableCopy() {
        sourceProperties.put("key1", "value1");
        MapPropertySource source = new MapPropertySource("testSource", sourceProperties);
        Map<String, String> mapCopy = source.asMap();
        assertThrows(UnsupportedOperationException.class, () -> mapCopy.put("key2", "value2"));
    }

    @Test
    @DisplayName("constructor throws exception for null name")
    void constructorThrowsForNullName() {
        assertThrows(IllegalArgumentException.class, () -> new MapPropertySource(null, sourceProperties));
    }

    @Test
    @DisplayName("constructor throws exception for blank name")
    void constructorThrowsForBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new MapPropertySource("   ", sourceProperties));
    }

    @Test
    @DisplayName("constructor throws exception for null properties map")
    void constructorThrowsForNullPropertiesMap() {
        assertThrows(IllegalArgumentException.class, () -> new MapPropertySource("testSource", null));
    }

    @Test
    @DisplayName("constructor throws exception for null property key")
    void constructorThrowsForNullPropertyKey() {
        sourceProperties.put(null, "value1");
        assertThrows(IllegalArgumentException.class, () -> new MapPropertySource("testSource", sourceProperties));
    }

    @Test
    @DisplayName("constructor throws exception for blank property key")
    void constructorThrowsForBlankPropertyKey() {
        sourceProperties.put("   ", "value1");
        assertThrows(IllegalArgumentException.class, () -> new MapPropertySource("testSource", sourceProperties));
    }

    @Test
    @DisplayName("constructor throws exception for null property value")
    void constructorThrowsForNullPropertyValue() {
        sourceProperties.put("key1", null);
        assertThrows(IllegalArgumentException.class, () -> new MapPropertySource("testSource", sourceProperties));
    }

    @Test
    @DisplayName("get method throws exception for null key")
    void getMethodThrowsForNullKey() {
        MapPropertySource source = new MapPropertySource("testSource", sourceProperties);
        assertThrows(IllegalArgumentException.class, () -> source.get(null));
    }

    @Test
    @DisplayName("get method throws exception for blank key")
    void getMethodThrowsForBlankKey() {
        MapPropertySource source = new MapPropertySource("testSource", sourceProperties);
        assertThrows(IllegalArgumentException.class, () -> source.get("   "));
    }

    @Test
    @DisplayName("contains method throws exception for null key")
    void containsMethodThrowsForNullKey() {
        MapPropertySource source = new MapPropertySource("testSource", sourceProperties);
        assertThrows(IllegalArgumentException.class, () -> source.contains(null));
    }

    @Test
    @DisplayName("contains method throws exception for blank key")
    void containsMethodThrowsForBlankKey() {
        MapPropertySource source = new MapPropertySource("testSource", sourceProperties);
        assertThrows(IllegalArgumentException.class, () -> source.contains("   "));
    }
}