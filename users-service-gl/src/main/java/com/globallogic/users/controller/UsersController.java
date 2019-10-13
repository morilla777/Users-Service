package com.globallogic.users.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.users.bean.ErrorMessage;
import com.globallogic.users.bean.RegisterUserMessage;
import com.globallogic.users.bean.User;
import com.globallogic.users.common.MessagesEnum;
import com.globallogic.users.exception.RegisteredEmailException;
import com.globallogic.users.exception.TokenNotFoundException;
import com.globallogic.users.service.UserService;


@RestController
@RequestMapping(value = "/users")
public class UsersController {
	
	private static final String SPACE = " ";
	private static final String BEARER = "Bearer";
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/health",produces = "application/json")
    public @ResponseBody ResponseEntity<Object> health() {	
		return new ResponseEntity<>(new ErrorMessage("OK"), HttpStatus.OK);
    }
	
	
	@PostMapping(value = "/register",consumes = "application/json",produces = "application/json")
    public @ResponseBody ResponseEntity<Object> register(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody User user) {	
		
		try {
			final String token = getToken(authorizationHeader);
			RegisterUserMessage registerUserMessage = userService.register(user, token);
			return new ResponseEntity<>(registerUserMessage, HttpStatus.OK);
		}
		catch(TokenNotFoundException ex) {
			return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.UNAUTHORIZED);
		}
		catch(RegisteredEmailException ex) {
			return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
		catch(Exception ex) {
			return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
		}
		
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorMessage handleValidationExceptions(MethodArgumentNotValidException ex) {
		return new ErrorMessage(ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ErrorMessage missingRequestHeaderException(MissingRequestHeaderException ex) {
		return new ErrorMessage(ex.getMessage());
	}
	
	
	private String getToken(String authorizationHeader) throws TokenNotFoundException{
		
		try {
			String[] arrayAuthorizationHeader = authorizationHeader.split(SPACE);
			
			if(!BEARER.equals(arrayAuthorizationHeader[0])) {
				throw new TokenNotFoundException();
			}
			
			return arrayAuthorizationHeader[1];
		}
		catch(Exception ex) {
			throw new TokenNotFoundException(MessagesEnum.MSG_TOKEN_NOT_FOUND.getMessage());
		}
	}

}
