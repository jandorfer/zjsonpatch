package com.flipkart.zjsonpatch;

import java.util.List;

public class Diff {
    private Operation operation;
    private List<Object> path;
    private Node value;
    private List<Object> toPath; //only to be used in move operation

    Diff(Operation operation, List<Object> path, Node value) {
        this.operation = operation;
        this.path = path;
        this.value = value;
    }

    Diff(Operation operation, List<Object> fromPath, Node value, List<Object> toPath) {
        this.operation = operation;
        this.path = fromPath;
        this.value = value;
        this.toPath = toPath;
    }

    public Operation getOperation() {
        return operation;
    }

    public List<Object> getPath() {
        return path;
    }

    public Node getValue() {
        return value;
    }

    public static Diff generateDiff(Operation replace, List<Object> path, Node target) {
        return new Diff(replace, path, target);
    }

    List<Object> getToPath() {
        return toPath;
    }
}
