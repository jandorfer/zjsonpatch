package com.flipkart.zjsonpatch.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.flipkart.zjsonpatch.JsonPatch;

public class JacksonJsonPatch {

    public static JsonNode apply(JsonNode patch, JsonNode source) {
        return ((JacksonNode) JsonPatch.apply(
                (JacksonNode.Array) JacksonNodeFactory.get(patch),
                JacksonNodeFactory.get(source)))
                        .getBasis();
    }
}
