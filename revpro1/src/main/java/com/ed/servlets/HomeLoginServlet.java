package com.ed.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ed.daos.EmployeeDAO;
import com.ed.daos.EmployeeDAOImpl;
import com.ed.models.Employee;
import com.ed.utils.User;





/**
 * Servlet implementation class HomeLoginServlet
 */
public class HomeLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rq = null;
		if (request.getParameter("button").equals("Login")) {
			EmployeeDAO ed = new EmployeeDAOImpl();
			String username = request.getParameter("username");
			Employee employee = ed.getEmployeeFromUsername(username);
			String password = request.getParameter("password");
			if (employee.login(password)) {
				User.setUser(employee.getAccountID(), false);
				rq = request.getRequestDispatcher("EmployeeHome.html");
			} else {
				rq = request.getRequestDispatcher("Login.html");
			}
		}
		rq.forward(request, response);
	}

}
