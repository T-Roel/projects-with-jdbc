package org.jdbc.model.dao;

import org.jdbc.db.DB;
import org.jdbc.model.dao.impl.DepartmentDaoJDBC;
import org.jdbc.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	
}
