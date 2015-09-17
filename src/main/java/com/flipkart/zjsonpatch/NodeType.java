package com.flipkart.zjsonpatch;

public enum NodeType {
    ARRAY("array"),
    BOOLEAN("boolean"),
    BINARY("binary"),
    DATEIIME("datetime"),
    INTEGER("integer"),
    NULL("null"),
    NUMBER("number"),
    OBJECT("object"),
    STRING("string");

    /**
     * The name for this type, as encountered in a JSON schema
     */
    private final String name;

    NodeType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
