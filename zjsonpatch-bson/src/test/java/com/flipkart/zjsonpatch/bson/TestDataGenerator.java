package com.flipkart.zjsonpatch.bson;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestDataGenerator {
    private static Random random = new Random();
    private static List<String> name = Arrays.asList("summers", "winters", "autumn", "spring", "rainy");
    private static List<Integer> age = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    private static List<String> gender = Arrays.asList("male", "female");
    private static List<String> country = Arrays.asList("india", "aus", "nz", "sl", "rsa", "wi", "eng", "bang", "pak");
    private static List<String> friends = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j");

    public static Document generate(int count) {
        List<Document> jsonNode = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Document objectNode = new Document();
            objectNode.put("name", name.get(random.nextInt(name.size())));
            objectNode.put("age", age.get(random.nextInt(age.size())));
            objectNode.put("gender", gender.get(random.nextInt(gender.size())));
            List countryNode = country.subList(random.nextInt(country.size() / 2), (country.size() / 2) + random.nextInt(country.size() / 2));
            objectNode.put("country", countryNode);
            List friendNode = friends.subList(random.nextInt(friends.size() / 2), (friends.size() / 2) + random.nextInt(friends.size() / 2));
            objectNode.put("friends", friendNode);
            jsonNode.add(objectNode);
        }
        Document holder = new Document();
        holder.put("people", jsonNode);
        return holder;
    }
}
