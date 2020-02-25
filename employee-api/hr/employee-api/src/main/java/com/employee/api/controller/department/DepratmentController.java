package com.employee.api.controller.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.employee.api.application.department.DepartmentApplication;
import com.employee.api.controller.DeleteResponse;
import com.employee.api.controller.IdsRequest;
import com.employee.api.error.exception.DepartmentException;
import com.employee.api.error.exception.ExceptionAdvisor;

@RequestMapping(value = "/v1/depratment", produces = "application/json")
public class DepratmentController extends ExceptionAdvisor {

	@Autowired
	private DepartmentApplication application;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody DepartmentListDTO getById(@PathVariable(value = "id") Long id) throws DepartmentException {
		return application.getById(id);
	}

	@RequestMapping(value = "/ids", method = RequestMethod.GET)
	public @ResponseBody DepartmentListDTO getByIds(@RequestParam(value = "request") IdsRequest idsRequest)
			throws DepartmentException {
		return application.getByIds(idsRequest);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public DepartmentListDTO save(@RequestBody DepartmentRequest request) throws DepartmentException {
		return application.save(request);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public DeleteResponse delete(@PathVariable(value = "id") Long id) throws DepartmentException {
		return application.delete(id);
	}

}
