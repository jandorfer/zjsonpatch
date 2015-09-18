package com.flipkart.zjsonpatch.bson;

import org.apache.commons.io.IOUtils;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.core.IsEqual.equalTo;

public abstract class AbstractTest {
    static BsonArray jsonNode;
    static BsonArray errorNode;

    protected AbstractTest(String fileName) throws IOException {
        String path = "/testdata/" + fileName + ".json";
        InputStream resourceAsStream = JsonDiffTest.class.getResourceAsStream(path);
        String testData = IOUtils.toString(resourceAsStream, "UTF-8");

        BsonDocument testsNode = BsonDocument.parse(testData);
        jsonNode = testsNode.getArray("ops");
        errorNode = testsNode.getArray("errors");
    }

    @Test
    public void testPatchAppliedCleanly() throws Exception {
        for (int i = 0; i < jsonNode.size(); i++) {
            BsonDocument testSpecification = (BsonDocument) jsonNode.get(i);
            BsonValue first = testSpecification.get("node");
            BsonValue second = testSpecification.get("expected");
            BsonArray patch = testSpecification.getArray("op");
            String message = testSpecification.containsKey("message") ? testSpecification.get("message").toString() : "";

            System.out.println("Test # " + i);
            System.out.println(first);
            System.out.println(second);
            System.out.println(patch);

            BsonValue secondPrime = BsonPatch.apply(patch, first);
            System.out.println(secondPrime);
            Assert.assertThat(message, secondPrime, equalTo(second));
        }
    }

    @Test(expected = RuntimeException.class)
    public void testErrorsAreCorrectlyReported() {
        for (int i = 0; i < errorNode.size(); i++) {
            BsonDocument testSpecification = (BsonDocument) errorNode.get(i);
            BsonDocument first = (BsonDocument) testSpecification.get("node");
            BsonArray patch = testSpecification.getArray("op");

            System.out.println("Error Test # " + i);
            System.out.println(first);
            System.out.println(patch);

            BsonValue secondPrime = BsonPatch.apply(patch, first);
            System.out.println(secondPrime);
        }

        if (errorNode.size() == 0) {
            throw new RuntimeException("dummy exception");
        }
    }
}
