package com.example.mongocrudapp.model.repository;

import com.example.mongocrudapp.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final MongoCollection<Document> collection;

    public UserRepository(MongoDatabase database) {
        this.collection = database.getCollection("users");
    }

    public void createUser(User user) {
        Document doc = new Document("name", user.getName())
                .append("email", user.getEmail())
                .append("password", user.getPassword());
        collection.insertOne(doc);
    }


    public void updateUser(String userId, String newName, String newEmail, String newPassword) {
        collection.updateOne(
                new Document("_id", new ObjectId(userId)),
                new Document("$set", new Document("name", newName).append("email",newEmail).append("password", newPassword))
        );
    }

    public boolean deleteUser(ObjectId userId) {
        try {
            DeleteResult result = collection.deleteOne(eq("_id", userId));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            User user = new User();
            user.setId(doc.getObjectId("_id"));
            user.setName(doc.getString("name"));
            user.setEmail(doc.getString("email"));
            user.setPassword(doc.getString("password"));
            users.add(user);
        }
        return users;
    }

    public User getUserById(ObjectId id) {
        Document doc = collection.find(new Document("_id", id)).first();
        if (doc != null) {
            User user = new User();
            user.setId(doc.getObjectId("_id"));
            user.setName(doc.getString("name"));
            user.setEmail(doc.getString("email"));
            user.setPassword(doc.getString("password"));
            return user;
        }
        return null;
    }

    public List<User> getUsersByName(String name) {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find(new Document("name", name))) {
            User user = new User();
            user.setId(doc.getObjectId("_id"));
            user.setName(doc.getString("name"));
            user.setEmail(doc.getString("email"));
            user.setPassword(doc.getString("password"));
            users.add(user);
        }
        return users;
    }


    public User getUserByEmail(String email) {
        Document doc = collection.find(new Document("email", email)).first();
        if (doc != null) {
            User user = new User();
            user.setId(doc.getObjectId("_id"));
            user.setName(doc.getString("name"));
            user.setEmail(doc.getString("email"));
            user.setPassword(doc.getString("password"));
            return user;
        }
        return null;
    }

    public List<User> getUsersByPassword(String password) {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find(new Document("password", password))) {
            User user = new User();
            user.setId(doc.getObjectId("_id"));
            user.setName(doc.getString("name"));
            user.setEmail(doc.getString("email"));
            user.setPassword(doc.getString("password"));
            users.add(user);
        }
        return users;
    }

    public List<User> getUsersByNameAndEmail(String name, String email) {
        return findUsersByFilter(and(eq("name", name), eq("email", email)));
    }

    public List<User> getUsersByNameAndPassword(String name, String password) {
        return findUsersByFilter(and(eq("name", name), eq("password", password)));
    }

    public List<User> getUsersByEmailAndPassword(String email, String password) {
        return findUsersByFilter(and(eq("email", email), eq("password", password)));
    }

    public List<User> getUsersByIdAndName(ObjectId id, String name) {
        return findUsersByFilter(and(eq("_id", id), eq("name", name)));
    }

    public List<User> getUsersByIdAndEmail(ObjectId id, String email) {
        return findUsersByFilter(and(eq("_id", id), eq("email", email)));
    }

    public List<User> getUsersByIdAndPassword(ObjectId id, String password) {
        return findUsersByFilter(and(eq("_id", id), eq("password", password)));
    }

    public List<User> getUsersByIdNameAndEmail(ObjectId id, String name, String email) {
        return findUsersByFilter(and(eq("_id", id), eq("name", name), eq("email", email)));
    }

    public List<User> getUsersByNameEmailAndPassword(String name, String email, String password) {
        return findUsersByFilter(and(eq("name", name), eq("email", email), eq("password", password)));
    }

    public List<User> getUsersByIdNameEmailAndPassword(ObjectId id, String name, String email, String password) {
        return findUsersByFilter(and(eq("_id", id), eq("name", name), eq("email", email), eq("password", password)));
    }

    // Helper Method to Convert Document to User
    private User documentToUser(Document doc) {
        User user = new User();
        user.setId(doc.getObjectId("_id"));
        user.setName(doc.getString("name"));
        user.setEmail(doc.getString("email"));
        user.setPassword(doc.getString("password"));
        return user;
    }

    // Helper Method to Find Users by Filter
    private List<User> findUsersByFilter(Bson filter) {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find(filter)) {
            users.add(documentToUser(doc));
        }
        return users;
    }

    public List<User> sortUsers(List<User> users, String sortField, boolean ascending) {
        users.sort((u1, u2) -> {
            String val1 = getFieldValue(u1, sortField);
            String val2 = getFieldValue(u2, sortField);
            return ascending ? val1.compareTo(val2) : val2.compareTo(val1);
        });
        return users;
    }

    private String getFieldValue(User user, String field) {
        switch (field) {
            case "_id": return user.getId().toString();
            case "name": return user.getName();
            case "email": return user.getEmail();
            case "password": return user.getPassword();
            default: return "";
        }
    }

    public long countUsers() {
        return collection.countDocuments();
    }



}
