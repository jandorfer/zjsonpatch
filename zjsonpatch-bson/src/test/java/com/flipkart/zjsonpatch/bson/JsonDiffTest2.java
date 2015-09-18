package com.flipkart.zjsonpatch.bson;

import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

public class JsonDiffTest2 {
    static List jsonNode;

    @BeforeClass
    public static void beforeClass() throws IOException {
        String path = "/testdata/diff.json";
        InputStream resourceAsStream = JsonDiffTest.class.getResourceAsStream(path);
        String testData = "{\"tests\":" + IOUtils.toString(resourceAsStream, "UTF-8") + "}";
        jsonNode = Document.parse(testData).get("tests", List.class);
    }

    @Test
    public void testPatchAppliedCleanly() throws Exception {
        for (int i = 0; i < jsonNode.size(); i++) {
            Document spec = (Document) jsonNode.get(i);
            Document first = spec.get("first", Document.class);
            Document second = spec.get("second", Document.class);
            List patch = spec.get("patch", List.class);
            String message = spec.get("message").toString();

            System.out.println("Test # " + i);
            System.out.println(first.toJson());
            System.out.println(second.toJson());
            Document out = new Document();
            out.put("patch", patch);
            System.out.println(out.toJson());

            Document secondPrime = BsonPatch.apply(patch, first);
            System.out.println(secondPrime.toJson());
            Assert.assertThat(message, secondPrime, equalTo(second));
        }

    }
}
