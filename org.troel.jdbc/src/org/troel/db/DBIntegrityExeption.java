package org.troel.db;

public class DBIntegrityExeption extends RuntimeException {

	private static final long serialVersionUID = 303365069353573944L;

	public DBIntegrityExeption(String msg) {
		super(msg);
	}
}
