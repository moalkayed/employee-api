package com.employee.api.service.department;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.api.domain.Department;
import com.employee.api.error.exception.GenericException;
import com.employee.api.infra.department.DepartmentRepository;
import com.employee.api.service.department.DepartmentList.DepartmentListBuilder;
import com.employee.api.util.Assert;
import com.employee.api.util.Page;
import com.employee.api.util.Pageable;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository repository;

	public Optional<DepartmentList> getById(Long id) throws GenericException {

		Optional<Department> department = repository.getById(id, Department.class);
		if (!department.isPresent()) {
			return Optional.empty();
		}

		DepartmentList departmentList = populateDepartmentList(department);
		return Optional.ofNullable(departmentList);
	}

	public Optional<DepartmentList> getByIds(Pageable pageable, Set<Long> ids) throws GenericException {

		Page<Department> departments = repository.getByIds(pageable, ids, Department.class);
		if (Assert.isNull(departments) || departments.isEmpty()) {
			return Optional.empty();
		}

		DepartmentList departmentList = populateDepartmentList(departments);
		return Optional.ofNullable(departmentList);
	}

	public Optional<DepartmentList> save(Department department) throws GenericException {
		Optional<Department> departments = repository.save(department, Department.class);

		if (!departments.isPresent()) {
			return Optional.empty();
		}

		DepartmentList departmentList = populateDepartmentList(departments);
		return Optional.ofNullable(departmentList);
	}

	public Boolean delete(Long id) throws GenericException {
		return repository.delete(id, Department.class);
	}

	private DepartmentList populateDepartmentList(Page<Department> departments) {
		if (Assert.isEmpty(departments.getContent())) {
			return null;
		}

		DepartmentListBuilder depratmentListBuilder = new DepartmentListBuilder();

		depratmentListBuilder.withDepartments(departments.getContent());
		depratmentListBuilder.withPageable(departments.getPageable());

		DepartmentList departmentList = depratmentListBuilder.build();
		return departmentList;
	}

	private DepartmentList populateDepartmentList(Optional<Department> department) {
		DepartmentListBuilder depratmentListBuilder = new DepartmentListBuilder();

		List<Department> departments = Arrays.asList(department.get());
		depratmentListBuilder.withDepartments(departments);
		depratmentListBuilder.withPageable(Pageable.DEFAULT_PAGEABLE);

		DepartmentList departmentList = depratmentListBuilder.build();
		return departmentList;
	}
}