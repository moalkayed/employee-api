package com.employee.api.infra.department;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.employee.api.domain.Department;

@NoRepositoryBean
public interface InfrastructureDepartmentRepository extends MongoRepository<Department, String>, DepartmentRepository {

}
