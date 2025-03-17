package com.example.mongocrudapp.utils;

import com.mongodb.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class  MongoDBConnection{
    private static final String URI = "mongodb+srv://andreearodica36:1234@clustertema2mongo.hj6jq.mongodb.net/sample_mflix?retryWrites=true&w=majority";
    private static MongoClient mongoClient;

    public static MongoDatabase connect(String dbName) {
        if (mongoClient == null) {
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(URI))
                    .serverApi(serverApi)
                    .build();

            try {
                mongoClient = MongoClients.create(settings);
            } catch (Exception e) {
                throw new RuntimeException("Failed to connect to MongoDB", e);
            }
        }

        return mongoClient.getDatabase(dbName);
}}
