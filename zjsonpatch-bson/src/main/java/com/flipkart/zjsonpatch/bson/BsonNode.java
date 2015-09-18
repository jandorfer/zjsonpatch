package com.flipkart.zjsonpatch.bson;

import com.flipkart.zjsonpatch.BasicNode;
import com.flipkart.zjsonpatch.Node;
import com.flipkart.zjsonpatch.NodeFactory;
import com.flipkart.zjsonpatch.NodeType;
import com.google.common.collect.Iterators;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.Iterator;

public abstract class BsonNode extends BasicNode implements Node {

    public static final BsonNodeFactory FACTORY = new BsonNodeFactory();

    private final BsonValue basis;

    protected BsonNode(BsonValue basis) {
        this.basis = basis;
    }

    public BsonValue getBasis() {
        return this.basis;
    }

    @Override
    public String toString() {
        BsonDocument temp = new BsonDocument();
        temp.put("v", getBasis());
        String result = temp.toJson();
        result = result.substring(result.indexOf(":") + 1);
        result = result.substring(0, result.lastIndexOf("}"));
        return result.trim();
    }

    @Override
    public int hashCode() {
        return getBasis().hashCode();
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        return !(obj == null || !(obj instanceof BsonNode)) &&
                getBasis().equals(((BsonNode) obj).getBasis());
    }

    @Override
    public Node deepCopy() {
        return BsonNodeFactory.get(BsonUtils.deepCopy(getBasis()));
    }

    public NodeFactory getFactory() {
        return FACTORY;
    }

    public static class Array extends BsonNode implements Node.Array {

        private final BsonArray arrayNode;

        public Array(BsonArray basis) {
            super(basis);
            this.arrayNode = basis;
        }

        public BsonArray getArrayNode() {
            return this.arrayNode;
        }

        @Override
        public int size() {
            return this.arrayNode.size();
        }

        @Override
        public Node get(int index) {
            return BsonNodeFactory.get(this.arrayNode.get(index));
        }

        @Override
        public void add(Node entry) {
            this.arrayNode.add(safeConvert(entry));
        }

        @Override
        public void insert(int atIndex, Node entry) {
            this.arrayNode.add(atIndex, safeConvert(entry));
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
            return Iterators.transform(this.arrayNode.iterator(), BsonNodeFactory::get);
        }
    }

    public static class Object extends BsonNode implements Node.Object {

        private final BsonDocument objectNode;

        public Object(BsonDocument basis) {
            super(basis);
            this.objectNode = basis;
        }

        @Override
        public Iterator<String> fieldNames() {
            return this.objectNode.keySet().iterator();
        }

        @Override
        public boolean has(String fieldName) {
            return this.objectNode.containsKey(fieldName);
        }

        @Override
        public Node get(String fieldName) {
            return BsonNodeFactory.get(this.objectNode.get(fieldName));
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

        @Override
        public String toString() {
            return this.objectNode.toJson();
        }
    }

    public static class Primitive extends BsonNode {

        public Primitive(BsonValue basis) {
            super(basis);
        }

        @Override
        public NodeType getNodeType() {
            return BsonNodeType.getNodeType(this.getBasis());
        }
    }

    protected static BsonValue safeConvert(Node entry) {
        return entry == null || !(entry instanceof BsonNode) ? null : ((BsonNode) entry).getBasis();
    }
}