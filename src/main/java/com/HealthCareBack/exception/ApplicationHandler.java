package com.HealthCareBack.exception;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.HealthCareBack.securityConfig.ResponseStructure;

@RestControllerAdvice
public class ApplicationHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> UserNotFoundById(UserNotFoundByIdException ex){
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("User with this givan ID doesn't Exist!!");
		
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.NOT_FOUND);
	}
	
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) 
	{
		//Here we receiveing all the field error as objecterror type,
		//as there can be multiple field errors so we are adding it inside a list
		//using the ex.getAllErrors method present inside MethodArgumentNotValidException
		//class.
		
		List<ObjectError> errorList=ex.getAllErrors();
		//creating a Map because we need to print the error as key and value()pair
		//(field:message)
		
		Map<String, String> errorMap=new HashMap<String,String>();
		
		//A for each loop to iterate through the errorList
		for(ObjectError error:errorList)
		{
			//Downcasting it to FieldError class type in order to fetch the field name
			//using fieldError.getField().method.
	      FieldError fieldError=(FieldError) error;
	      String field= fieldError.getField();
	      
	      
	      //Getting the default error message of the set by us in the validations
	      //using the getDefaultMessage()method present in ObjectError class.
	      String message=error.getDefaultMessage();
	      
	      //Adding the field and message inside the Map as key and value pair.
	      errorMap.put(field, message);
		}
		return new ResponseEntity<Object>(errorMap,HttpStatus.BAD_REQUEST);
	}
}