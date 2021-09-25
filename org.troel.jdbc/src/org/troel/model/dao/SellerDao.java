package org.troel.model.dao;

import java.util.List;

import org.troel.model.entities.Department;
import org.troel.model.entities.Seller;

public interface SellerDao {

	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer id);
	List<Seller> findAll();
	List<Seller> findByDepartment(Department dep);
}
