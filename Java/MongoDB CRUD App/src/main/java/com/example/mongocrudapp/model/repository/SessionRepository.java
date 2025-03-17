package com.example.mongocrudapp.model.repository;

import com.example.mongocrudapp.model.Session;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class SessionRepository {
    private final MongoCollection<Document> collection;

    public SessionRepository(MongoDatabase database) {
        this.collection = database.getCollection("sessions");
    }
    public void createSession(Session session) {
        if (session.getUserId() == null || session.getJwt() == null) {
            throw new IllegalArgumentException("User ID and JWT cannot be null!");
        }

        try {
            Document existingDoc = collection.find(eq("user_id", session.getUserId())).first();

            if (existingDoc == null) {
                Document doc = new Document("user_id", session.getUserId())
                        .append("jwt", session.getJwt());
                collection.insertOne(doc);
                System.out.println("Document inserted successfully!");
            } else {
                throw new IllegalStateException("User ID already exists, skipping insertion.");
            }
        } catch (Exception e) {
            System.out.println("Error inserting session: " + e.getMessage());
            throw e;
        }
    }



    public void updateSession(String sessionId, String newUserId, String newJwt) {
        collection.updateOne(
                new Document("_id", new ObjectId(sessionId)),
                new Document("$set", new Document("user_id", newUserId).append("jwt", newJwt))
        );
    }


    public boolean deleteSession(ObjectId sessionId) {
        try {
            DeleteResult result = collection.deleteOne(eq("_id", sessionId));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Session> getAllSessions() {
        List<Session> sessions = new ArrayList<>();
        for (Document doc : collection.find()) {
            Session session = new Session();
            session.setId(doc.getObjectId("_id"));
            session.setUserId(doc.getString("user_id"));
            session.setJwt(doc.getString("jwt"));
            sessions.add(session);
        }
        return sessions;
    }
    public Session getSessionById(ObjectId selectedSessionId) {
        Document doc = collection.find(new Document("_id", selectedSessionId)).first();
        if (doc != null) {
            Session session = new Session();
            session.setId(doc.getObjectId("_id"));
            session.setUserId(doc.getString("user_id"));
            session.setJwt(doc.getString("jwt"));
            return session;
        }
        return null;
    }

    public List<Session> getSessionsByJwt(String jwt) {
        List<Session> sessions = new ArrayList<>();
        for (Document doc : collection.find(new Document("jwt", jwt))) {
            Session session = new Session();
            session.setId(doc.getObjectId("_id"));
            session.setUserId(doc.getString("user_id"));
            session.setJwt(doc.getString("jwt"));
            sessions.add(session);
        }
        return sessions;
    }


    public Session getUserByUserId(String userId) {
        Document doc = collection.find(new Document("user_id", userId)).first();
        if (doc != null) {
            Session session = new Session();
            session.setId(doc.getObjectId("_id"));
            session.setUserId(doc.getString("user_id"));
            session.setJwt(doc.getString("jwt"));
            return session;
        }
        return null;
    }


    public List<Session> getUsersByIdAndUserId(ObjectId id, String userId) {
        return findSessionsByFilter(and(eq("_id", id), eq("userId", userId)));
    }

    public List<Session> getUsersByIdAndJwt(ObjectId id, String jwt) {
        return findSessionsByFilter(and(eq("_id", id), eq("jwt", jwt)));
    }

    public List<Session> getUsersByUserIdAndJwt(String userId, String jwt) {
        return findSessionsByFilter(and(eq("user_id", userId), eq("jwt", jwt)));
    }

    public List<Session> getUsersByIdUserIdAndJwt(ObjectId id, String userId, String jwt) {
        return findSessionsByFilter(and(eq("_id", id), eq("user_id", userId), eq("jwt", jwt)));
    }


    private Session documentToSession(Document doc) {
        Session session = new Session();
        session.setId(doc.getObjectId("_id"));
        session.setUserId(doc.getString("user_id"));
        session.setJwt(doc.getString("jwt"));
        return session;
    }

    private List<Session> findSessionsByFilter(Bson filter) {
        List<Session> sessions = new ArrayList<>();
        for (Document doc : collection.find(filter)) {
            sessions.add(documentToSession(doc));
        }
        return sessions;
    }

    public List<Session> sortSessions(List<Session> sessions, String sortField, boolean ascending) {
        sessions.sort((u1, u2) -> {
            String val1 = getFieldValue(u1, sortField);
            String val2 = getFieldValue(u2, sortField);
            return ascending ? val1.compareTo(val2) : val2.compareTo(val1);
        });
        return sessions;
    }

    private String getFieldValue(Session session, String field) {
        return switch (field) {
            case "_id" -> session.getId().toString();
            case "user_id" -> session.getUserId();
            case "jwt" -> session.getJwt();
            default -> "";
        };
    }

    public long countSessions() {
        return collection.countDocuments();
    }


}
