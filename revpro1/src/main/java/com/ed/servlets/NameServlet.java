package com.ed.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ed.daos.EmployeeDAO;
import com.ed.daos.EmployeeDAOImpl;
import com.ed.models.Employee;
import com.ed.utils.User;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class NameServlet
 */
public class NameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!User.isNull() && !User.isManager()) {
			EmployeeDAO ed = new EmployeeDAOImpl();
			ObjectMapper om = new ObjectMapper();
			PrintWriter out = response.getWriter();
			Employee employees = ed.getEmployeeFromID(User.getUserId());
			String nameString = om.writeValueAsString(employees.getfName()+" "+employees.getlName());
			out.print(nameString);
		} else {
			request.getRequestDispatcher("Login.html").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
