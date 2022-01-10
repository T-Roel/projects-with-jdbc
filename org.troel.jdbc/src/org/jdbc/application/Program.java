package org.jdbc.application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.jdbc.model.dao.DaoFactory;
import org.jdbc.model.dao.DepartmentDao;
import org.jdbc.model.dao.SellerDao;
import org.jdbc.model.entities.Department;
import org.jdbc.model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		// Seller
		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println("=== TEST 1: seller findById ===");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		System.out.println("\n=== TEST 2: seller findByDepartment ===");
		Department dep = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(dep);
		list.forEach(x -> {
			System.out.println(x);
		});

		System.out.println("\n=== TEST 3: seller findAll ===");
		List<Seller> listAll = sellerDao.findAll();
		listAll.forEach(x -> {
			System.out.println(x);
		});

		System.out.println("\n=== TEST 4: seller insert ===");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dep);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());

		System.out.println("\n=== TEST 5: seller update ===");
		seller = sellerDao.findById(1);
		seller.setName("Martha Waine");
		sellerDao.update(seller);
		System.out.println("Update Completed");

		System.out.println("\n=== TEST 6: seller delete ===");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(8);
		System.out.println("Delete complete!");

		// Department
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

		System.out.println("\n=== TEST 1: department findById =======");
		Department depart = departmentDao.findById(1);
		System.out.println(depart);

		System.out.println("\n=== TEST 2: department findAll =======");
		List<Department> listDep = departmentDao.findAll();
		listDep.forEach(x -> {
			System.out.println(x);
		});

		System.out.println("\n=== TEST 3: department insert =======");
		Department newDepartment = new Department(null, "Music");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New id: " + newDepartment.getId());

		System.out.println("\n=== TEST 4: department update =======");
		Department dep2 = departmentDao.findById(1);
		dep2.setName("Food");
		departmentDao.update(dep2);
		System.out.println("Update completed");

		System.out.println("\n=== TEST 5: department delete =======");
		System.out.print("Enter id for delete test: ");
		id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete completed");

		sc.close();
	}
}
