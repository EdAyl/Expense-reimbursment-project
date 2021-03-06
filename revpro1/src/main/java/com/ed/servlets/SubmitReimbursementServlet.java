package com.ed.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ed.daos.EmployeeDAOImpl;
import com.ed.daos.ReimbursementsDAO;
import com.ed.daos.ReimbursementsDAOImpl;
import com.ed.models.Employee;
import com.ed.models.Reimbursements;
import com.ed.utils.User;


/**
 * Servlet implementation class SubmitReimbursementServlet
 */
public class SubmitReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitReimbursementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher reqD = null;
		if (!User.isNull() && !User.isManager()) {
			double amount = Double.parseDouble(request.getParameter("amount"));
			String reason = request.getParameter("reason");
			ReimbursementsDAO rd = new ReimbursementsDAOImpl();
			Reimbursements r = new Reimbursements();
			r.setPending(true);
			r.setApproved(false);
			r.setDenied(false);
			r.setAmount(amount);
			r.setReason(reason);
			Employee e = new EmployeeDAOImpl().getEmployeeFromID(User.getUserId());
			r.setEmployee(e);
			rd.addReimbursement(r, e);
			reqD = request.getRequestDispatcher("Reimbursements.html");
		} else {
			reqD = request.getRequestDispatcher("Login.html");
		}
		reqD.forward(request, response);
	}
	}
