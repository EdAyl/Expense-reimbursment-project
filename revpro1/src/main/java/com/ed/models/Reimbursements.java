package com.ed.models;

public class Reimbursements {
	private int reimbursementID;
	private Employee employee;
	private String reason;
	private double amount;
	private boolean pending;
	private boolean approved;
	private boolean denied;

	public Reimbursements() {
		super();
	}
	
	public Reimbursements(int reimbursementID, Employee employee, String reason, double amount, boolean pending, boolean approved, boolean denied) {
		this.reimbursementID = reimbursementID;
		this.reason = reason;
		this.amount = amount;
		this.pending = pending;
		this.approved = approved;
		this.denied = denied;
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getReimbursementID() {
		return reimbursementID;
	}

	public void setReimbursementID(int reimbursementID) {
		this.reimbursementID = reimbursementID;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isDenied() {
		return denied;
	}

	public void setDenied(boolean denied) {
		this.denied = denied;
	}
	
	

}
