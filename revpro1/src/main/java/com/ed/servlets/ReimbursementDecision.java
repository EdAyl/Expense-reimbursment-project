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
 * Servlet implementation class ReimbursementDecision
 */
public class ReimbursementDecision extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReimbursementDecision() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher reqD = null;
		if (!User.isNull() && User.isManager()) {
			int id = Integer.parseInt(request.getParameter("reimbursementid"));
			String decision = request.getParameter("decision");
			ReimbursementsDAO rd = new ReimbursementsDAOImpl();
			Reimbursements r = rd.getReimbursementByID(id);
			if(decision == "approve") {
				rd.approveReimbursement(r);
			}else {
				rd.denyReimbursement(r);
			}
			reqD = request.getRequestDispatcher("Reimbursements.html");
		} else {
			reqD = request.getRequestDispatcher("Login.html");
		}
		reqD.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
