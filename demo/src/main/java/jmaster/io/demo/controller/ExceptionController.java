package jmaster.io.demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionController {
	
	//Logger logger = LoggerFactory.getLogger(this.getClass());
	@ExceptionHandler({NoResultException.class})
	public String notFound(Exception e) {
		//logger.info("INFO",e);
		log.info("INFO",e);
		return "no-data.html";
	}
}
