package com.ed.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ed.models.Employee;
import com.ed.utils.ConnectionUtil;

public class EmployeeDAOImpl implements EmployeeDAO {
	private static Logger log = Logger.getRootLogger();


	@Override
	public List<Employee> getEmployees() {
		List<Employee> employeeList = new ArrayList<>();
		String sql = "SELECT * FROM reimbursements.accountid";
		ResultSet rs = null;
		try(Connection con = ConnectionUtil.getConnection(); Statement s = con.createStatement();){
			rs = s.executeQuery(sql);
			while (rs.next()){
				int accountID = rs.getInt("accountid");
				String fName = rs.getString("firstname");
				String lName = rs.getString("lastname");
				String username = rs.getString("username");
				String pw = rs.getString("userpw");
				boolean isManager = rs.getBoolean("ismanager");
				
				Employee e = new Employee();
				e.setAccountID(accountID);
				e.setfName(fName);
				e.setlName(lName);
				e.setUsername(username);
				e.setPassword(pw);
				e.setManager(isManager);
				employeeList.add(e);
			}
		}catch(SQLException e) {
			log.error("SQL Exception",e);
		}finally {
			if (rs != null) {
				try {
					rs.close();
				}catch(SQLException e) {
					log.error("Closer failed",e);
				}
			}
		}
		return employeeList;
	}

	@Override
	public Employee getEmployeeFromID(int accountID) {
		Employee em = null;
		try(Connection con = ConnectionUtil.getConnection();){
			em = getEmployeeFromID(accountID, con);
		}catch(SQLException e) {
			log.error("SQL Exception",e);
		}
		return em;
	}

	@Override
	public Employee getEmployeeFromID(int accountID, Connection con) {
		Employee em = null;
		String sql = "SELECT * FROM reimbursements.accountid WHERE accountid = ?";
		ResultSet rs = null;
		try(PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1,  accountID);
			rs = ps.executeQuery();
			while(rs.next()) {
				String fName = rs.getString("firstname");
				String lName = rs.getString("lastname");
				String username = rs.getString("username");
				String pw = rs.getString("userpw");
				boolean isManager = rs.getBoolean("ismanager");
				
				em = new Employee();
				em.setAccountID(accountID);
				em.setfName(fName);
				em.setlName(lName);
				em.setUsername(username);
				em.setPassword(pw);
				em.setManager(isManager);
			}
		}catch(SQLException e) {
			log.error("SQL Exception",e);
		}
		return em;
	}

	@Override
	public Employee getEmployeeFromUsername(String username) {
		Employee em = null;
		String sql = "SELECT * FROM reimbursements.accountid WHERE username = ?";
		ResultSet rs = null;
		try(Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);){
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				int accountID = rs.getInt("accountid");
				String fName = rs.getString("firstname");
				String lName = rs.getString("lastname");
				String pw = rs.getString("userpw");
				boolean isManager = rs.getBoolean("ismanager");
				
				em = new Employee();
				em.setAccountID(accountID);
				em.setfName(fName);
				em.setlName(lName);
				em.setUsername(username);
				em.setPassword(pw);
				em.setManager(isManager);								
			}
		}catch(SQLException e) {
			log.error("SQL Exception", e);
		}
		return em;
	}
	
	@Override
	public int createEmployee(Employee employee) {
		return 0;
	}

}
