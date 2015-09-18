package com.flipkart.zjsonpatch.bson;

import com.flipkart.zjsonpatch.JsonDiff;
import org.bson.BsonArray;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.List;

public class BsonDiff {

    public static List asJson(Document source, Document target) {
        return asJson(source, target, null);
    }

    public static List asJson(Document source, Document target, CodecRegistry codecRegistry) {
        codecRegistry = codecRegistry == null ? BsonUtils.DEFAULT_CODEC_REGISTRY : codecRegistry;
        return (List) BsonUtils.unwrap(
                asJson(source.toBsonDocument(null, codecRegistry),
                       target.toBsonDocument(null, codecRegistry)));
    }

    public static BsonArray asJson(BsonValue source, BsonValue target) {
        return ((BsonNode.Array) JsonDiff.asJson(
                BsonNodeFactory.get(source),
                BsonNodeFactory.get(target)))
                        .getArrayNode();
    }
}
