package com.flipkart.zjsonpatch;

public interface NodeFactory {
    Node.Array arrayNode();
    Node.Object objectNode();
    Node primitive(String value);
}
