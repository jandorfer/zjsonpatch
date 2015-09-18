package com.flipkart.zjsonpatch.bson;

import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

/**
 * Unit test
 */
public class JsonDiffTest {
    static List jsonNode;

    @BeforeClass
    public static void beforeClass() throws IOException {
        String path = "/testdata/sample.json";
        InputStream resourceAsStream = JsonDiffTest.class.getResourceAsStream(path);
        String testData = "{\"tests\":" + IOUtils.toString(resourceAsStream, "UTF-8") + "}";
        jsonNode = Document.parse(testData).get("tests", List.class);
    }

    @Test
    public void testSampleJsonDiff() throws Exception {
        for (int i = 0; i < jsonNode.size(); i++) {
            Document spec = (Document) jsonNode.get(i);
            Document first = safeGet(spec, "first");
            Document second = safeGet(spec, "second");

            System.out.println("Test # " + i);
            System.out.println(first.toJson());
            System.out.println(second.toJson());

            List actualPatch = BsonDiff.asJson(first, second);


            Document out = new Document();
            out.put("patch", actualPatch);
            System.out.println(out.toJson());

            Document secondPrime = BsonPatch.apply(actualPatch, first);
            System.out.println(secondPrime.toJson());
            Assert.assertTrue(second.equals(secondPrime));
        }
    }

    @Test
    public void testGeneratedJsonDiff() throws Exception {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            Document first = TestDataGenerator.generate(random.nextInt(10));
            Document second = TestDataGenerator.generate(random.nextInt(10));

            List actualPatch = BsonDiff.asJson(first, second);
            System.out.println("Test # " + i);

            System.out.println(first.toJson());
            System.out.println(second.toJson());
            Document out = new Document();
            out.put("patch", actualPatch);
            System.out.println(out.toJson());

            Document secondPrime = BsonPatch.apply(actualPatch, first);
            System.out.println(secondPrime.toJson());
            Assert.assertTrue(second.equals(secondPrime));
        }
    }

    /**
     * Bson lib doesn't like arrays as the root element of a "document".
     */
    public static Document safeGet(Document source, String member) {
        Object result = source.get(member);
        if (result instanceof Document) return (Document) result;
        return new Document("arr", result);
    }
}
