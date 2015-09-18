# This is an implementation of  [RFC 6902 JSON Patch](http://tools.ietf.org/html/rfc6902) written in Java.

##Description & Use-Cases
- Java Library to find / apply JSON Patches according to RFC 6902.
- JSON Patch defines a JSON document structure for representing changes to a JSON document.
- It can be used to avoid sending a whole document when only a part has changed, thus reducing network bandwidth requirements if data (in json format) is required to send across multiple systems over network or in case of multi DC transfer.
- When used in combination with the HTTP PATCH method as per [RFC 5789 HTTP PATCH](http://tools.ietf.org/html/rfc5789), it will do partial updates for HTTP APIs in a standard  way.

##Complexity
- To find JsonPatch : Ω(N+M) ,N and M represnets number of keys in first and second json respectively / O(summation of la*lb) where la , lb represents jsonArray of length la / lb of against same key in first and second json ,since LCS is used to find difference between 2 json arrays there of order of quadratic.
- To Optimize Diffs ( compact move and remove into Move ) : Ω(D) / O(D*D) where D represents number of diffs obtained before compaction into Move operation.
- To Apply Diff : O(D) where D represents number of diffs

## API Usage

### Obtaining Json Diff as patch
```java
// With Jackson...
JsonNode patch = JacksonJsonDiff.asJson(JsonNode source, JsonNode target);

// With generic bson Documents...
// Return value is a list of Documents
List patch = BsonDiff.asJson(Document source, Document target);

// With "typed" bson...
BsonArray patch = BsonDiff.asJson(BsonValue source, BsonValue target);
```
Computes and returns a JSON Patch from source  to target,
Both source and target must be either valid JSON objects or  arrays or values.
Further, if resultant patch is applied to source, it will yield target.

The algorithm which computes this JsonPatch currently generates following operations as per rfc 6902 -
 - ADD
 - REMOVE
 - REPLACE
 - MOVE


### Apply Json Patch
```java
// With Jackson...
JsonNode result = JacksonJsonPatch.apply(JsonNode patch, JsonNode source);

// With generic bson Documents...
// Return value is a list of Documents
Document result = BsonPatch.asJson(List patch, Document source);

// With "typed" bson...
BsonValue patch = BsonDiff.asJson(BsonArray patch, BsonValue source);
```
Given a Patch, it apply it to source Json and return a target json which can be ( json object or array or value ). This operation  performed on a clone of source json ( thus, source json is untouched and can be used further).

### Example
First Json
```json
{"a": 0,"b": [1,2]}
```

Second json ( the json to obtain )
```json
 {"b": [1,2,0]}
```
Following patch will be returned:
```json
[{"op":"MOVE","from":"/a","path":"/b/2","value":0}]
```
here o represents Operation, p represent fromPath from where value should be moved, tp represents toPath where value should be moved and v represents value to move.


### Tests:
1. 100+ selective hardcoded different input jsons , with their driver test classes present under /test directory.
2. Apart from selective input, a deterministic random json generator is present under ( TestDataGenerator.java ),  and its driver test class method is JsonDiffTest.testGeneratedJsonDiff().
