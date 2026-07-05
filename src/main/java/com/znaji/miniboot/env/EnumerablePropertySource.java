package com.znaji.miniboot.env;

import java.util.Map;

public interface EnumerablePropertySource extends PropertySource {

    Map<String, String> asMap();
}
