package com.flipkart.zjsonpatch.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.flipkart.zjsonpatch.JsonDiff;

public class JacksonJsonDiff {
    public static ArrayNode asJson(JsonNode source, JsonNode target) {
        return ((JacksonNode.Array) JsonDiff.asJson(
                JacksonNodeFactory.get(source),
                JacksonNodeFactory.get(target)))
                        .getArrayNode();
    }
}
