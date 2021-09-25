package org.troel.model.dao;

import org.troel.db.DB;
import org.troel.model.dao.impl.DepartmentDaoJDBC;
import org.troel.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	
}
