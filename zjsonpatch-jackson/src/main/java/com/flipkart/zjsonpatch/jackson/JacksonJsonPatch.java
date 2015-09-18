package com.flipkart.zjsonpatch.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.flipkart.zjsonpatch.JsonPatch;

public class JacksonJsonPatch {

    public static JsonNode apply(ArrayNode patch, JsonNode source) {
        return ((JacksonNode) JsonPatch.apply(
                new JacksonNode.Array(patch),
                JacksonNodeFactory.get(source)))
                        .getBasis();
    }
}
