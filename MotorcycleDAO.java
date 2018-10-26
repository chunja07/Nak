package dao;

import java.sql.*;
import java.util.*;

import util.*;
import model.*;
import java.util.Date;

import com.mysql.cj.protocol.Resultset;;

public class MotorcycleDAO {

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

	public int getRecordCount(Connection conn) {
		int count = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "select count(*) from motorcycle_product";

		try {
			pstmt = conn.prepareStatement(query);
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

	public int getRecordCountBrand(Connection conn, String brand) {
		int count = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "select count(*) from motorcycle_product where motorcycle_brand = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, brand);

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
	
	public int getRecordCountOtherBrand(Connection conn, String honda, String ktm) {
		int count = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "select count(*) from motorcycle_product where motorcycle_brand not in (?,?)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, honda);
			pstmt.setString(2, ktm);

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

	public ArrayList<Motorcycle> select(Connection conn) {

		ArrayList<Motorcycle> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "select * from motorcycle_product";

		try {
			pstmt = conn.prepareStatement(query);
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

	public ArrayList<Motorcycle> select(Connection conn, int page, int count) {

		String query = "select * from motorcycle_product order by motorcycle_pn desc limit ?,?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Motorcycle> list = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (page - 1) * count);
			pstmt.setInt(2, count);

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

	public ArrayList<Motorcycle> BrandSelect(Connection conn, int page, int count, String brand) {

		String query = "select * from motorcycle_product where motorcycle_brand = ? order by motorcycle_pn desc limit ?,?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Motorcycle> list = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, brand);
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

	public ArrayList<Motorcycle> OtherBrandSelect(Connection conn, int page, int count, String honda, String ktm) {

		String query = "select * from motorcycle_product where motorcycle_brand not in (?,?) order by motorcycle_pn desc limit ?,?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Motorcycle> list = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, honda);
			pstmt.setString(2, ktm);
			pstmt.setInt(3, (page - 1) * count);
			pstmt.setInt(4, count);

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

	public Motorcycle motorcycle(Connection conn, String motorcycle_pn) {

		String query = "select * from motorcycle_product where motorcycle_pn = ?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Motorcycle obj = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, motorcycle_pn);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				obj = getModelObject(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			obj = null;
		}
		;

		return obj;
	}

}
