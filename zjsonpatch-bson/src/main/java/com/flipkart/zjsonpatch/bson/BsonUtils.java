package com.flipkart.zjsonpatch.bson;

import org.bson.*;
import org.bson.codecs.BsonValueCodecProvider;
import org.bson.codecs.DocumentCodecProvider;
import org.bson.codecs.ValueCodecProvider;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class BsonUtils {
    public static final CodecRegistry DEFAULT_CODEC_REGISTRY =
            CodecRegistries.fromProviders(Arrays.asList(new CodecProvider[]{
                    new ValueCodecProvider(),
                    new DocumentCodecProvider(),
                    new BsonValueCodecProvider()}));

    public static BsonValue deepCopy(BsonValue value) {
        switch (value.getBsonType()) {
            case ARRAY:
                return value.asArray().getValues().stream()
                        .map(BsonUtils::deepCopy)
                        .collect(Collectors.toCollection(BsonArray::new));
            case BINARY:
                return new BsonBinary(value.asBinary().getType(), value.asBinary().getData());
            case BOOLEAN:
                return new BsonBoolean(value.asBoolean().getValue());
            case DATE_TIME:
                return new BsonDateTime(value.asDateTime().getValue());
            case DB_POINTER:
                return new BsonDbPointer(value.asDBPointer().getNamespace(),
                        new ObjectId(value.asDBPointer().getId().toByteArray()));
            case DOCUMENT:
                BsonDocument doc = new BsonDocument();
                for (Map.Entry<String, BsonValue> entry : value.asDocument().entrySet()) {
                    doc.append(entry.getKey(), entry.getValue());
                }
                return doc;
            case DOUBLE:
                return new BsonDouble(value.asDouble().getValue());
            case INT32:
                return new BsonInt32(value.asInt32().getValue());
            case INT64:
                return new BsonInt64(value.asInt64().getValue());
            case JAVASCRIPT:
                return new BsonJavaScript(value.asJavaScript().getCode());
            case JAVASCRIPT_WITH_SCOPE:
                return new BsonJavaScriptWithScope(value.asJavaScriptWithScope().getCode(),
                        (BsonDocument) deepCopy(value.asJavaScriptWithScope().getScope()));
            case MAX_KEY:
                return new BsonMaxKey();
            case MIN_KEY:
                return new BsonMinKey();
            case NULL:
                return new BsonNull();
            case OBJECT_ID:
                return new BsonObjectId(new ObjectId(value.asObjectId().getValue().toByteArray()));
            case REGULAR_EXPRESSION:
                return new BsonRegularExpression(value.asRegularExpression().getPattern(),
                        value.asRegularExpression().getOptions());
            case STRING:
                return new BsonString(value.asString().getValue());
            case SYMBOL:
                return new BsonSymbol(value.asSymbol().getSymbol());
            case TIMESTAMP:
                return new BsonTimestamp(value.asTimestamp().getTime(), value.asTimestamp().getInc());
            case END_OF_DOCUMENT:
            case UNDEFINED:
            default:
                return null;
        }
    }

    public static Object unwrap(BsonValue value) {
        switch (value.getBsonType()) {
            case ARRAY:
                return value.asArray().getValues().stream()
                        .map(BsonUtils::unwrap)
                        .collect(Collectors.toList());
            case BINARY:
                return value.asBinary().getData();
            case BOOLEAN:
                return value.asBoolean().getValue();
            case DATE_TIME:
                return new Date(value.asDateTime().getValue());
            case DB_POINTER:
                return value.asDBPointer().toString();
            case DOCUMENT:
                Document doc = new Document();
                for (Map.Entry<String, BsonValue> entry : value.asDocument().entrySet()) {
                    doc.append(entry.getKey(), unwrap(entry.getValue()));
                }
                return doc;
            case DOUBLE:
                return value.asDouble().getValue();
            case INT32:
                return value.asInt32().getValue();
            case INT64:
                return value.asInt64().getValue();
            case JAVASCRIPT:
                return value.asJavaScript().toString();
            case JAVASCRIPT_WITH_SCOPE:
                return value.asJavaScriptWithScope().toString();
            case MAX_KEY:
                return value.toString();
            case MIN_KEY:
                return value.toString();
            case NULL:
                return null;
            case OBJECT_ID:
                return value.asObjectId().getValue();
            case REGULAR_EXPRESSION:
                return value.asRegularExpression().toString();
            case STRING:
                return value.asString().getValue();
            case SYMBOL:
                return value.asSymbol().getSymbol();
            case TIMESTAMP:
                return value.asTimestamp().toString();
            case END_OF_DOCUMENT:
            case UNDEFINED:
            default:
                return null;
        }
    }
}
