package com.globallogic.users.bean;

public class ErrorMessage {
	
	private String mensaje;
	
	public ErrorMessage(String msg) {
		this.mensaje = msg;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
