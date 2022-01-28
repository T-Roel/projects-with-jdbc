package org.jdbc.db;

public class DBExeption extends RuntimeException {

	private static final long serialVersionUID = 8004063964475285665L;

	public DBExeption(String msg) {
		super(msg);
	}
}
