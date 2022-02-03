package org.springboot.service.exceptions;

public class DatabaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1876195860020819225L;

	public DatabaseException(String msg) {
		super(msg);
	}
}
