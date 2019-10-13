package com.globallogic.users.bean;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class User {

	private String name;
	
	@NotEmpty(message= "El campo 'email' es obligatorio")
	@Length(message = "El largo máximo del campo 'email' es 25",max = 25)
	@Email(message = "El formato del campo 'email' es inválido")
	private String email;
	@Length(message = "El largo máximo del campo 'password' es 10",max = 10)
	@NotEmpty(message= "El campo 'password' es obligatorio")
	@Pattern(regexp = "(([A-Z]{1})([a-z]{1,7})([1-9]{2}))", message = "El formato del campo 'password' es inválido")
	private String password;
	private List<Phone> phones;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

}
