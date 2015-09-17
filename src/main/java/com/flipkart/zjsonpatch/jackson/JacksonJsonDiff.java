package com.flipkart.zjsonpatch.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.flipkart.zjsonpatch.JsonDiff;

public class JacksonJsonDiff {

    public static JsonNode asJson(JsonNode source, JsonNode target) {
        return ((JacksonNode) JsonDiff.asJson(
                JacksonNodeFactory.get(source),
                JacksonNodeFactory.get(target)))
                        .getBasis();
    }
}
