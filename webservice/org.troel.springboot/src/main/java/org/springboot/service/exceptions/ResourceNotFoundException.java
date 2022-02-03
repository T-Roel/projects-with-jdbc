package org.springboot.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4757256973098066851L;

	public ResourceNotFoundException(Object id) {
		super("Resource not found. Id " + id);
	}
}
