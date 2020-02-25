package com.employee.api.controller.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.employee.api.application.employee.EmployeeApplication;
import com.employee.api.controller.DeleteResponse;
import com.employee.api.controller.IdsRequest;
import com.employee.api.error.exception.EmployeeException;
import com.employee.api.error.exception.ExceptionAdvisor;
import com.employee.api.error.exception.GenericException;

@RequestMapping(value = "/v1/employee", produces = "application/json")
public class EmployeeController extends ExceptionAdvisor {

	@Autowired
	private EmployeeApplication application;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody EmployeeListDTO getById(@PathVariable(value = "id") Long id) throws GenericException {
		return application.getById(id);
	}

	@RequestMapping(value = "/ids", method = RequestMethod.GET)
	public @ResponseBody EmployeeListDTO getByIds(@RequestParam(value = "request") IdsRequest idsRequest)
			throws GenericException {
		return application.getByIds(idsRequest);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public @ResponseBody EmployeeListDTO filter(@RequestParam(value = "request") FilterRequest filter)
			throws GenericException {
		return application.getByFilter(filter);
	}

	@RequestMapping(value = "/raise/{id}/{salary}", method = RequestMethod.GET)
	public @ResponseBody EmployeeListDTO raiseSalary(@PathVariable(value = "id") Long id,
			@PathVariable(value = "ratio") Double ratio) throws GenericException {
		return application.raiseSalary(id, ratio);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public EmployeeListDTO save(@RequestBody EmployeeRequest request, @RequestParam("resume") MultipartFile resume)
			throws GenericException {
		return application.save(request, resume);
	}

	@RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
	public ResumeResponse uploadResume(@PathVariable(value = "id") Long id,
			@RequestParam("resume") MultipartFile resume) throws GenericException {
		return application.uploadResume(resume, id);
	}

	@RequestMapping(value = "/download/{id}", method = RequestMethod.POST)
	public byte[] searchResume(@PathVariable(value = "id") Long id) throws GenericException {
		return application.downloadResume(id);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public DeleteResponse delete(@PathVariable(value = "id") Long id) throws EmployeeException {
		return application.delete(id);
	}

}
