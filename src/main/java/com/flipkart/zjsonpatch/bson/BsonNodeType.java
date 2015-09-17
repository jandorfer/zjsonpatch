package com.flipkart.zjsonpatch.bson;

import com.flipkart.zjsonpatch.NodeType;
import com.google.common.base.Preconditions;
import org.bson.BsonType;
import org.bson.BsonValue;

import java.util.EnumMap;
import java.util.Map;

public class BsonNodeType {

    private static final Map<BsonType, NodeType> TOKEN_MAP
            = new EnumMap<BsonType, NodeType>(BsonType.class);

    static {
        TOKEN_MAP.put(BsonType.ARRAY, NodeType.ARRAY);
        TOKEN_MAP.put(BsonType.BINARY, NodeType.BINARY);
        TOKEN_MAP.put(BsonType.BOOLEAN, NodeType.BOOLEAN);
        TOKEN_MAP.put(BsonType.DATE_TIME, NodeType.DATEIIME);
        //TOKEN_MAP.put(BsonType.DB_POINTER, NodeType.);
        TOKEN_MAP.put(BsonType.DOCUMENT, NodeType.OBJECT);
        TOKEN_MAP.put(BsonType.DOUBLE, NodeType.NUMBER);
        //TOKEN_MAP.put(BsonType.END_OF_DOCUMENT, NodeType.);
        TOKEN_MAP.put(BsonType.INT32, NodeType.INTEGER);
        TOKEN_MAP.put(BsonType.INT64, NodeType.INTEGER);
        //TOKEN_MAP.put(BsonType.JAVASCRIPT, NodeType.);
        //TOKEN_MAP.put(BsonType.JAVASCRIPT_WITH_SCOPE, NodeType.);
        TOKEN_MAP.put(BsonType.NULL, NodeType.NULL);
        TOKEN_MAP.put(BsonType.OBJECT_ID, NodeType.BINARY);
        //TOKEN_MAP.put(BsonType.REGULAR_EXPRESSION, NodeType.);
        TOKEN_MAP.put(BsonType.STRING, NodeType.STRING);
        //TOKEN_MAP.put(BsonType.SYMBOL, NodeType.);
        TOKEN_MAP.put(BsonType.TIMESTAMP, NodeType.DATEIIME);
        //TOKEN_MAP.put(BsonType.UNDEFINED, NodeType.);
    }

    public static NodeType getNodeType(final BsonValue node) {
        final BsonType token = node.getBsonType();
        final NodeType ret = TOKEN_MAP.get(token);
        Preconditions.checkNotNull(ret, "unhandled token type " + token);
        return ret;
    }
}
