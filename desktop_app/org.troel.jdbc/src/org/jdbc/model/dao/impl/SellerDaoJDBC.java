package org.jdbc.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdbc.db.DB;
import org.jdbc.db.DBExeption;
import org.jdbc.model.dao.SellerDao;
import org.jdbc.model.entities.Department;
import org.jdbc.model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	
	private static final String SQL_FIND_BY_ID = "SELECT seller.*,department.Name as DepName FROM seller INNER JOIN department ON seller.department_id = department.department_id WHERE seller.seller_id = ?";
	private static final String SQL_FIND_BY_DEPARTMENT = "SELECT seller.*,department.Name as DepName FROM seller INNER JOIN department ON seller.department_id = department.department_id WHERE seller.department_id = ? ORDER BY Name";
	private static final String SQL_FIND_ALL = "SELECT seller.*,department.Name as DepName FROM seller INNER JOIN department ON seller.department_id = department.department_id ORDER BY Name";
	private static final String SQL_INSERT = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, department_id) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, department_id = ? WHERE seller_id = ?";
	private static final String SQL_DELETE = "DELETE FROM seller WHERE seller_id = ?";
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DBExeption("Unexpected error! No rows affected!");
			}
		}catch(SQLException e) {
			throw new DBExeption(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(SQL_UPDATE);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
		}catch(SQLException e) {
			throw new DBExeption(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(SQL_DELETE);
			st.setInt(1, id);
			st.executeUpdate();
		}catch(SQLException e) {
			throw new DBExeption(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(SQL_FIND_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
			return null;
		}catch(SQLException e) {
			throw new DBExeption(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(SQL_FIND_ALL);
			rs = st.executeQuery();
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				
				Department depart = map.get(rs.getInt("department_id"));
				
				if(depart == null) {
					depart = instantiateDepartment(rs);
					map.put(rs.getInt("department_id"), depart);
				}
					
				Seller obj = instantiateSeller(rs, depart);
				list.add(obj);
			}
			return list;
		}catch(SQLException e) {
			throw new DBExeption(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public List<Seller> findByDepartment(Department dep) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(SQL_FIND_BY_DEPARTMENT);
			st.setInt(1, dep.getId());
			rs = st.executeQuery();
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				
				Department depart = map.get(rs.getInt("department_id"));
				
				if(depart == null) {
					depart = instantiateDepartment(rs);
					map.put(rs.getInt("department_id"), depart);
				}
					
				Seller obj = instantiateSeller(rs, depart);
				list.add(obj);
			}
			return list;
		}catch(SQLException e) {
			throw new DBExeption(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("seller_id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(new java.util.Date(rs.getTimestamp("BirthDate").getTime())); 
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("department_id"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
}
