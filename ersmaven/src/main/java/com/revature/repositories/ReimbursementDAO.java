package com.revature.repositories;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import com.revature.util.ConnectionFactory;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReimbursementDAO {
	AuthService authService = new AuthService();

	/**
	 * Should retrieve a Reimbursement from the DB with the corresponding id or an
	 * empty optional if there is no match.
	 */
	/**
	 * Should retrieve a Reimbursement from the DB with the corresponding id or an
	 * empty optional if there is no match.
	 */
	public Optional<Reimbursement> getById(int id) {
		Reimbursement reimburse = new Reimbursement();
		try {
			Connection conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from reimbursement where id=" + id);
			if (rs.next()) {

				reimburse.setId(rs.getInt("id"));
				reimburse.setAmount(rs.getDouble("amount"));
				reimburse.setAuthor(rs.getInt("author"));
				reimburse.setResolver(rs.getInt("resolver"));
				

//				String status = rs.getString("status");
//				String pending = "pending";
				if (rs.getString("status").equalsIgnoreCase("pending")) {
					reimburse.setStatus(Status.PENDING);
				} else if (rs.getString("status").equalsIgnoreCase("denied")) {
					reimburse.setStatus(Status.DENIED);
				} else if (rs.getString("status").equalsIgnoreCase("approved")) {
					reimburse.setStatus(Status.APPROVED);
				}
				
				reimburse.setCreationDate(rs.getDate("creation_date"));
				reimburse.setResolutionDate(rs.getDate("resolution_date"));
				reimburse.setReceipt((InputStream) rs.getObject("receipt_image"));

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Optional.of(reimburse);
	}

	/**
	 * Should retrieve a List of Reimbursements from the DB with the corresponding
	 * Status or an empty List if there are no matches.
	 */
	public List<Reimbursement> getByStatus(Status status) {
		List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();

		try {
			Connection conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from reimbursement where status=" + status);
			while (rs.next()) {
				Reimbursement reimburse = new Reimbursement();
				reimburse.setId(rs.getInt("id"));
				reimburse.setAmount(rs.getDouble("amount"));
				reimburse.setAuthor(rs.getInt("author"));
				reimburse.setResolver(rs.getInt("resolver"));

				// String pending = "pending";
				if (rs.getString("status").equalsIgnoreCase("pending")) {
					reimburse.setStatus(Status.PENDING);
				} else if (rs.getString("status").equalsIgnoreCase("denied")) {
					reimburse.setStatus(Status.DENIED);
				} else if (rs.getString("status").equalsIgnoreCase("approved")) {
					reimburse.setStatus(Status.APPROVED);
				}
				
				reimburse.setCreationDate(rs.getDate("creation_date"));
				reimburse.setResolutionDate(rs.getDate("resolution_date"));
				reimburse.setReceipt((InputStream) rs.getObject("receipt_image"));
				reimbursementList.add(reimburse);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return reimbursementList;
	}

	public List<Reimbursement> getAllReimbursements() {
		List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();

		try {
			Connection conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from reimbursement");
			while (rs.next()) {
				Reimbursement reimburse = new Reimbursement();
				reimburse.setId(rs.getInt("id"));
				reimburse.setAmount(rs.getDouble("amount"));
				reimburse.setAuthor(rs.getInt("author"));
				reimburse.setResolver(rs.getInt("resolver"));
				

	//			String status = rs.getString("status");
	//			String pending = "pending";
				if (rs.getString("status").equalsIgnoreCase("pending")) {
					reimburse.setStatus(Status.PENDING);
				} else if (rs.getString("status").equalsIgnoreCase("denied")) {
					reimburse.setStatus(Status.DENIED);
				} else if (rs.getString("status").equalsIgnoreCase("approved")) {
					reimburse.setStatus(Status.APPROVED);
				}
				

				reimburse.setCreationDate(rs.getDate("creation_date"));
				reimburse.setResolutionDate(rs.getDate("resolution_date"));
				reimburse.setReceipt((InputStream) rs.getObject("receipt_image"));
				reimbursementList.add(reimburse);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return reimbursementList;
	}

	/**
	 * <ul>
	 * <li>Should Update an existing Reimbursement record in the DB with the
	 * provided information.</li>
	 * <li>Should throw an exception if the update is unsuccessful.</li>
	 * <li>Should return a Reimbursement object with updated information.</li>
	 * </ul>
	 */
	/**
	 * @param unprocessedReimbursement
	 * @return
	 */
	public Reimbursement update(Reimbursement unprocessedReimbursement) {
		Reimbursement reimburse = new Reimbursement();
		try {
			Connection conn = ConnectionFactory.getConnection();
			String updateQuery = "UPDATE reimbursmeent SET resolver = ?, status = ?,resolution_date = CURRENT_TIMESTAMP WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(updateQuery);
			pstmt.setInt(1, unprocessedReimbursement.getResolver());
			
			/*
			 * if (unprocessedReimbursement.getStatus().equals("APPROVED"))
			 * pstmt.setObject(2, Status.APPROVED); if
			 * (unprocessedReimbursement.getStatus().equals("DENIED")) pstmt.setObject(2,
			 * Status.DENIED);
			 */
			pstmt.setString(2, unprocessedReimbursement.getStatus().toString()); //
				pstmt.setInt(3, unprocessedReimbursement.getId());
			pstmt.execute();

			ResultSet rs = conn.createStatement()
					.executeQuery("select * from reimbursment where id=" + unprocessedReimbursement.getId());
			if (rs.next()) {
				reimburse.setId(rs.getInt("id"));
				reimburse.setAmount(rs.getDouble("amount"));
				reimburse.setAuthor(rs.getInt("author"));
				reimburse.setResolver(rs.getInt("resolver"));

				reimburse.setDescription(rs.getString("description"));
				reimburse.setCreationDate(rs.getDate("creation_date"));
				reimburse.setResolutionDate(rs.getDate("resolution_date"));
				

			//	String status = rs.getString("status");
			//	String pending = "pending";
				if (rs.getString("status").equalsIgnoreCase("pending"))
					reimburse.setStatus(Status.PENDING);
				if (rs.getString("status").equalsIgnoreCase("approved"))
					reimburse.setStatus(Status.APPROVED);
				if (rs.getString("status").equalsIgnoreCase("denied"))
					reimburse.setStatus(Status.DENIED);
		
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return reimburse;
	}

	public List<Reimbursement> getReimbursementByAuthor(int id) {
		List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();

		try {
			Connection conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from reimbursement where author=" + id);
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			while (rs.next()) {
				for (int i=1;i<=rsmd.getColumnCount();i++) {
					System.out.println(rsmd.getColumnName(i)+" : "+ rs.getString(i));
				}
				Reimbursement reimburse = new Reimbursement();
				reimburse.setId(rs.getInt("id"));
				reimburse.setAmount(rs.getDouble("amount"));
				reimburse.setAuthor(rs.getInt("author"));
				reimburse.setResolver(rs.getInt("resolver"));

		//		String status = rs.getString("status");
		//		String pending = "pending";
				if (rs.getString("status").equalsIgnoreCase("pending")) {
					reimburse.setStatus(Status.PENDING);
				} else if (rs.getString("status").equalsIgnoreCase("denied")) {
					reimburse.setStatus(Status.DENIED);
				} else if (rs.getString("status").equalsIgnoreCase("approved")) {
					reimburse.setStatus(Status.APPROVED);
				}
				
				reimburse.setCreationDate(rs.getDate("creation_date"));
				reimburse.setResolutionDate(rs.getDate("resolution_date"));
				reimburse.setReceipt((InputStream) rs.getObject("receipt_image"));
				reimbursementList.add(reimburse);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return reimbursementList;
	}

	/**
	 * @param reimbursement
	 * @return
	 */
	public Reimbursement create(Reimbursement reimbursement) {
		//User user = null;
	// 	UserService userServ = new UserService();
		
	//	user = userServ.getByUserId(reimbursement.getAuthor()).get();
	//	System.out.println("User ID in Reimbursement Servlet is id=" + user.getId());
		try {
			Connection conn = ConnectionFactory.getConnection();
			String insertQuery = "insert into reimbursement (amount,author,description,status) values (?,?,?,'pending')";
			PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDouble(1, reimbursement.getAmount());
			pstmt.setInt(2, reimbursement.getAuthor());
			pstmt.setString(3, reimbursement.getDescription());
			pstmt.setString(4, reimbursement.getStatus().toString());
		//	pstmt.setBlob(4, reimbursement.getReceipt());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				reimbursement.setId(rs.getInt(1));
				/*
				 * Reimbursement reimburse = new Reimbursement();
				 * reimburse.setId(rs.getInt("id"));
				 * reimburse.setAmount(rs.getDouble("amount"));
				 * reimburse.setAuthor(rs.getInt("author"));
				 * reimburse.setResolver(rs.getInt("resolver")); if
				 * (rs.getString("status").equalsIgnoreCase("pending")) {
				 * reimburse.setStatus(Status.PENDING); } else if
				 * (rs.getString("status").equalsIgnoreCase("denied")) {
				 * reimburse.setStatus(Status.DENIED); } else if
				 * (rs.getString("status").equalsIgnoreCase("approved")) {
				 * reimburse.setStatus(Status.APPROVED); }
				 * reimburse.setCreationDate(rs.getDate("creation_date"));
				 * reimburse.setResolutionDate(rs.getDate("resolution_date"));
				 * reimburse.setReceipt((InputStream) rs.getBlob("receipt_image"));
				 */
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return reimbursement;
	}
}