package com.employee.api.infra.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.employee.api.domain.Employee;
import com.employee.api.infra.GenericRepositoryImpl;
import com.employee.api.infra.query.QueryParam;
import com.employee.api.util.Page;
import com.employee.api.util.PageImpl;
import com.employee.api.util.Pageable;
import com.employee.api.util.SortType;
import com.employee.api.util.SortableParam;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class EmployeeRepositoryImpl extends GenericRepositoryImpl<Employee, Long> implements EmployeeRepository {

	private Logger logger = LoggerFactory.getLogger(EmployeeRepositoryImpl.class);

	private MongoCollection<Document> collection;

	public EmployeeRepositoryImpl(MongoTemplate mongoTemplate, MongoDatabase database) {
		super(mongoTemplate, database);
		collection = database.getCollection(mongoTemplate.getCollectionName(Employee.class));
	}

	public Page<Employee> getByFilter(Pageable pageable, QueryParam... qps) {
		try {
			Document query = generateQuery(qps);

			List<Document> iterable = null;
			int skip = calculateSkip(pageable);

			Map<SortableParam, SortType> sort = pageable.getSort();

			SortType sortValue = sort.entrySet().iterator().next().getValue();
			Bson sortOrder = populateSortingOrder(sort.keySet(), sortValue);

			iterable = collection.find(query).skip(skip).limit(pageable.getPageSize()).sort(sortOrder)
					.into(new ArrayList<>());

			if (iterable.isEmpty()) {
				return Page.empty(pageable);
			}

			List<Employee> employees = new ArrayList<>();

			iterable.stream().forEach(document -> {
				String clazzName = document.getString("_class");
				try {
					Class<?> c = Class.forName(clazzName);

					JsonWriterSettings settings = JsonWriterSettings.builder()
							.int64Converter((value, writer) -> writer.writeNumber(value.toString())).build();

					String json = document.toJson(settings).replaceAll("_id", "id");
					document = Document.parse(json);

					Employee entity = (Employee) gson.fromJson(document.toJson(getEncoder()), c);
					employees.add(entity);

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			});

			Long totalCount = 0l;

			totalCount = collection.countDocuments(query);

			PageImpl<Employee> employeePageImpl = null;
			Page<Employee> employeesPage = null;

			employeePageImpl = new PageImpl<Employee>(employees, pageable, totalCount);
			employeesPage = new Page.PageBuilder<Employee>().withContent(employees)
					.withPageable(employeePageImpl.getPageable()).build();

			return employeesPage;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

}