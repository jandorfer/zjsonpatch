package com.flipkart.zjsonpatch;

import java.util.Iterator;

/**
 * Generic wrapper for interactions with JSON nodes.
 */
public interface Node {

    NodeType getNodeType();
    boolean isArray();
    boolean isObject();
    Node deepCopy();

    NodeFactory getFactory();

    interface Array extends Node, Iterable<Node> {
        int size();
        Node get(int index);
        void add(Node entry);
        void insert(int atIndex, Node entry);
        void set(int atIndex, Node newValue);
        void remove(int atIndex);
    }

    interface Object extends Node {
        Iterator<String> fieldNames();
        boolean has(String fieldName);
        Node get(String fieldName);
        void put(String fieldName, Node value);
        void remove(String fieldName);
    }
}