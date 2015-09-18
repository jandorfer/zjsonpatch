package com.flipkart.zjsonpatch.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.flipkart.zjsonpatch.Node;
import com.flipkart.zjsonpatch.NodeFactory;

public class JacksonNodeFactory implements NodeFactory {

    public static JacksonNode get(JsonNode from) {
        switch (JacksonNodeType.getNodeType(from)) {
            case ARRAY:
                return new JacksonNode.Array((ArrayNode) from);
            case OBJECT:
                return new JacksonNode.Object((ObjectNode) from);
            default:
                return new JacksonNode.Primitive(from);
        }
    }

    @Override
    public Node.Array arrayNode() {
        return (Node.Array) get(JsonNodeFactory.instance.arrayNode());
    }

    @Override
    public Node.Object objectNode() {
        return (Node.Object) get(JsonNodeFactory.instance.objectNode());
    }

    @Override
    public Node primitive(String value) {
        return get(JsonNodeFactory.instance.textNode(value));
    }
}
