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
import com.ed.models.Reimbursements;
import com.ed.utils.ConnectionUtil;

public class ReimbursementsDAOImpl implements ReimbursementsDAO {
	private static Logger log = Logger.getRootLogger();

	private List<Reimbursements> getReimbursements(String db) {
		List<Reimbursements> reimbursementsList = new ArrayList<>();
		ResultSet rs = null;
		try(Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();){
					rs = s.executeQuery(db);
					while (rs.next()) {
						Reimbursements r = populateReimbursements(rs, con);
						reimbursementsList.add(r);
					}
				} catch (SQLException e) {
					log.error("SQL Exception error",e);
				} finally {
					if (rs!=null) {
						try {
							rs.close();
						} catch (SQLException e) {
							log.error("closing failed",e);
						}
					}
				}
		return reimbursementsList;
	}
	
	@Override
	public List<Reimbursements> getReimbursements(){
		return getReimbursements("SELECT * FROM reimbursements.reimbursementrequests");
	}

	@Override
	public Reimbursements getReimbursementByID(int reimbursementID) {
		Reimbursements re = null;
		String sql = "SELECT * FROM reimbursements.reimbursementrequests WHERE reimbursementid = ?";
		ResultSet rs = null;
		try(Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, reimbursementID);
			rs = ps.executeQuery();
			while(rs.next()) {
				re = populateReimbursements(rs, con);
			}
		}catch (SQLException e) {
			log.error("SQL Exception",e);
		}finally {
			try {
				rs.close();
			} catch(SQLException e) {
				log.error("closer failed",e);
			}
		}
		return re;
	}

	@Override
	public Reimbursements getReimbursementByID(int reimbursementID, Connection con) {
		Reimbursements re = null;
		String sql = "SELECT * FROM reimbursements.reimbursementrequests WHERE reimbursementid = ?";
		ResultSet rs = null;
		try(PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, reimbursementID);
			rs = ps.executeQuery();
			while (rs.next()) {
				int reID = rs.getInt("reimbursementid");
				String reason = rs.getString("reason");
				double amount = rs.getDouble("amount");
				int accountID = rs.getInt("accountid");
				boolean isPending = rs.getBoolean("pending");
				boolean isApproved = rs.getBoolean("approved");
				boolean isDenied = rs.getBoolean("denied");
				EmployeeDAO ed = new EmployeeDAOImpl();
				Employee e = ed.getEmployeeFromID(accountID, con);
				
				re = new Reimbursements();
				re.setReimbursementID(reID);
				re.setReason(reason);
				re.setAmount(amount);
				re.setEmployee(e);
				re.setPending(isPending);
				re.setApproved(isApproved);
				re.setDenied(isDenied);
			}
		}catch(SQLException e) {
			log.error("SQL Exception",e);
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch (SQLException e) {
					log.error("Closer failed",e);
				}
			}
		}
		return re;
	}

	@Override
	public List<Reimbursements> getPendingReimbursements() {
		return getReimbursements("SELECT * FROM reimbursements.reimbursementrequests WHERE ispending = TRUE");
	}

	@Override
	public List<Reimbursements> getApprovedReimbursements() {
		return getReimbursements("SELECT * FROM reimbursements.reimbursementrequests WHERE isapproved = TRUE");

	}
	
	@Override
	public List<Reimbursements> getDeniedReimbursements() {
		return getReimbursements("SELECT * FROM reimbursements.reimbursementrequests WHERE isdenied = TRUE");

	}
	
	private List<Reimbursements> getReimbursementFromEmployeeID(int employeeID, String sql){
		List<Reimbursements> re = new ArrayList<>();
		ResultSet rs = null;
		try(Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, employeeID);
			rs = ps.executeQuery();
			while(rs.next()) {
				Reimbursements reim = populateReimbursements(rs, con);
				re.add(reim);
			}
		}catch(SQLException e) {
			log.error("SQL Exception",e);
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch (SQLException e) {
					log.error("Closer failed",e);
				}
			}
		}
		return re;
	}

	@Override
	public List<Reimbursements> getReimbursementFromEmployeeID(int employeeID) {
		return getReimbursementFromEmployeeID(employeeID, "SELECT * FROM reimbursements.reimbursementrequests WHERE accountid = ?");
	}

	@Override
	public List<Reimbursements> getPendingReimbursementsFromEmployeeID(int employeeID) {
		String sql = "SELECT * FROM reimbursements.reimbursementrequests WHERE accountid = ? AND pending = TRUE";
		return getReimbursementFromEmployeeID(employeeID, sql);
	}

	@Override
	public List<Reimbursements> getApprovedReimbursementsFromEmployeeID(int employeeID) {
		String sql = "SELECT * FROM reimbursements.reimbursementrequests WHERE accountid = ? AND approved = TRUE";
		return getReimbursementFromEmployeeID(employeeID, sql);
	}

	@Override
	public List<Reimbursements> getDeniedReimbursementsFromEmployeeID(int employeeID) {
		String sql = "SELECT * FROM reimbursements.reimbursementrequests WHERE accountid = ? AND denied = TRUE";
		return getReimbursementFromEmployeeID(employeeID, sql);
	}

	@Override
	public void addReimbursement(Reimbursements r, Employee em) {
		r.setReimbursementID(getNextID());
		String sql = "INSERT INTO reimbursements.reimbursementrequests(reimbursementid, reason, amount, accountid, pending, approved, denied) VALUES (?, ?, ?, ?, ?, ?, ?);";
		try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);){
			System.out.println("inside add try");

			ps.setInt(1,  r.getReimbursementID());
			ps.setString(2,  r.getReason());
			ps.setDouble(3, r.getAmount());
			int accountID = em.getAccountID();
			ps.setInt(4, accountID);
			ps.setBoolean(5, r.isPending());
			ps.setBoolean(6, r.isApproved());
			ps.setBoolean(7,  r.isDenied());
			System.out.println(ps);
			ps.executeQuery();
		}catch (SQLException e) {
			log.error("SQL exception", e);
		}
	}
	
	private int getNextID() {
		int nextID = 0;
		ResultSet rs = null;
		try (Connection con = ConnectionUtil.getConnection(); Statement s = con.createStatement();){
			rs = s.executeQuery("SELECT MAX(reimbursementid) FROM reimbursements.reimbursementrequests");
			if (rs.next()) {
				nextID = rs.getInt(1) + 1;
			}
		}catch(SQLException e) {
			log.error("SQL Exception",e);
		}finally {
			if (rs != null) {
				try {
					rs.close();
				}catch (SQLException e) {
					log.error("Closing failed",e);
				}
			}
		}
		return nextID;
	}
	
	private Reimbursements populateReimbursements(ResultSet rs, Connection con) throws SQLException {
		int reimbursementID = rs.getInt("reimbursementid");
		String reason = rs.getString("reason");
		Double amount = rs.getDouble("amount");
		int accountID = rs.getInt("accountid");
		boolean pending = rs.getBoolean("pending");
		boolean approved = rs.getBoolean("approved");
		boolean denied = rs.getBoolean("denied");
		EmployeeDAO ed = new EmployeeDAOImpl();
		Employee e = ed.getEmployeeFromID(accountID, con);
		
		Reimbursements r = new Reimbursements();
		r.setReimbursementID(reimbursementID);
		r.setReason(reason);
		r.setAmount(amount);
		r.setPending(pending);
		r.setApproved(approved);
		r.setDenied(denied);
		r.setEmployee(e);
		return r;
	}

}
