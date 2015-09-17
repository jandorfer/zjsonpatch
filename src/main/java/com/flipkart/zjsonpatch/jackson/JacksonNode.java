package com.flipkart.zjsonpatch.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.flipkart.zjsonpatch.BasicNode;
import com.flipkart.zjsonpatch.Node;
import com.flipkart.zjsonpatch.NodeFactory;
import com.flipkart.zjsonpatch.NodeType;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;

import java.util.Iterator;

public abstract class JacksonNode extends BasicNode implements Node {

    public static final JacksonNodeFactory FACTORY = new JacksonNodeFactory();

    private final JsonNode basis;

    protected JacksonNode(JsonNode basis) {
        this.basis = basis;
    }

    public JsonNode getBasis() {
        return this.basis;
    }

    @Override
    public String toString() {
        return getBasis().toString();
    }

    @Override
    public int hashCode() {
        return getBasis().hashCode();
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        return !(obj == null || !(obj instanceof JacksonNode)) &&
                getBasis().equals(((JacksonNode) obj).getBasis());
    }

    @Override
    public Node deepCopy() {
        return JacksonNodeFactory.get(basis.deepCopy());
    }

    public NodeFactory getFactory() {
        return FACTORY;
    }

    public static class Array extends JacksonNode implements Node.Array {

        private final ArrayNode arrayNode;

        public Array(ArrayNode basis) {
            super(basis);
            this.arrayNode = basis;
        }

        @Override
        public int size() {
            return this.arrayNode.size();
        }

        @Override
        public Node get(int index) {
            return JacksonNodeFactory.get(this.arrayNode.get(index));
        }

        @Override
        public void add(Node entry) {
            this.arrayNode.add(safeConvert(entry));
        }

        @Override
        public void insert(int atIndex, Node entry) {
            this.arrayNode.insert(atIndex, safeConvert(entry));
        }

        @Override
        public void set(int atIndex, Node newValue) {
            this.arrayNode.set(atIndex, safeConvert(newValue));
        }

        @Override
        public void remove(int atIndex) {
            this.arrayNode.remove(atIndex);
        }

        @Override
        public NodeType getNodeType() {
            return NodeType.ARRAY;
        }

        @Override
        public Iterator<Node> iterator() {
            return Iterators.transform(this.arrayNode.iterator(), new Function<JsonNode, Node>() {
                @Override
                public Node apply(JsonNode jsonNode) {
                    return JacksonNodeFactory.get(jsonNode);
                }
            });
        }
    }

    public static class Object extends JacksonNode implements Node.Object {

        private final ObjectNode objectNode;

        public Object(ObjectNode basis) {
            super(basis);
            this.objectNode = basis;
        }

        @Override
        public Iterator<String> fieldNames() {
            return this.objectNode.fieldNames();
        }

        @Override
        public boolean has(String fieldName) {
            return this.objectNode.has(fieldName);
        }

        @Override
        public Node get(String fieldName) {
            return JacksonNodeFactory.get(this.objectNode.get(fieldName));
        }

        @Override
        public void put(String fieldName, Node value) {
            this.objectNode.put(fieldName, safeConvert(value));
        }

        @Override
        public void remove(String fieldName) {
            this.objectNode.remove(fieldName);
        }

        @Override
        public NodeType getNodeType() {
            return NodeType.OBJECT;
        }
    }

    public static class Primitive extends JacksonNode {

        public Primitive(JsonNode basis) {
            super(basis);
        }

        @Override
        public NodeType getNodeType() {
            return JacksonNodeType.getNodeType(this.getBasis());
        }
    }

    protected static JsonNode safeConvert(Node entry) {
        return entry == null || !(entry instanceof JacksonNode) ? null : ((JacksonNode) entry).getBasis();
    }
}
