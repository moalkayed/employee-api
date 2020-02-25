package com.employee.api.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employee.api.controller.Request;
import com.employee.api.controller.department.DepartmentDTO;
import com.employee.api.controller.department.DepartmentListDTO;
import com.employee.api.controller.employee.EmployeeDTO;
import com.employee.api.controller.employee.EmployeeListDTO;
import com.employee.api.domain.Department;
import com.employee.api.domain.Employee;
import com.employee.api.error.DepratmentError;
import com.employee.api.error.EmployeeError;
import com.employee.api.error.exception.DepartmentException;
import com.employee.api.error.exception.EmployeeException;
import com.employee.api.error.exception.GenericException;
import com.employee.api.service.department.DepartmentList;
import com.employee.api.service.department.DepartmentService;
import com.employee.api.service.employee.EmployeeList;
import com.employee.api.service.employee.EmployeeService;
import com.employee.api.util.Assert;
import com.employee.api.util.Pageable;
import com.employee.api.util.SortType;
import com.employee.api.util.SortableParam;

public abstract class AbstractApplication {

	private Logger log = LoggerFactory.getLogger(AbstractApplication.class);

	protected EmployeeListDTO populate(DepartmentService departmentService, EmployeeService employeeService,
			EmployeeList employeeList) throws GenericException {

		EmployeeListDTO employeeListDTO = new EmployeeListDTO();

		List<Employee> employees = employeeList.getEmployees();
		if (Assert.isNull(employees)) {
			return employeeListDTO;
		}

		List<EmployeeDTO> employeeDTOs = populateEmployeeDTOs(employeeService, departmentService, employees);
		employeeListDTO.setEmployees(employeeDTOs);

		employeeListDTO.setSuccess(Boolean.TRUE);
		employeeListDTO.setCurrentDate(LocalDateTime.now());

		Pageable pageable = employeeList.getPageable();
		if (Assert.isNull(pageable)) {
			return employeeListDTO;
		}
		employeeListDTO.setPageNumber(pageable.getPageNumber());
		employeeListDTO.setHasMore(pageable.getHasNext());
		employeeListDTO.setPageSize(pageable.getPageSize());
		employeeListDTO.setTotalNumber(pageable.getTotalNumber());

		return employeeListDTO;
	}

	protected List<EmployeeDTO> populateEmployeeDTOs(EmployeeService employeeService,
			DepartmentService departmentService, List<Employee> employees) throws GenericException {
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();

		for (Employee employee : employees) {
			EmployeeDTO employeeDTO = new EmployeeDTO();
			employeeDTO.setFullName(employee.getFirstName() + " " + employee.getLastName());
			employeeDTO.setActive(employee.getActive());
			employeeDTO.setEmail(employee.getEmail());
			employeeDTO.setHireDate(employee.getHireDate());
			employeeDTO.setId(employee.getId());

			String managerFullName = populateManagerFullName(employeeService, employee.getManagerId());
			employeeDTO.setManagerFullName(managerFullName);

			String departmentName = populateDepartmentName(departmentService, employee.getDepratmentId());
			employeeDTO.setDepratmentName(departmentName);

			employeeDTO.setPhoneNumber(employee.getPhoneNumber());
			employeeDTO.setSalary(employee.getSalary());

			employeeDTOs.add(employeeDTO);
		}
		return employeeDTOs;
	}

	private String populateManagerFullName(EmployeeService service, Long managerId) throws GenericException {
		if (Assert.isEmpty(managerId)) {
			return null;
		}

		Optional<EmployeeList> employeeListOptional = service.getById(managerId);
		if (!employeeListOptional.isPresent()) {
			return null;
		}

		EmployeeList employeeList = employeeListOptional.get();
		List<Employee> employees = employeeList.getEmployees();
		if (Assert.isEmpty(employees)) {
			return null;
		}

		Employee employee = employees.get(0);
		if (Assert.isNull(employee)) {
			return null;
		}

		return employee.getFirstName() + " " + employee.getLastName();
	}

	private String populateDepartmentName(DepartmentService service, Long departmentId) throws GenericException {
		if (Assert.isEmpty(departmentId)) {
			return null;
		}

		Optional<DepartmentList> employeeListOptional = service.getById(departmentId);
		if (!employeeListOptional.isPresent()) {
			return null;
		}

		DepartmentList departmentList = employeeListOptional.get();
		List<Department> departments = departmentList.getDepartments();
		if (Assert.isEmpty(departments)) {
			return null;
		}

		Department department = departments.get(0);
		if (Assert.isNull(department)) {
			return null;
		}

		return department.getName();
	}

	protected List<DepartmentDTO> populateDepartmentDTO(DepartmentService departmentService,
			EmployeeService employeeService, List<Department> departments) throws GenericException {
		List<DepartmentDTO> departmentDTOs = new ArrayList<DepartmentDTO>();

		for (Department department : departments) {
			DepartmentDTO departmentDTO = new DepartmentDTO();

			departmentDTO.setId(department.getId());
			EmployeeDTO employeeDTO = populateEmployee(departmentService, employeeService, department.getManagerId());
			departmentDTO.setEmployeeDTO(employeeDTO);
			departmentDTO.setName(department.getName());

			departmentDTOs.add(departmentDTO);
		}
		return departmentDTOs;
	}

	protected EmployeeDTO populateEmployee(DepartmentService departmentService, EmployeeService employeeService,
			Long id) throws GenericException {

		Optional<EmployeeList> employeeList = employeeService.getById(id);
		if (!employeeList.isPresent()) {
			log.debug(String.format("Employeed %s received by service is not present", employeeList));
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, "Employee is not present");
		}

		EmployeeListDTO employeeListDTO = populate(departmentService, employeeService, employeeList.get());
		List<EmployeeDTO> employees = employeeListDTO.getEmployees();
		if (Assert.isEmpty(employees)) {
			log.debug(String.format("Employeed %s received by service is not present", employees));
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, "Employee is not present");
		}

		EmployeeDTO employeeDTO = employees.get(0);
		return employeeDTO;
	}

	protected DepartmentListDTO populate(DepartmentService departmentService, EmployeeService employeeService,
			DepartmentList departmentList) throws DepartmentException {
		DepartmentListDTO departmentListDTO = new DepartmentListDTO();

		List<Department> departments = departmentList.getDepartments();
		if (Assert.isNull(departments)) {
			return departmentListDTO;
		}

		List<DepartmentDTO> departmentDTO;
		try {
			departmentDTO = populateDepartmentDTO(departmentService, employeeService, departments);
		} catch (GenericException e) {
			log.error(e.getMessage(), e);
			throw new DepartmentException(DepratmentError.DEPARTMENT_NOT_FOUND, e);
		}
		departmentListDTO.setDepartments(departmentDTO);

		departmentListDTO.setSuccess(Boolean.TRUE);
		departmentListDTO.setCurrentDate(LocalDateTime.now());

		Pageable pageable = departmentList.getPageable();
		if (Assert.isNull(pageable)) {
			return departmentListDTO;
		}
		departmentListDTO.setPageNumber(pageable.getPageNumber());
		departmentListDTO.setHasMore(pageable.getHasNext());
		departmentListDTO.setPageSize(pageable.getPageSize());
		departmentListDTO.setTotalNumber(pageable.getTotalNumber());

		return departmentListDTO;
	}

	protected Pageable populateRequestPageable(Request request) {
		Pageable.PageableBuilder pageableBuilder = new Pageable.PageableBuilder();
		pageableBuilder.withPageSize(request.getPageSize());
		pageableBuilder.withPageNumber(request.getPageNumber());

		Map<SortableParam, SortType> sort = request.getSort();
		if (Assert.isEmpty(sort)) {
			sort = new HashMap<>();
			sort.put(SortableParam.HIRE_DATE, SortType.DESC);
		}
		pageableBuilder.withSort(sort);

		return pageableBuilder.build();
	}
}
