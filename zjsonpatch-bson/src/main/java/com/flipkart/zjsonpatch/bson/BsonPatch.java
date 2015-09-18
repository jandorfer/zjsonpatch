package com.flipkart.zjsonpatch.bson;

import com.flipkart.zjsonpatch.JsonPatch;
import org.bson.BsonArray;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.List;

public class BsonPatch {

    public static Document apply(List patch, Document source) {
        return apply(patch, source, null);
    }

    public static Document apply(List patch, Document source, CodecRegistry codecRegistry) {
        codecRegistry = codecRegistry == null ? BsonUtils.DEFAULT_CODEC_REGISTRY : codecRegistry;
        BsonArray arr = new Document("v", patch).toBsonDocument(null, codecRegistry).getArray("v");
        return (Document) BsonUtils.unwrap(
                apply(arr, source.toBsonDocument(null, codecRegistry)));
    }

    public static BsonValue apply(BsonArray patch, BsonValue source) {
        return ((BsonNode) JsonPatch.apply(
                new BsonNode.Array(patch),
                BsonNodeFactory.get(source)))
                        .getBasis();
    }
}
