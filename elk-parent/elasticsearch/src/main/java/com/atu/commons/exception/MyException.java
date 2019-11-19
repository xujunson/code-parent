package com.atu.commons.exception;
/**
 * 自定义异常
 */
public class MyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4501729179439491771L;
	private String message;
	@Override
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MyException(String message) {
		super();
		this.message = message;
	}
	public MyException() {
		super();
	}
	

}
