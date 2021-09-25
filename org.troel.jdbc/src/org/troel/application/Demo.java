package org.troel.application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.troel.db.DB;
import org.troel.db.DBExeption;

public class Demo {

	public static void main(String[] args) {

		/*
		// RECUPERANDO DADOS
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM department");
			
			while(rs.next()) {
				System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
		*/
		
		/*
		// INSERINDO DADOS
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
		String sql = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?,?,?,?,?)";
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, "Rogério Gomes");
			st.setString(2, "gomes@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("18/04/1995").getTime()));
			st.setDouble(4, 3000.0);
			st.setInt(5, 4);
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				while(rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Id = " + id);
				}
				
			}else {
				System.out.println("No rows affected!");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
		*/
		
		/*
		// ATUALIZAR DADOS
		Connection conn = null;
		PreparedStatement st = null;
		String update = "UPDATE seller SET BaseSalary = BaseSalary + ? WHERE (DepartmentId = ?)";
		
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement(update);
			
			st.setDouble(1, 200.0);
			st.setInt(2, 2);
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows Affected: " + rowsAffected);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
		*/
		
		/*
		//	DELETAR DADOS
		Connection conn = null;
		PreparedStatement st = null;
		String delete = "DELETE FROM department WHERE Id = ? ";
		
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement(delete);
			
			st.setInt(1, 2);
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows Affected: " + rowsAffected);
			
		}catch(SQLException e) {
			throw new DBIntegrityExeption(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
		*/
		
		// TRANSACOES
		Connection conn = null;
		Statement st = null;
		String firstUpdate = "UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1";
		String secondUpdate = "UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2";
		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			
			int rows1 = st.executeUpdate(firstUpdate);
			
			//int x = 1;
			//if(x < 2) {
			//	throw new SQLException("Fake error");
			//}
			
			int rows2 = st.executeUpdate(secondUpdate);
			
			conn.commit();
			
			System.out.println("rows1: " + rows1);
			System.out.println("rows2: " + rows2);
		}catch(SQLException e) {
			try {
				conn.rollback();
				throw new DBExeption("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBExeption("Error trying to rollback! Caused by: " + e1.getMessage());
			}
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}		
}
