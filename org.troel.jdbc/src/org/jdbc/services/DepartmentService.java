package org.jdbc.services;

import java.util.ArrayList;
import java.util.List;

import org.jdbc.model.entities.Department;

public class DepartmentService {

	public List<Department> findAll() {
		List<Department> deps = new ArrayList<Department>();
		deps.add(new Department(1, "Books"));
		deps.add(new Department(2, "Computers"));
		deps.add(new Department(1, "Electronics"));
		
		return deps;
	}
	
}
