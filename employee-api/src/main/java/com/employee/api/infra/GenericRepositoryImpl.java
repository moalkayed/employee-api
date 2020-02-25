package com.employee.api.infra;

import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.codecs.BsonTypeClassMap;
import org.bson.codecs.Codec;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.Encoder;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.employee.api.config.codec.CustomDateCodec;
import com.employee.api.infra.query.QueryParam;
import com.employee.api.util.Assert;
import com.employee.api.util.Page;
import com.employee.api.util.PageImpl;
import com.employee.api.util.Pageable;
import com.employee.api.util.SortType;
import com.employee.api.util.SortableParam;
import com.google.gson.Gson;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class GenericRepositoryImpl<K, V> implements GenericRepository<K, V> {

	protected Logger log = LoggerFactory.getLogger(GenericRepositoryImpl.class);

	@Autowired
	protected final MongoTemplate mongoTemplate;

	@Autowired
	protected final MongoDatabase database;

	@Autowired
	private MongoOperations mongoDbOps;

	@Autowired
	protected ApplicationContext applicationContext;

	protected Gson gson = new Gson();

	public GenericRepositoryImpl(MongoTemplate mongoTemplate, MongoDatabase database) {
		super();
		this.mongoTemplate = mongoTemplate;
		this.database = database;
	}

	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@Override
	public Optional<K> getById(V id, Class<K> clazz) {
		try {

			MongoCollection<Document> collection = database.getCollection(mongoTemplate.getCollectionName(clazz));

			Document query = new Document();
			query.append("_id", new Document().append("$eq", id));

			FindIterable<Document> iterable = collection.find(query);

			if (iterable.first() == null) {
				return Optional.empty();
			}

			Document document = iterable.first();

			JsonWriterSettings settings = JsonWriterSettings.builder()
					.int64Converter((value, writer) -> writer.writeNumber(value.toString())).build();

			String json = document.toJson(settings).replaceAll("_id", "id");
			document = document.parse(json);

			String clazzName = document.getString("_class");
			Class c = Class.forName(clazzName);

			K entity = (K) gson.fromJson(document.toJson(getEncoder()), c);

			return Optional.ofNullable(entity);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public Page<K> getByIds(Pageable pageable, Set<V> ids, Class<K> clazz) {
		try {
			Document query = new Document();
			query.append("_id", new Document().append("$in", ids));

			Page<K> entities = getEntitiesByQuery(pageable, clazz, query);

			return entities;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public Page<K> getByQuery(Pageable pageable, Document query, Class<K> clazz) {
		try {
			Page<K> entitiesByQuery = getEntitiesByQuery(pageable, clazz, query);

			return entitiesByQuery;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private Page<K> getEntitiesByQuery(Pageable pageable, Class<K> clazz, Document query) {

		int skip = calculateSkip(pageable);

		Map<SortableParam, SortType> sort = pageable.getSort();

		SortType sortValue = sort.entrySet().iterator().next().getValue();

		Bson sortOrder = populateSortingOrder(sort.keySet(), sortValue);
		MongoCollection<Document> collection = database.getCollection(mongoTemplate.getCollectionName(clazz));

		ArrayList<Document> iterable = collection.find(query).skip(skip).limit(pageable.getPageSize()).sort(sortOrder)
				.into(new ArrayList<>());

		if (iterable.isEmpty()) {
			return Page.empty(pageable);
		}

		List<K> entities = new ArrayList<>();

		iterable.stream().forEach(document -> {
			JsonWriterSettings settings = JsonWriterSettings.builder()
					.int64Converter((value, writer) -> writer.writeNumber(value.toString())).build();

			String json = document.toJson(settings).replaceAll("_id", "id");
			document = Document.parse(json);

			String clazzName = document.getString("_class");
			Class<?> c;
			try {
				c = Class.forName(clazzName);
			} catch (ClassNotFoundException e) {
				c = clazz;
				log.error(e.getMessage(), e);
			}

			K entity = (K) gson.fromJson(document.toJson(getEncoder()), c);
			entities.add(entity);
		});

		PageImpl<K> entitiesPageImpl = new PageImpl<K>(entities, pageable, iterable.size());

		Page<K> entitiesPage = new Page.PageBuilder<K>().withContent(entities)
				.withPageable(entitiesPageImpl.getPageable()).build();

		return entitiesPage;
	}

	protected Bson populateSortingOrder(Set<SortableParam> sortableParams, SortType sortType) {
		Bson sortingOrder = null;

		List<SortableParam> sorts = new ArrayList<>(sortableParams);
		sorts.add(SortableParam.HIRE_DATE);

		switch (sortType) {
		case ASC:
			sortingOrder = orderBy(ascending(sorts.stream().map(s -> s.getParam()).collect(Collectors.toList())));
			break;
		case DESC:
			sortingOrder = orderBy(descending(sorts.stream().map(s -> s.getParam()).collect(Collectors.toList())));
			break;
		default:
			break;
		}

		return sortingOrder;
	}

	protected Document generateQuery(QueryParam... qps) {
		Document query = new Document();

		Arrays.asList(qps).stream().forEach(p -> {
			String operator = p.getOperator();
			String fieldName = p.getFieldName();
			Object value = p.getValue();

			switch (operator) {
			case "EQ":
				query.append(fieldName, new Document().append("$eq", value));
				break;
			case "NE":
				query.append(fieldName, new Document().append("$ne", value));
				break;
			case "GT":
				query.put(fieldName, BasicDBObjectBuilder.start("$gt", value).get());
				break;
			case "GTE":
				query.put(fieldName, BasicDBObjectBuilder.start("$gte", value).get());
				break;
			case "LT":
				query.put(fieldName, BasicDBObjectBuilder.start("$lt", value).get());
				break;
			case "LTE":
				query.put(fieldName, BasicDBObjectBuilder.start("$lte", value).get());
				break;
			case "IN":
				query.append(fieldName, new Document().append("$in", value));
				break;
			case "NIN":
				query.append(fieldName, new Document().append("$nin", value));
				break;
			case "BETWEEN":
				Object[] values = (Object[]) value;
				query.append(fieldName, new Document().append("$gte", values[0]).append("$lte", values[1]));
				break;
			default:
				break;
			}
		});
		return query;
	}

	@Override
	public Optional<K> save(K entity, Class<K> clazz) {
		try {
			K object = mongoDbOps.save(entity, mongoTemplate.getCollectionName(clazz));
			return Optional.ofNullable(object);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public Boolean delete(V v, Class<K> clazz) {
		return null;
	}

	protected String generateKey(String clazzName, String methodName, String string, String value) {
		StringBuilder key = new StringBuilder("");

		if (Assert.isEmpty(value)) {
			return key.toString();
		}

		if (Assert.notEmpty(clazzName)) {
			key.append(clazzName);
		}

		if (Assert.notEmpty(methodName)) {
			key.append(":").append(methodName);
		}

		if (Assert.notNull(string)) {
			key.append(":").append(string);
		}

		key.append(":").append(value);

		return key.toString();
	}

	protected int calculateSkip(Pageable pageable) {
		int skip = pageable.getPageSize() * (pageable.getPageNumber());
		return skip;
	}

	protected Encoder<Document> getEncoder() {
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(customCodecs()),
				MongoClientSettings.getDefaultCodecRegistry());

		return (Encoder<Document>) new DocumentCodec(codecRegistry, new BsonTypeClassMap());
	}

	private ArrayList<Codec<?>> customCodecs() {
		ArrayList<Codec<?>> codecs = new ArrayList<>();
		codecs.add(new CustomDateCodec("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		return codecs;
	}

}
