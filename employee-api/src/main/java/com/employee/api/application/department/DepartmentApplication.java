package com.employee.api.application.department;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.api.application.AbstractApplication;
import com.employee.api.controller.DeleteResponse;
import com.employee.api.controller.IdsRequest;
import com.employee.api.controller.department.DepartmentListDTO;
import com.employee.api.controller.department.DepartmentRequest;
import com.employee.api.domain.Department;
import com.employee.api.error.DefaultError;
import com.employee.api.error.DepratmentError;
import com.employee.api.error.exception.DepartmentException;
import com.employee.api.error.exception.GenericException;
import com.employee.api.service.department.DepartmentList;
import com.employee.api.service.department.DepartmentService;
import com.employee.api.service.employee.EmployeeService;
import com.employee.api.util.Assert;
import com.employee.api.util.Pageable;

@Service
public class DepartmentApplication extends AbstractApplication {

	private Logger log = LoggerFactory.getLogger(DepartmentApplication.class);

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private EmployeeService employeeService;

	public DepartmentListDTO getById(Long id) throws DepartmentException {

		if (Assert.isNull(id)) {
			log.debug(String.format("dep Id %s may not be null nor empty", id));
			throw new DepartmentException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("dep Id %s may not be null nor empty", id));
		}

		Optional<DepartmentList> depratments;
		try {
			depratments = departmentService.getById(id);
		} catch (GenericException e) {
			log.error(e.getMessage(), e);
			throw new DepartmentException(DepratmentError.DEPARTMENT_NOT_FOUND, e);
		}

		if (!depratments.isPresent()) {
			log.debug(String.format("dept %s received by service is not present", depratments));
			throw new DepartmentException(DepratmentError.DEPARTMENT_NOT_FOUND, "dept is not present");
		}

		DepartmentListDTO departmentListDTO = populate(departmentService, employeeService, depratments.get());
		return departmentListDTO;
	}

	public DepartmentListDTO getByIds(IdsRequest idsRequest) throws DepartmentException {

		if (Assert.isNull(idsRequest)) {
			log.debug(String.format("dep Ids %s may not be null nor empty", idsRequest));
			throw new DepartmentException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("dep Ids %s may not be null nor empty", idsRequest));
		}

		Pageable pageable = populateRequestPageable(idsRequest);

		Optional<DepartmentList> depratments;
		try {
			depratments = departmentService.getByIds(pageable, idsRequest.getIds());
		} catch (GenericException e) {
			log.error(e.getMessage(), e);
			throw new DepartmentException(DepratmentError.DEPARTMENT_NOT_FOUND, e);
		}

		if (!depratments.isPresent()) {
			log.debug(String.format("dept %s received by service is not present", depratments));
			throw new DepartmentException(DepratmentError.DEPARTMENT_NOT_FOUND, "dept is not present");
		}

		DepartmentListDTO departmentListDTO = populate(departmentService, employeeService, depratments.get());
		return departmentListDTO;
	}

	public DepartmentListDTO save(DepartmentRequest request) throws DepartmentException {

		if (Assert.isNull(request)) {
			log.debug(String.format("Request %s sent by client is null", request));
			throw new DepartmentException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("Request %s sent by client is null", request));
		}

		Department department = populate(request);
		Optional<DepartmentList> departments;
		try {
			departments = departmentService.save(department);
		} catch (GenericException e) {
			log.error(e.getMessage(), e);
			throw new DepartmentException(DefaultError.INTERNAL_SERVER_ERROR, e);
		}

		if (!departments.isPresent()) {
			log.debug(String.format("Employeed %s received by service is not present", departments));
			throw new DepartmentException(DepratmentError.DEPARTMENT_NOT_FOUND, "dept is not present");
		}

		DepartmentListDTO departmentListDTO = populate(departmentService, employeeService, departments.get());
		return departmentListDTO;
	}

	public DeleteResponse delete(Long id) throws DepartmentException {
		DeleteResponse deleteResponse = new DeleteResponse();

		if (Assert.isEmpty(id)) {
			deleteResponse.setDeleted(false);
			log.warn(String.format("Unable to delete dep from DB, Empty dep id."));
			return deleteResponse;
		}

		try {
			boolean isDeleted = departmentService.delete(id);
			deleteResponse.setDeleted(isDeleted);

		} catch (GenericException e) {
			log.error(e.getMessage(), e);
			throw new DepartmentException(DepratmentError.DEPARTMENT_NOT_FOUND, e);
		}

		return deleteResponse;
	}

	private Department populate(DepartmentRequest request) throws DepartmentException {

		if (Assert.isNull(request)) {
			log.debug(String.format("Request %s sent by client is null", request));
			throw new DepartmentException(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST,
					String.format("Request %s sent by client is null", request));
		}

		Department.DepartmentBuilder departmentBuilder = new Department.DepartmentBuilder();

		departmentBuilder.withId(request.getId());
		departmentBuilder.withManagerId(request.getManagerId());
		departmentBuilder.withName(request.getName());

		return departmentBuilder.build();
	}

}
