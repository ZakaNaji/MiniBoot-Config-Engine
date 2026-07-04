package com.znaji.miniboot;

import com.znaji.miniboot.NaiveConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NaiveConfigTest {

    @Test
    @DisplayName("returns the value when the key exists")
    void returnsValueWhenKeyExists() {
        // given
        NaiveConfig config = new NaiveConfig();
        config.put("key1", "value1");

        // when
        String value = config.get("key1").orElse(null);

        // then
        assertEquals("value1", value);

    }

    @Test
    @DisplayName("returns empty when the key is missing")
    void returnsEmptyWhenKeyIsMissing() {
        // given
        NaiveConfig config = new NaiveConfig();

        // when
        Optional<String> missingKey = config.get("missingKey");

        // then
        assertTrue(missingKey.isEmpty());
    }

    @Test
    @DisplayName("returns true when the key exists")
    void containsExistingKey() {
        // given
        NaiveConfig config = new NaiveConfig();
        config.put("key1", "value1");

        // when
        boolean contains = config.contains("key1");

        // then
        assertTrue(contains);
    }

    @Test
    @DisplayName("returns false when the key is missing")
    void doesNotContainMissingKey() {
        // given
        NaiveConfig config = new NaiveConfig();

        // when
        boolean contains = config.contains("missingKey");

        // then
        assertFalse(contains);
    }

    @Test
    @DisplayName("loads initial properties from the constructor")
    void loadsInitialProperties() {
        // given
        Map<String, String> initialProperties = Map.of("initialKey", "initialValue");
        NaiveConfig config = new NaiveConfig(initialProperties);

        // when
        config.get("initialKey");

        // then
        assertTrue(config.contains("initialKey"));
        assertFalse(config.get("initialKey").isEmpty());
    }

    @Test
    @DisplayName("does not expose internal mutable map")
    void doesNotExposeInternalMutableMap() {
        // given
        NaiveConfig config = new NaiveConfig();

        // when
        Map<String, String> internalMap = config.asMap();

        // then
        assertThrows(UnsupportedOperationException.class, () -> internalMap.put("key", "value"));
    }

    @Test
    @DisplayName("constructor copies input map")
    void constructorCopiesInputMap() {
        // given
        Map<String, String> initialProperties = new java.util.HashMap<>();
        initialProperties.put("app.server.port", "8080");
        NaiveConfig config = new NaiveConfig(initialProperties);

        // when
        initialProperties.put("app.server.port", "9090");

        // then
        assertEquals("8080", config.get("app.server.port").orElseThrow());
    }

    @Test
    @DisplayName("put method throws NullPointerException when key is null")
    void testPutWithNullKey() {
        NaiveConfig config = new NaiveConfig();
        assertThrows(NullPointerException.class, () -> config.put(null, "value"));
    }

    @Test
    @DisplayName("put method throws NullPointerException when value is null")
    void testPutWithNullValue() {
        NaiveConfig config = new NaiveConfig();
        assertThrows(NullPointerException.class, () -> config.put("key", null));
    }

    @Test
    @DisplayName("put method throws IllegalArgumentException when key is blank")
    void testPutWithBlankKey() {
        NaiveConfig config = new NaiveConfig();
        assertThrows(IllegalArgumentException.class, () -> config.put("   ", "value"));
    }

    @Test
    @DisplayName("get method throws NullPointerException when key is null")
    void testGetWithNullKey() {
        NaiveConfig config = new NaiveConfig();
        assertThrows(NullPointerException.class, () -> config.get(null));
    }

    @Test
    @DisplayName("contains method throws NullPointerException when key is null")
    void testContainsWithNullKey() {
        NaiveConfig config = new NaiveConfig();
        assertThrows(NullPointerException.class, () -> config.contains(null));
    }

    @Test
    @DisplayName("put method overwrites existing value for the same key")
    void testPutOverwritesExistingValue() {
        NaiveConfig config = new NaiveConfig();
        config.put("key", "value1");
        config.put("key", "value2");
        assertEquals("value2", config.get("key").orElseThrow());
    }

    @Test
    @DisplayName("constructor rejects blank key")
    void constructorRejectsBlankKey() {
        Map<String, String> initialProperties = Map.of("   ", "value");
        assertThrows(IllegalArgumentException.class, () -> new NaiveConfig(initialProperties));
    }

    @Test
    @DisplayName("constructor rejects null key")
    void constructorRejectsNullKey() {
        Map<String, String> initialProperties = new HashMap<>();
        initialProperties.put(null, "value");
        assertThrows(NullPointerException.class, () -> new NaiveConfig(initialProperties));
    }

    @Test
    @DisplayName("constructor rejects null value")
    void constructorRejectsNullValue() {
        Map<String, String> initialProperties = new HashMap<>();
        initialProperties.put("key", null);
        assertThrows(NullPointerException.class, () -> new NaiveConfig(initialProperties));
    }

    @Test
    @DisplayName("get method rejects blank key")
    void getRejectsBlankKey() {
        NaiveConfig config = new NaiveConfig();
        assertThrows(IllegalArgumentException.class, () -> config.get("   "));
    }

    @Test
    @DisplayName("contains method rejects blank key")
    void containsRejectsBlankKey() {
        NaiveConfig config = new NaiveConfig();
        assertThrows(IllegalArgumentException.class, () -> config.contains("   "));
    }
}