package org.jdbc.services;

import java.util.List;

import org.jdbc.model.dao.DaoFactory;
import org.jdbc.model.dao.SellerDao;
import org.jdbc.model.entities.Seller;

public class SellerService {

	private SellerDao dptDao = DaoFactory.createSellerDao();

	public List<Seller> findAll() {
		return dptDao.findAll();
	}

	public void saveOrUpdate(Seller dept) {
		if (dept.getId() == null) {
			dptDao.insert(dept);
		} else {
			dptDao.update(dept);
		}
	}

	public void removeDepart(Seller depart) {
		dptDao.deleteById(depart.getId());
	}
}
