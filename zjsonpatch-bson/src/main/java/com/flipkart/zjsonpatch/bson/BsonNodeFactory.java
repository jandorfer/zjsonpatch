package com.flipkart.zjsonpatch.bson;

import com.flipkart.zjsonpatch.Node;
import com.flipkart.zjsonpatch.NodeFactory;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;

public class BsonNodeFactory implements NodeFactory {

    public static BsonNode get(BsonValue from) {
        if (from == null) return null;
        switch (BsonNodeType.getNodeType(from)) {
            case ARRAY:
                return new BsonNode.Array((BsonArray) from);
            case OBJECT:
                return new BsonNode.Object((BsonDocument) from);
            default:
                return new BsonNode.Primitive(from);
        }
    }

    @Override
    public Node.Array arrayNode() {
        return new BsonNode.Array(new BsonArray());
    }

    @Override
    public Node.Object objectNode() {
        return new BsonNode.Object(new BsonDocument());
    }

    @Override
    public Node primitive(String value) {
        return new BsonNode.Primitive(new BsonString(value));
    }
}
