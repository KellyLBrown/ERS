package com.revature.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Reimbursement;
import com.revature.beans.Reimbursement.ReimbursementType;
import com.revature.beans.Reimbursement.StatusType;
import com.revature.utils.ConnectionUtil;

public class ReimbursementDao {
	
	ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();
	Connection conn = connUtil.getConnection();
	
	// add a Reimbursement ticket to the database
	public Reimbursement addTicket(Reimbursement r){
//		String type = null;
//		if(r.getType() == ReimbursementType.FOOD) {
//			type = "FOOD";
//		} else if(r.getType() == ReimbursementType.TRAVEL) {
//			type = "TRAVEL";
//		} else if(r.getType() == ReimbursementType.LODGING) {
//			type = "LODGING";
//		} else if(r.getType() == ReimbursementType.OTHER) {
//			type = "OTHER";
//		}
		r.setId(this.getAllTickets().size() + 1);
		r.setStatus(StatusType.PENDING);
		try {
			String sql = "INSERT into reimbursement VALUES(" + r.getId() + ", " + r.getAmount() + ", " +
					"default" + ", '" + r.getDescription() + "', " + r.getAuthorId() + ", " + "NULL" + ");";
			Statement s = conn.createStatement();
			s.executeUpdate(sql);
			addRStatus(r);
			addRType(r);
			return r;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	// grabs the ticket and updates it, with resolver, and from pending to approved or denied
	public Reimbursement updateTicket(Reimbursement r) {
		try {
			String sql = "UPDATE reimbursement set r_resolver = " + r.getResolverId() + " WHERE r_id = " + r.getId() + ";";
			Statement s = conn.createStatement();
			s.executeUpdate(sql);
			updateRStatus(r);
			return r;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	


	// grabs a specific ticket by r_id
	public Reimbursement getTicket(int rId) {
		try {
			String sql = "SELECT * FROM reimbursement WHERE r_id = " + rId + ";";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if(rs != null) {
				rs.next();
				Reimbursement r = new Reimbursement();
				r.setId(rs.getInt(1));
				r.setAmount(rs.getDouble(2));
				r.setSubmitted(rs.getTimestamp(3).toLocalDateTime());
				r.setDescription(rs.getString(4));
				r.setAuthorId(rs.getInt(5));
				r.setResolverId(rs.getInt(6));
				r.setStatus(getStatus(r.getId()));
				r.setType(getType(r.getId()));
				
				return r;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}


	// grabs all tickets from a specific user by author_id
	public List<Reimbursement> getTicketsByAuthor(int uId){
		try {
			String sql = "SELECT * FROM reimbursement WHERE r_author = " + uId + ";";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			List<Reimbursement> rList = new ArrayList<Reimbursement>();
			
			if(rs != null) {
				while(rs.next()) {
				Reimbursement r = new Reimbursement();
				r.setId(rs.getInt(1));
				r.setAmount(rs.getDouble(2));
				r.setSubmitted(rs.getTimestamp(3).toLocalDateTime());
				r.setDescription(rs.getString(4));
				r.setAuthorId(rs.getInt(5));
				r.setResolverId(rs.getInt(6));
				r.setStatus(getStatus(r.getId()));
				r.setType(getType(r.getId()));
				rList.add(r);
				}
				
				return rList;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	// grabs all tickets from a specific user by resolver_id
	public List<Reimbursement> getTicketsByResolver(int uId){
		try {
			String sql = "SELECT * FROM reimbursement WHERE r_resolver = " + uId + ";";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			List<Reimbursement> rList = new ArrayList<Reimbursement>();
			
			if(rs != null) {
				while(rs.next()) {
				Reimbursement r = new Reimbursement();
				r.setId(rs.getInt(1));
				r.setAmount(rs.getDouble(2));
				r.setSubmitted(rs.getTimestamp(3).toLocalDateTime());
				r.setDescription(rs.getString(4));
				r.setAuthorId(rs.getInt(5));
				r.setResolverId(rs.getInt(6));
				r.setStatus(getStatus(r.getId()));
				r.setType(getType(r.getId()));
				rList.add(r);
				}
				
				return rList;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
			
	}
	
	// grab a list of all tickets currently in the system
	public List<Reimbursement> getAllTickets(){
		try {
			String sql = "SELECT * FROM reimbursement ORDER BY r_id;";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			List<Reimbursement> rList = new ArrayList<Reimbursement>();
			
			if(rs != null) {
				while(rs.next()) {
				Reimbursement r = new Reimbursement();
				r.setId(rs.getInt(1));
				r.setAmount(rs.getDouble(2));
				r.setSubmitted(rs.getTimestamp(3).toLocalDateTime());
				r.setDescription(rs.getString(4));
				r.setAuthorId(rs.getInt(5));
				r.setResolverId(rs.getInt(6));
				r.setStatus(getStatus(r.getId()));
				r.setType(getType(r.getId()));
				rList.add(r);
				}
				return rList;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// grab a list of only pending tickets
	public List<Reimbursement> getPending(){
		try {
			String sql = "select * from reimbursement r join reimbursement_status s on (r.r_id = s.r_id ) where s.r_status = 'PENDING' ORDER BY r.r_id;";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			List<Reimbursement> rList = new ArrayList<Reimbursement>();
			
			if(rs != null) {
				while(rs.next()) {
				Reimbursement r = new Reimbursement();
				r.setId(rs.getInt(1));
				r.setAmount(rs.getDouble(2));
				r.setSubmitted(rs.getTimestamp(3).toLocalDateTime());
				r.setDescription(rs.getString(4));
				r.setAuthorId(rs.getInt(5));
				r.setResolverId(rs.getInt(6));
				r.setStatus(getStatus(r.getId()));
				r.setType(getType(r.getId()));
				rList.add(r);
				}
				
				return rList;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	// grab a list of only pending tickets
		public List<Reimbursement> getApproved(){
			try {
				String sql = "select * from reimbursement r join reimbursement_status s on (r.r_id = s.r_id ) where s.r_status = 'APPROVED' ORDER BY r.r_id;";
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				List<Reimbursement> rList = new ArrayList<Reimbursement>();
				
				if(rs != null) {
					while(rs.next()) {
					Reimbursement r = new Reimbursement();
					r.setId(rs.getInt(1));
					r.setAmount(rs.getDouble(2));
					r.setSubmitted(rs.getTimestamp(3).toLocalDateTime());
					r.setDescription(rs.getString(4));
					r.setAuthorId(rs.getInt(5));
					r.setResolverId(rs.getInt(6));
					r.setStatus(getStatus(r.getId()));
					r.setType(getType(r.getId()));
					rList.add(r);
					}
					
					return rList;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		// grab a list of only pending tickets
		public List<Reimbursement> getDenied(){
			try {
				String sql = "select * from reimbursement r join reimbursement_status s on (r.r_id = s.r_id ) where s.r_status = 'DENIED' ORDER BY r.r_id;";
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				List<Reimbursement> rList = new ArrayList<Reimbursement>();
				
				if(rs != null) {
					while(rs.next()) {
					Reimbursement r = new Reimbursement();
					r.setId(rs.getInt(1));
					r.setAmount(rs.getDouble(2));
					r.setSubmitted(rs.getTimestamp(3).toLocalDateTime());
					r.setDescription(rs.getString(4));
					r.setAuthorId(rs.getInt(5));
					r.setResolverId(rs.getInt(6));
					r.setStatus(getStatus(r.getId()));
					r.setType(getType(r.getId()));
					rList.add(r);
					}
					
					return rList;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		
	
	
	// ---------------------- Status Section ---------------------------
	
	private void addRStatus(Reimbursement r) {
		// TODO Auto-generated method stub
		try {
			String sql = "INSERT into reimbursement_status VALUES(" + r.getId() + ", 'PENDING');";
			Statement s = conn.createStatement();
			s.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private StatusType getStatus(int id) {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT * FROM reimbursement_status WHERE r_id = " + id + ";";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if(rs != null) {
				rs.next();
				String type = rs.getString(2);
					if(type.equals("PENDING")) {
						return StatusType.PENDING;
					} else if(type.equals("APPROVED")) {
						return StatusType.APPROVED;
					} else if(type.equals("DENIED")) {
						return StatusType.DENIED;
					}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	private void updateRStatus(Reimbursement r) {
		// TODO Auto-generated method stub
		try {
			String sql = "UPDATE reimbursement_status set r_status = '" + r.getStatus() + "' WHERE r_id = " + r.getId() + ";";
			Statement s = conn.createStatement();
			s.executeUpdate(sql);
					} catch (SQLException e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
	}	
	
	
	
	//----------------------- Type Section ------------------------------
	
	private void addRType(Reimbursement r) {
		// TODO Auto-generated method stub
		try {
			String sql = "INSERT into reimbursement_type VALUES(" + r.getId() + ", '" + r.getType() + "');";
			Statement s = conn.createStatement();
			s.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private ReimbursementType getType(int id) {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT * FROM reimbursement_TYPE WHERE r_id = " + id + ";";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if(rs != null) {
				rs.next();
				String type = rs.getString(2);
					if(type.equals("FOOD")) {
						return ReimbursementType.FOOD;
					} else if(type.equals("TRAVEL")) {
						return ReimbursementType.TRAVEL;
					} else if(type.equals("LODGING")) {
						return ReimbursementType.LODGING;
					} else if(type.equals("OTHER")) {
						return ReimbursementType.OTHER;
					}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	

}
