package dao;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import util.*;
import model.*;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.protocol.Resultset;;

public class SerachDAO {
	
	public Motorcycle getModelObject(ResultSet rs) throws SQLException {

		Motorcycle motorcycle = new Motorcycle();

		motorcycle.setMotorcycle_Pn(rs.getString(1));
		motorcycle.setMotorcycle_Name(rs.getString(2));
		motorcycle.setMotorcycle_Brand(rs.getString(3));
		motorcycle.setMotorcycle_Price(rs.getInt(4));
		motorcycle.setMotorcycle_Year(rs.getInt(5));
		motorcycle.setMotorcycle_Condition(rs.getString(6));

		return motorcycle;
	}
	
	public ArrayList <Motorcycle> searchBrand (Connection conn, String search){
		
		ArrayList <Motorcycle> list= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "select * from motorcycle_product where motorcycle_brand like '?%'";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, search);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<>();
			
			while(rs.next()) {
				list.add(getModelObject(rs));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			list = null;
		}
		
			jdbcUtil.close(pstmt);
			jdbcUtil.close(rs);
		
			return list;
	}
	
	public ArrayList<Motorcycle> serachBrand(Connection conn, int page, int count, String search) {

		String query = "select * from motorcycle_product where motorcycle_brand like ? order by motorcycle_pn desc limit ?,?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Motorcycle> list = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, search);
			pstmt.setInt(2, (page - 1) * count);
			pstmt.setInt(3, count);

			rs = pstmt.executeQuery();

			list = new ArrayList<>();

			while (rs.next()) {
				list.add(getModelObject(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			list = null;
		}

		jdbcUtil.close(pstmt);
		jdbcUtil.close(rs);

		return list;

	}
	
	public int getSearchRecordCount(Connection conn, String search) {
		int count = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "select count(*) from motorcycle_product where motorcycle_brand like ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, search);
			
			rs = pstmt.executeQuery();

			if (rs.next())
				count = rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
			count = 0;
		}
		jdbcUtil.close(pstmt);
		jdbcUtil.close(rs);

		return count;
	}
	
}
