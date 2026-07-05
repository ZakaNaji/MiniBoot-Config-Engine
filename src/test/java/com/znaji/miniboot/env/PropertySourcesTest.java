package com.znaji.miniboot.env;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PropertySourcesTest {

    private PropertySources propertySources;

    @BeforeEach
    void setUp() {
        propertySources = new PropertySources();
    }


    @Test
    @DisplayName("addLast appends source")
    void testAddLast() {
        PropertySource source1 = new MapPropertySource("source1", Map.of("key1", "value1"));
        PropertySource source2 = new MapPropertySource("source2", Map.of("key2", "value2"));

        propertySources.addLast(source1);
        propertySources.addLast(source2);

        List<PropertySource> sourcesList = propertySources.asList();

        assertEquals(2, propertySources.size());
        assertEquals("source1", sourcesList.get(0).name());
        assertEquals("source2", sourcesList.get(1).name());
    }

    @Test
    @DisplayName("addFirst prepends source")
    void testAddFirst() {
        PropertySource source1 = new MapPropertySource("source1", Map.of("key1", "value1"));
        PropertySource source2 = new MapPropertySource("source2", Map.of("key2", "value2"));

        propertySources.addFirst(source1);
        propertySources.addFirst(source2);

        List<PropertySource> sourcesList = propertySources.asList();

        assertEquals(2, propertySources.size());
        assertEquals("source2", sourcesList.get(0).name());
        assertEquals("source1", sourcesList.get(1).name());
    }


    @Test
    @DisplayName("get returns value from first matching source")
    void testGetReturnsValueFromFirstMatchingSource() {
        PropertySource source1 = new MapPropertySource("source1", Map.of("key1", "value1"));
        PropertySource source2 = new MapPropertySource("source2", Map.of("key1", "value2"));

        propertySources.addLast(source1);
        propertySources.addLast(source2);

        assertEquals("value1", propertySources.get("key1").orElse(null));
    }

    @Test
    @DisplayName("get falls back to lower-priority source when higher source does not contain key")
    void testGetFallsBackToLowerPrioritySource() {
        PropertySource source1 = new MapPropertySource("source1", Map.of("key1", "value1"));
        PropertySource source2 = new MapPropertySource("source2", Map.of("key2", "value2"));

        propertySources.addLast(source1);
        propertySources.addLast(source2);

        assertEquals("value2", propertySources.get("key2").orElse(null));
    }

    @Test
    @DisplayName("get returns Optional.empty when no source contains key")
    void testGetReturnsEmptyWhenNoSourceContainsKey() {
        PropertySource source1 = new MapPropertySource("source1", Map.of("key1", "value1"));
        PropertySource source2 = new MapPropertySource("source2", Map.of("key2", "value2"));

        propertySources.addLast(source1);
        propertySources.addLast(source2);

        assertTrue(propertySources.get("nonexistentKey").isEmpty());
    }

    @Test
    @DisplayName("contains returns true when any source contains key")
    void testContainsReturnsTrueWhenAnySourceContainsKey() {
        PropertySource source1 = new MapPropertySource("source1", Map.of("key1", "value1"));
        PropertySource source2 = new MapPropertySource("source2", Map.of("key2", "value2"));

        propertySources.addLast(source1);
        propertySources.addLast(source2);

        assertTrue(propertySources.contains("key1"));
        assertTrue(propertySources.contains("key2"));
    }

    @Test
    @DisplayName("contains returns false when no source contains key")
    void testContainsReturnsFalseWhenNoSourceContainsKey() {
        PropertySource source1 = new MapPropertySource("source1", Map.of("key1", "value1"));
        PropertySource source2 = new MapPropertySource("source2", Map.of("key2", "value2"));

        propertySources.addLast(source1);
        propertySources.addLast(source2);

        assertFalse(propertySources.contains("nonexistentKey"));
    }

    @Test
    @DisplayName("asList returns sources in priority order")
    void testAsListReturnsSourcesInPriorityOrder() {
        PropertySource source1 = new MapPropertySource("source1", Map.of("key1", "value1"));
        PropertySource source2 = new MapPropertySource("source2", Map.of("key2", "value2"));

        propertySources.addLast(source1);
        propertySources.addFirst(source2);

        List<PropertySource> sourcesList = propertySources.asList();

        assertEquals(2, sourcesList.size());
        assertEquals("source2", sourcesList.get(0).name());
        assertEquals("source1", sourcesList.get(1).name());
    }

    @Test
    @DisplayName("asList does not allow external mutation")
    void testAsListDoesNotAllowExternalMutation() {
        PropertySource source1 = new MapPropertySource("source1", Map.of("key1", "value1"));
        propertySources.addLast(source1);

        List<PropertySource> sourcesList = propertySources.asList();

        assertThrows(UnsupportedOperationException.class, () -> sourcesList.add(new MapPropertySource("source2", Map.of("key2", "value2"))));
    }

    @Test
    @DisplayName("rejects null source in addFirst")
    void testRejectsNullSourceInAddFirst() {
        assertThrows(IllegalArgumentException.class, () -> propertySources.addFirst(null));
    }

    @Test
    @DisplayName("rejects null source in addLast")
    void testRejectsNullSourceInAddLast() {
        assertThrows(IllegalArgumentException.class, () -> propertySources.addLast(null));
    }

    @Test
    @DisplayName("rejects duplicate source names in addFirst")
    void testRejectsDuplicateSourceNamesInAddFirst() {
        PropertySource source1 = new MapPropertySource("source1", Map.of("key1", "value1"));
        propertySources.addFirst(source1);
        PropertySource duplicateSource = new MapPropertySource("source1", Map.of("key2", "value2"));
        assertThrows(IllegalArgumentException.class, () -> propertySources.addFirst(duplicateSource));
    }

    @Test
    @DisplayName("size returns number of sources")
    void testSizeReturnsNumberOfSources() {
        assertEquals(0, propertySources.size());

        PropertySource source1 = new MapPropertySource("source1", Map.of("key1", "value1"));
        propertySources.addLast(source1);
        assertEquals(1, propertySources.size());

        PropertySource source2 = new MapPropertySource("source2", Map.of("key2", "value2"));
        propertySources.addLast(source2);
        assertEquals(2, propertySources.size());
    }
}