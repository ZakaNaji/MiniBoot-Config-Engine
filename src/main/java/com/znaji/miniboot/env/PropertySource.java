package com.znaji.miniboot.env;

import java.util.Map;
import java.util.Optional;

public interface PropertySource {

    String name();

    Optional<String> get(String key);

    boolean contains(String key);

    Map<String, String> asMap();
}
