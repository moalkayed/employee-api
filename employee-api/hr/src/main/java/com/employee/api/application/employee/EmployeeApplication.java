package com.employee.api.application.employee;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.api.application.AbstractApplication;
import com.employee.api.controller.DeleteResponse;
import com.employee.api.controller.IdsRequest;
import com.employee.api.controller.employee.EmployeeListDTO;
import com.employee.api.controller.employee.EmployeeRequest;
import com.employee.api.controller.employee.FilterRequest;
import com.employee.api.domain.Employee;
import com.employee.api.error.DefaultError;
import com.employee.api.error.EmployeeError;
import com.employee.api.error.exception.EmployeeException;
import com.employee.api.error.exception.GenericException;
import com.employee.api.service.department.DepartmentService;
import com.employee.api.service.employee.EmployeeList;
import com.employee.api.service.employee.EmployeeQueryValues;
import com.employee.api.service.employee.EmployeeService;
import com.employee.api.util.Assert;
import com.employee.api.util.Pageable;

@Service
public class EmployeeApplication extends AbstractApplication {

	private Logger log = LoggerFactory.getLogger(EmployeeApplication.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	public EmployeeListDTO getById(Long id) throws GenericException {

		if (Assert.isNull(id)) {
			log.debug(String.format("Employee Id %s may not be null nor empty", id));
			throw new EmployeeException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("Employee Id %s may not be null nor empty", id));
		}

		Optional<EmployeeList> employees;
		try {
			employees = employeeService.getById(id);
		} catch (GenericException e) {
			log.error(e.getMessage(), e);
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, e);
		}

		if (!employees.isPresent()) {
			log.debug(String.format("Employeed %s received by service is not present", employees));
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, "Employee is not present");
		}

		EmployeeListDTO employeeListDTO = populate(departmentService, employeeService, employees.get());
		return employeeListDTO;
	}

	public EmployeeListDTO getByIds(IdsRequest idsRequest) throws GenericException {

		if (Assert.isNull(idsRequest)) {
			log.debug(String.format("Request %s sent by client is null", idsRequest));
			throw new EmployeeException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("Request %s sent by client is null", idsRequest));
		}

		Pageable pageable = populateRequestPageable(idsRequest);

		Optional<EmployeeList> employees;
		try {
			employees = employeeService.getByIds(pageable, idsRequest.getIds());
		} catch (GenericException e) {
			log.error(e.getMessage(), e);
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, e);
		}

		if (!employees.isPresent()) {
			log.debug(String.format("Employeed %s received by service is not present", employees));
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, "Employee is not present");
		}

		EmployeeListDTO employeeListDTO = populate(departmentService,employeeService, employees.get());
		return employeeListDTO;
	}

	public EmployeeListDTO getByFilter(FilterRequest request) throws GenericException {

		if (Assert.isNull(request)) {
			log.debug(String.format("Request %s sent by client is null", request));
			throw new EmployeeException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("Request %s sent by client is null", request));
		}

		Pageable pageable = populateRequestPageable(request);
		EmployeeQueryValues employeeValues = populateQueryValues(request);

		Optional<EmployeeList> employees;
		try {
			employees = employeeService.getByFilter(pageable, employeeValues);
		} catch (GenericException e) {
			log.error(e.getMessage(), e);
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, e);
		}

		if (!employees.isPresent()) {
			log.debug(String.format("Employeed %s received by service is not present", employees));
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, "Employee is not present");
		}

		EmployeeListDTO employeeListDTO = populate(departmentService,employeeService, employees.get());
		return employeeListDTO;
	}

	public EmployeeListDTO raiseSalary(Long id, Double ratio) throws GenericException {

		if (Assert.isNull(id) || Assert.isNull(ratio)) {
			log.debug(String.format("Request %s , %s sent by client is null", id, ratio));
			throw new EmployeeException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("Request  %s , %s s sent by client is null", id, ratio));
		}

		Optional<EmployeeList> employeesListOptional;
		try {
			employeesListOptional = employeeService.getById(id);
		} catch (GenericException e) {
			log.error(e.getMessage(), e);
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, e);
		}

		Double newSalary = populateNewSalary(employeesListOptional, ratio);
		EmployeeRequest employeeRequest = populate(employeesListOptional, newSalary);

		EmployeeListDTO employeeListDTO = this.save(employeeRequest);
		return employeeListDTO;
	}

	public EmployeeListDTO save(EmployeeRequest request) throws GenericException {

		if (Assert.isNull(request)) {
			log.debug(String.format("Request %s sent by client is null", request));
			throw new EmployeeException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("Request %s sent by client is null", request));
		}

		Employee employee = populate(request);
		Optional<EmployeeList> employees;
		try {
			employees = employeeService.save(employee);
		} catch (GenericException e) {
			log.error(e.getMessage(), e);
			throw new EmployeeException(DefaultError.INTERNAL_SERVER_ERROR, e);
		}

		if (!employees.isPresent()) {
			log.debug(String.format("Employeed %s received by service is not present", employees));
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, "Employee is not present");
		}

		EmployeeListDTO employeeListDTO = populate(departmentService,employeeService, employees.get());
		return employeeListDTO;
	}

	public DeleteResponse delete(Long id) throws EmployeeException {
		DeleteResponse deleteResponse = new DeleteResponse();

		if (Assert.isEmpty(id)) {
			deleteResponse.setDeleted(false);
			log.warn(String.format("Unable to delete employee from DB, Empty employee id."));
			return deleteResponse;
		}

		try {
			boolean isDeleted = employeeService.delete(id);
			deleteResponse.setDeleted(isDeleted);

		} catch (GenericException e) {
			log.error(e.getMessage(), e);
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, e);
		}

		return deleteResponse;
	}

	private EmployeeQueryValues populateQueryValues(FilterRequest request) {
		EmployeeQueryValues.EmployeeQueryValuesBuilder queryValuesBuilder = new EmployeeQueryValues.EmployeeQueryValuesBuilder();

		queryValuesBuilder.withFirstName(request.getFirstName());
		queryValuesBuilder.withLastName(request.getLastName());
		queryValuesBuilder.withActive(request.getActive());
		queryValuesBuilder.withDepratmentId(request.getDepratmentId());
		queryValuesBuilder.withEmail(request.getEmail());
		queryValuesBuilder.withHireDateFrom(request.getHireDateFrom());
		queryValuesBuilder.withHireDateTo(request.getHireDateTo());
		queryValuesBuilder.withManagerId(request.getManagerId());
		queryValuesBuilder.withMaritalStatus(request.getMaritalStatus());
		queryValuesBuilder.withNationality(request.getNationality());
		queryValuesBuilder.withPhoneNumber(request.getPhoneNumber());
		queryValuesBuilder.withSalary(request.getSalary());
		queryValuesBuilder.withYearOfBirthday(request.getYearOfBirthday());

		return queryValuesBuilder.build();
	}

	private Employee populate(EmployeeRequest request) throws EmployeeException {

		if (Assert.isNull(request)) {
			log.debug(String.format("Request %s sent by client is null", request));
			throw new EmployeeException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("Request %s sent by client is null", request));
		}

		Employee.EmployeeBuilder employeeBuilder = new Employee.EmployeeBuilder();

		employeeBuilder.withActive(request.getActive());
		employeeBuilder.withDepratmentId(request.getDepratmentId());
		employeeBuilder.withEmail(request.getEmail());
		employeeBuilder.withFirstName(request.getFirstName());
		employeeBuilder.withHireDate(request.getHireDate());
		employeeBuilder.withLastName(request.getLastName());
		employeeBuilder.withManagerId(request.getManagerId());
		employeeBuilder.withPhoneNumber(request.getPhoneNumber());
		employeeBuilder.withSalary(request.getSalary());

		return employeeBuilder.build();
	}

	private Double populateNewSalary(Optional<EmployeeList> employeesListOptional, Double ratio)
			throws EmployeeException {

		if (!employeesListOptional.isPresent()) {
			log.debug(String.format("Employee %s received by service is not present", employeesListOptional));
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, "Employee is not present");
		}

		EmployeeList employeeList = employeesListOptional.get();
		if (Assert.isNull(employeeList)) {
			log.debug(String.format("Employeed %s received by service is not present", employeeList));
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, "Employee is not present");
		}

		List<Employee> employees = employeeList.getEmployees();
		if (Assert.isNull(employees) || employees.size() < 1) {
			log.debug(String.format("Employeed %s received by service is not present", employees));
			throw new EmployeeException(EmployeeError.EMPLOYEE_NOT_FOUND, "Employee is not present");
		}

		Employee employee = employees.get(0);

		Double oldSalary = employee.getSalary();
		Double newSalary = (oldSalary * ratio);

		return newSalary;

	}

	private EmployeeRequest populate(Optional<EmployeeList> employeesList, Double newSalary) throws EmployeeException {
		if (!employeesList.isPresent()) {
			log.debug(String.format("Request %s sent by client is null", employeesList));
			throw new EmployeeException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("Request %s sent by client is null", employeesList));
		}

		EmployeeList employeeList = employeesList.get();
		List<Employee> employees = employeeList.getEmployees();
		if (Assert.isEmpty(employees)) {
			log.debug(String.format("Request %s sent by client is null", employees));
			throw new EmployeeException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("Request %s sent by client is null", employees));
		}

		Employee employee = employees.get(0);

		EmployeeRequest employeeRequest = new EmployeeRequest();
		employeeRequest.setId(employee.getId());
		employeeRequest.setActive(employee.getActive());
		employeeRequest.setDepratmentId(employee.getDepratmentId());
		employeeRequest.setDOB(employee.getDOB());
		employeeRequest.setEmail(employee.getEmail());
		employeeRequest.setFirstName(employee.getFirstName());
		employeeRequest.setHireDate(employee.getHireDate());
		employeeRequest.setLastName(employee.getLastName());
		employeeRequest.setManagerId(employee.getManagerId());
		employeeRequest.setMaritalStatus(employee.getMaritalStatus());
		employeeRequest.setNationality(employee.getNationality());
		employeeRequest.setPhoneNumber(employee.getPhoneNumber());
		employeeRequest.setSalary(employee.getSalary());

		return employeeRequest;
	}

}
