package jmaster.io.demo.controller;




import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.NoResultException;
import jmaster.io.demo.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

	// Logger logger = LoggerFactory.getLogger(this.getClass());
	@ExceptionHandler({NoResultException.class})
	public ResponseDTO<String> notFound(NoResultException e) {
		//logger.info("INFO",e);
		log.info("INFO",e);
		return ResponseDTO.<String>builder()
				.status(404).msg("No Data").build();
	}
	@ExceptionHandler({BindException.class} )
	@ResponseStatus(code= HttpStatus.BAD_REQUEST)
	public ResponseDTO<String> badRequest(BindException e) {
	
		log.info("bad request");
		List<ObjectError> errors=e.getBindingResult().getAllErrors();
		String msg="";
		for (ObjectError err: errors) {
			FieldError fieldError= (FieldError) err;
			
			msg += fieldError.getField() + ":" + err.getDefaultMessage() + ";";
		}
		return ResponseDTO.<String>builder()
				.status(400).msg(msg).build();

					
	}
	
	@ExceptionHandler({SQLIntegrityConstraintViolationException.class})
	@ResponseStatus(code= HttpStatus.CONFLICT)
	public ResponseDTO<String> duplicated(SQLIntegrityConstraintViolationException e) {
		//logger.info("INFO",e);
		log.info("INFO",e);
		return ResponseDTO.<String>builder()
				.status(409).msg("Duplicated Data").build();
	}
}
