package com.flipkart.zjsonpatch;

public abstract class BasicNode implements Node {

    public boolean isArray() {
        return getNodeType() == NodeType.ARRAY;
    }

    public boolean isObject() {
        return getNodeType() == NodeType.OBJECT;
    }
}
