package YukEcmWeb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import yukcommon.exception.EcmNormalError;

@ControllerAdvice
@RestController
public class CtrAdvice {
	@ExceptionHandler(EcmNormalError.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleAppException(EcmNormalError ex) {
	  return ex.getMessage();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleAppException(Exception ex) {
	  return ex.getMessage();
	}
}
