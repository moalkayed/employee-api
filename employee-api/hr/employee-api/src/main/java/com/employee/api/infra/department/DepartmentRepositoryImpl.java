package com.employee.api.infra.department;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.employee.api.domain.Department;
import com.employee.api.infra.GenericRepositoryImpl;
import com.mongodb.client.MongoDatabase;

public class DepartmentRepositoryImpl extends GenericRepositoryImpl<Department, Long> implements DepartmentRepository {

	public DepartmentRepositoryImpl(MongoTemplate mongoTemplate, MongoDatabase database) {
		super(mongoTemplate, database);
	}

}
