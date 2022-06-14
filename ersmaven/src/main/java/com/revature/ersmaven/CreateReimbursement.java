package com.revature.ersmaven;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.revature.services.AuthService;
import com.revature.services.ReimbursementService;
import com.revature.util.ConnectionFactory;

/**
 * Servlet implementation class CreateReimbursementServlet
 */
@WebServlet("/CreateReimbursement")
@MultipartConfig(maxFileSize = 16177215)
public class CreateReimbursement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	//This annotation defines the maximum file size which can be taken.
	ReimbursementService reimbursementService = new ReimbursementService();
	AuthService authService = new AuthService();

    public CreateReimbursement() {
        super();
        // TODO Auto-generated constructor stub
    }
    public int uploadFile(String amount, String description, InputStream file) {
		String sql = "INSERT INTO reimbursement(amount, description ,receipt_image) values (?, ?, ?)";
		int row = 0;

		Connection conn = ConnectionFactory.getInstance().getConnection();
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, amount);
			preparedStatement.setString(2, description);
			if (file != null) {
				// Fetches the input stream
				// of the upload file for
				// the blob column
				preparedStatement.setBlob(3, file);
			}

			// Sends the statement to
			// the database server
			row = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return row;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	 // protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Getting the parameters from web page
		String amount = request.getParameter("amount");

		String description= request.getParameter("description");

		// Input stream of the upload file
		InputStream inputStream = null;

		String message = null;

		// Obtains the upload file
		// part in this multipart request
		Part filePart = request.getPart("receipt_image");

		if (filePart != null) {

			// Prints out some information
			// for debugging
			System.out.println(filePart.getName());
			System.out.println(filePart.getSize());
			System.out.println(filePart.getContentType());

			// Obtains input stream of the upload file
			inputStream = filePart.getInputStream();
		}

		// Sends the statement to the database server
		int row = uploadFile(amount, description, inputStream);
		if (row > 0) {
			message = "File uploaded and saved into database";
		}
		System.out.println(message);
	}

		// doGet(request, response);
	}

