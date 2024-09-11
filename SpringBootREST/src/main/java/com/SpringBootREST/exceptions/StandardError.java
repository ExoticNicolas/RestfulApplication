package com.SpringBootREST.exceptions;

import java.io.Serializable;
import java.util.Date;

public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	private String message;
	private String error;
	private Date date;

	public StandardError() {

	}

	public StandardError(String message, String error, Date date) {
		this.message = message;
		this.error = error;
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}





}
