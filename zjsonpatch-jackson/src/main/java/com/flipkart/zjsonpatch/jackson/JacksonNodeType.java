package com.flipkart.zjsonpatch.jackson;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.flipkart.zjsonpatch.NodeType;
import com.google.common.base.Preconditions;

import java.util.EnumMap;
import java.util.Map;

public class JacksonNodeType {

    private static final Map<JsonToken, NodeType> TOKEN_MAP
            = new EnumMap<JsonToken, NodeType>(JsonToken.class);

    static {
        TOKEN_MAP.put(JsonToken.START_ARRAY, NodeType.ARRAY);
        TOKEN_MAP.put(JsonToken.VALUE_TRUE, NodeType.BOOLEAN);
        TOKEN_MAP.put(JsonToken.VALUE_FALSE, NodeType.BOOLEAN);
        TOKEN_MAP.put(JsonToken.VALUE_NUMBER_INT, NodeType.INTEGER);
        TOKEN_MAP.put(JsonToken.VALUE_NUMBER_FLOAT, NodeType.NUMBER);
        TOKEN_MAP.put(JsonToken.VALUE_NULL, NodeType.NULL);
        TOKEN_MAP.put(JsonToken.START_OBJECT, NodeType.OBJECT);
        TOKEN_MAP.put(JsonToken.VALUE_STRING, NodeType.STRING);
    }

    public static NodeType getNodeType(final JsonNode node) {
        final JsonToken token = node.asToken();
        final NodeType ret = TOKEN_MAP.get(token);
        Preconditions.checkNotNull(ret, "unhandled token type " + token);
        return ret;
    }
}
