package com.employee.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.employee.api.infra.GenericRepository;
import com.employee.api.infra.GenericRepositoryImpl;
import com.employee.api.infra.department.DepartmentRepository;
import com.employee.api.infra.department.DepartmentRepositoryImpl;
import com.employee.api.infra.employee.EmployeeRepository;
import com.employee.api.infra.employee.EmployeeRepositoryImpl;

@Configuration
@EnableMongoRepositories(basePackages = "com.employee.api")
public class RepoConfig {

	@Autowired
	private MongoDbConfig mongoDbConfig;

	@Bean
	public GenericRepository<?, ?> genericRepository() throws Exception {
		return new GenericRepositoryImpl<>(mongoDbConfig.mongoTemplate(), mongoDbConfig.db());
	}

	@Bean
	public EmployeeRepository employeeRepository() throws Exception {
		return new EmployeeRepositoryImpl(mongoDbConfig.mongoTemplate(), mongoDbConfig.db());
	}
	
	@Bean
	public DepartmentRepository departmentRepository() throws Exception {
		return new DepartmentRepositoryImpl(mongoDbConfig.mongoTemplate(), mongoDbConfig.db());
	}

}
