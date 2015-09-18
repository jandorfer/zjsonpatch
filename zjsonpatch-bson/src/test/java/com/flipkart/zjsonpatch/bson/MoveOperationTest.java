package com.flipkart.zjsonpatch.bson;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MoveOperationTest extends AbstractTest {
    public MoveOperationTest() throws IOException {
        super("move");
    }

    @Test
    public void testMoveValueGeneratedHasNoValue() throws IOException {
        BsonDocument jsonNode1 = BsonDocument.parse("{ \"foo\": { \"bar\": \"baz\", \"waldo\": \"fred\" }, \"qux\": { \"corge\": \"grault\" } }");
        BsonDocument jsonNode2 = BsonDocument.parse("{ \"foo\": { \"bar\": \"baz\" }, \"qux\": { \"corge\": \"grault\", \"thud\": \"fred\" } }");
        BsonArray patch = BsonDocument.parse("{\"v\":[{\"op\":\"move\",\"from\":\"/foo/waldo\",\"path\":\"/qux/thud\"}]}")
                .getArray("v");

        BsonArray diff = BsonDiff.asJson(jsonNode1, jsonNode2);

        assertThat(diff, equalTo(patch));
    }

    @Test
    public void testMoveArrayGeneratedHasNoValue() throws IOException {
        BsonDocument jsonNode1 = BsonDocument.parse("{ \"foo\": [ \"all\", \"grass\", \"cows\", \"eat\" ] }");
        BsonDocument jsonNode2 = BsonDocument.parse("{ \"foo\": [ \"all\", \"cows\", \"eat\", \"grass\" ] }");
        BsonArray patch = BsonDocument.parse("{\"v\":[{\"op\":\"move\",\"from\":\"/foo/1\",\"path\":\"/foo/3\"}]}")
                .getArray("v");

        BsonArray diff = BsonDiff.asJson(jsonNode1, jsonNode2);

        assertThat(diff, equalTo(patch));
    }
}
