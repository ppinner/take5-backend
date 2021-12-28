package com.example.take5.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class SpringMongoConfiguration {

    public @Bean MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://dataGuy:dataGuy1@cluster0.uyjm9.mongodb.net:27017");
    }

    public @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "Take5");
    }
}