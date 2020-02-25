package com.employee.api.config;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.codecs.BsonTypeClassMap;
import org.bson.codecs.Codec;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.Encoder;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.employee.api.config.codec.CustomDateCodec;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

@Configuration
@EnableMongoRepositories(basePackages = "com.employee.api")
public class MongoDbConfig {

	@Autowired
	private MongoProps mongoProps;

	@SuppressWarnings("deprecation")
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate = new MongoTemplate(mongo(), mongoProps.getDatabaseName());
		MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
		mongoMapping.afterPropertiesSet();
		return mongoTemplate;
	}

	@Bean
	public Encoder<Document> getEncoder() {
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(customCodecs()),
				MongoClientSettings.getDefaultCodecRegistry());

		return (Encoder<Document>) new DocumentCodec(codecRegistry, new BsonTypeClassMap());
	}

	@Bean
	public MongoClient mongo() {
		return new MongoClient(new ServerAddress(mongoProps.getHost(), mongoProps.getPort()), mongoCredentials(),
				mongoClientOptions());
	}

	@Bean
	public MongoDatabase db() throws Exception {
		CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
		CodecRegistry pojoRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());

		CodecRegistry customCodecsRegistry = CodecRegistries.fromCodecs(customCodecs());

		CodecRegistry codecRegistries = CodecRegistries.fromRegistries(defaultCodecRegistry, pojoRegistry,
				customCodecsRegistry);

		return mongo().getDatabase(mongoProps.getDatabaseName()).withCodecRegistry(codecRegistries);
	}

	private MongoCredential mongoCredentials() {
		return MongoCredential.createCredential(mongoProps.getHost(), mongoProps.getDatabaseName(),
				mongoProps.getPassword().toCharArray());
	}

	private MongoClientOptions mongoClientOptions() {
		MongoClientOptions.Builder mongoClientOptions = MongoClientOptions.builder().sslInvalidHostNameAllowed(true)
				.sslEnabled(true);

		try {
			codecRegistry(mongoClientOptions);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mongoClientOptions.build();
	}

	private void codecRegistry(MongoClientOptions.Builder mongoClientOptions) {
		CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
		CodecRegistry pojoRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry customCodecsRegistry = CodecRegistries.fromCodecs(customCodecs());
		CodecRegistry codecRegistries = CodecRegistries.fromRegistries(defaultCodecRegistry, pojoRegistry,
				customCodecsRegistry);
		mongoClientOptions.codecRegistry(codecRegistries);
	}

	private ArrayList<Codec<?>> customCodecs() {
		ArrayList<Codec<?>> codecs = new ArrayList<>();
		codecs.add(new CustomDateCodec("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		return codecs;
	}
}
