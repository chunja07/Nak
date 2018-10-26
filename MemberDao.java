package dao;

import java.sql.*;
import java.util.*;

import util.*;
import model.*;
import java.util.Date;;

public class MemberDao {

	public Member getModelObject(ResultSet rs) throws SQLException {

		Member member = new Member();

		member.setId(rs.getString(1));
		member.setName(rs.getString(2));
		member.setPassword(rs.getString(3));
		member.setEmail(rs.getString(4));
		member.setPhone(rs.getString(5));
		member.setAddress(rs.getString(6));
		member.setRegdate(rs.getDate(7));
		
		return member;
	}

	public int getRecordCount(Connection conn) {
		int result = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "count (*) from member";

		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			rs.next();
			result = rs.getInt(1);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		jdbcUtil.close(pstmt);
		jdbcUtil.close(rs);

		return result;
	}

	public ArrayList<Member> select(Connection conn) {

		ArrayList<Member> list = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "select * from member";
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			list = new ArrayList<>();

			while (rs.next()) {
				list.add(getModelObject(rs));

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		jdbcUtil.close(pstmt);
		jdbcUtil.close(rs);

		return list;
	}

	public Member select(Connection conn, String id) {

		Member member = new Member();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "select * from member where id = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
						
			if (rs.next()) {
				member = getModelObject(rs);
			}
			
			System.out.println("目池记?");
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
		jdbcUtil.close(rs);
		jdbcUtil.close(pstmt);
		
		System.out.println("目池记2?");
		
		return member;
			
	}

	public int insert(Connection conn, Member member) {

		int result = 0;

		PreparedStatement pstmt = null;

		String query = "insert into member values (?,?,?,?,?,?,?)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPassword());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPhone());
			pstmt.setString(6, member.getAddress());
			pstmt.setTimestamp(7, getCurrentTimeStamp());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		jdbcUtil.close(pstmt);

		return result;

	}
	
	public int delete (Connection conn, Member member) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String query = "delete from member where id = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getId());
			
			result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			jdbcUtil.close(pstmt);
			
			return result;
	}
	
	public int update (Connection conn, Member member, String newPassword) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String query = "update member set password = ? where id = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, member.getId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		jdbcUtil.close(pstmt);
		
		return result;
		
	}
	
public int update (Connection conn, Member member, String newName, String newEmail, String newPhone, String newAddress) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String query = "update member set name = ?, email = ?, phone = ?, address = ? where id = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newName);
			pstmt.setString(2, newEmail);
			pstmt.setString(3, newPhone);
			pstmt.setString(4, newAddress);
			pstmt.setString(5, member.getId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		jdbcUtil.close(pstmt);
		
		return result;
		
	}
	
	
	private static Timestamp getCurrentTimeStamp() {
		Date today = new Date();
		return new Timestamp(today.getTime());
	}
	
}
