package com.revature.ersmaven;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.services.AuthService;
import com.revature.util.ConnectionFactory;

/**
 * Servlet implementation class ApproveReimbursement
 */
@WebServlet("/ApproveReimbursement")
public class ApproveReimbursement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApproveReimbursement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		AuthService authService = new AuthService();
		ConnectionFactory.getInstance();
		Connection conn = ConnectionFactory.getConnection();
		try {
		HttpSession session = request.getSession(false);
		String resolver= (String)session.getAttribute("username");
			int id = Integer.parseInt(request.getParameter("id"));
			String query = "update reimbursement set status ='approved' where id=?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			// pstmt.setString(1, resolver);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			response.sendRedirect("LoginServlet");
		} catch (Exception e) {
			e.printStackTrace();
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
