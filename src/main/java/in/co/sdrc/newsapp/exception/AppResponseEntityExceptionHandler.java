package in.co.sdrc.newsapp.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class AppResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ItemNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleItemNotFoundException(ItemNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "Item not found");
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ItemIdAlreadyExistsException.class)
	public final ResponseEntity<ExceptionResponse> handleItemIdAlreadyExistsException(ItemIdAlreadyExistsException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "Item Id already exists. please try another one");
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(AssignmentNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleAssignmentNotFoundException(AssignmentNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "Assignment not found");
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ItemAlreadyAssignedException.class)
	public final ResponseEntity<ExceptionResponse> handleItemAlreadyAssignedException(ItemAlreadyAssignedException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "Item already assigned.");
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidAssignmentException.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidAssignmentException(InvalidAssignmentException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "Invalid assignment.");
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT);
	}
	

	@ExceptionHandler(InvalidDateFormatException.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidDateFormatException(InvalidDateFormatException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "Invalid date format.");
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DBOperationFailedException.class)
	public final ResponseEntity<ExceptionResponse> handleDBOperationFailedException(DBOperationFailedException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "Database operation failed");
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidInputException.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidInputException(InvalidInputException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "Invalid Input Exception");
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LogFileNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleLogFileNotFoundException(LogFileNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "Not found");
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}



}
