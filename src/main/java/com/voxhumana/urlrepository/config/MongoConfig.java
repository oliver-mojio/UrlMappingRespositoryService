package com.voxhumana.urlrepository.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.voxhumana.urlrepository.converter.OffsetDateTimeReadConverter;
import com.voxhumana.urlrepository.converter.OffsetDateTimeWriteConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoRepositories("com.voxhumana.urlrepository.repository")
@Import(value = MongoAutoConfiguration.class)
public class MongoConfig extends AbstractMongoClientConfiguration {
    private final List<Converter<?, ?>> converters = new ArrayList<>();

    @Autowired
    private MongoProperties mongoProperties;

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(MongoClientSettings.builder().build());
    }

    @Override
    protected String getDatabaseName() {
        return mongoProperties.getDatabase();
    }

    @Override
    public CustomConversions customConversions() {
        converters.add(new OffsetDateTimeReadConverter());
        converters.add(new OffsetDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }
}
