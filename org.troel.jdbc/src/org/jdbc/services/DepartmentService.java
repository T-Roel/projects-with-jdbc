package org.jdbc.services;

import java.util.List;

import org.jdbc.model.dao.DaoFactory;
import org.jdbc.model.dao.DepartmentDao;
import org.jdbc.model.entities.Department;

public class DepartmentService {

	private DepartmentDao dptDao = DaoFactory.createDepartmentDao();
	
	public List<Department> findAll() {
		return dptDao.findAll();
	}
	
	public void saveOrUpdate(Department dept) {
		if(dept.getId() == null) {
			dptDao.insert(dept);
		}else {
			dptDao.update(dept);
		}
	}
}
