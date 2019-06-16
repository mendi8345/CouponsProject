package DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import DAO.CouponDAO;
import DB.ConnectionPool;
import Exceptions.CantConnectToDbException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;

public class CouponDBDAO implements CouponDAO {

	Connection con;
	Company company;

	@Override

	public void insertCoupon(Company company, Coupon coupon) throws Exception {
		boolean couponExist = false;
		Set<Coupon> allCoupon = new HashSet<Coupon>();
		allCoupon = getAllCoupons();
		for (Coupon c : allCoupon) {
			if (c.getTitle().equals(coupon.getTitle())) {
				couponExist = true;
				System.out.println("|----------coupon already exist----------|");
				break;

			}
		}
		if (!couponExist) {
			try {
				this.con = ConnectionPool.getInstance().getConnection();
			} catch (Exception e1) {
				throw new CantConnectToDbException();
			}
			String sql1 = "INSERT INTO Coupon (title,startDate,endDate,amount,messege,couponType,price,image)  VALUES(?,?,?,?,?,?,?,?)";

			try {
				PreparedStatement pstmt1 = this.con.prepareStatement(sql1);

				pstmt1.setString(1, coupon.getTitle());
				pstmt1.setDate(2, coupon.getStartDate());
				pstmt1.setDate(3, coupon.getEndDate());
				pstmt1.setInt(4, coupon.getAmount());
				pstmt1.setString(5, coupon.getMessege());
				pstmt1.setString(6, coupon.getCouponType().name());
				pstmt1.setDouble(7, coupon.getPrice());
				pstmt1.setString(8, coupon.getImage());

				pstmt1.executeUpdate();
				pstmt1.close();

				long id = 0;
				String sql2 = "SELECT ID FROM Coupon WHERE TITLE=?";
				PreparedStatement pstmt2 = this.con.prepareStatement(sql2);
				pstmt2.setString(1, coupon.getTitle());

				ResultSet rs = pstmt2.executeQuery();

				while (rs.next()) {
					id = rs.getLong("ID");
				}

				long compId = 0;
				String sql3 = "SELECT ID FROM company WHERE CompName=?";
				PreparedStatement pstmt3 = this.con.prepareStatement(sql3);
				pstmt3.setString(1, company.getCompName());

				ResultSet rs1 = pstmt3.executeQuery();
				while (rs1.next()) {
					compId = rs1.getLong("ID");

				}
				String sql4 = "INSERT INTO Company_Coupon (COMP_ID,COUPON_ID) VALUES(?, ?)";

				PreparedStatement pstmt4 = this.con.prepareStatement(sql4);
				pstmt4.setLong(1, compId);
				pstmt4.setLong(2, id);

				pstmt4.executeUpdate();

				pstmt4.close();

			} catch (SQLException e) {
				throw new Exception("Coupons insert failed");
			} finally {
				ConnectionPool.getInstance().returnConnection(this.con);
			}
		}
	}

	@Override
	public void removeCoupon(Coupon coupon) throws Exception {
		try {
			long id;
			try {
				this.con = ConnectionPool.getInstance().getConnection();
			} catch (Exception e1) {
				throw new CantConnectToDbException();
			}
			Set<Coupon> allCoupons = new HashSet<Coupon>();
			allCoupons = getAllCoupons();

			for (Coupon c : allCoupons) {
				if (c.getTitle().equals(coupon.getTitle())) {

					id = coupon.getId();

					String sql = "DELETE FROM CUSTOMER_COUPON WHERE COUPON_ID=?";
					PreparedStatement pstm = this.con.prepareStatement(sql);
					pstm.setLong(1, id);
					pstm.executeUpdate();
					pstm.close();

					break;
				}
			}
			String sql = "DELETE FROM Company_Coupon WHERE COUPON_ID=?";
			PreparedStatement pstmt = this.con.prepareStatement(sql);
			pstmt.setLong(1, coupon.getId());
			pstmt.executeUpdate();
			pstmt.close();

			String sql2 = "DELETE FROM Coupon WHERE ID=?";

			PreparedStatement pstmt2 = this.con.prepareStatement(sql2);
			pstmt2.setLong(1, coupon.getId());
			pstmt2.executeUpdate();
			pstmt2.close();

		} catch (SQLException e) {

			throw new Exception("failed to remove coupon FROM CUSTOMER_COUPON");
		} finally {

			ConnectionPool.getInstance().returnConnection(this.con);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws Exception {
		try {
			this.con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e1) {
			throw new CantConnectToDbException();
		}
		try {
			String sql = "UPDATE Coupon SET endDate=?, price=?, amount=?, messege=?, image=? WHERE ID=?";

			PreparedStatement pstmt = this.con.prepareStatement(sql);

			pstmt.setDate(1, coupon.getEndDate());
			pstmt.setDouble(2, coupon.getPrice());
			pstmt.setInt(3, coupon.getAmount());
			pstmt.setString(4, coupon.getMessege());
			pstmt.setString(5, coupon.getImage());
			pstmt.setLong(6, coupon.getId());

			pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {
			throw new Exception("failed to update coupon ");

		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);

		}
	}

	@Override
	public Coupon getCoupon(long id) throws Exception {
		try {
			this.con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e1) {
			throw new CantConnectToDbException();
		}
		Coupon coupon = new Coupon();

		try (Statement stm = this.con.createStatement()) {
			String sql = "SELECT * FROM Coupon WHERE ID=" + id;

			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			coupon.setId(rs.getLong(1));

			coupon.setTitle(rs.getString(2));
			coupon.setStartDate(rs.getDate(3));
			coupon.setEndDate(rs.getDate(4));
			coupon.setAmount(rs.getInt(5));
			coupon.setMessege(rs.getString(6));
			coupon.setCouponType(CouponType.valueOf(rs.getString("CouponType")));
			coupon.setPrice(rs.getDouble(8));
			coupon.setImage(rs.getString(9));

		} catch (SQLException e) {
			throw new Exception("cannot get Coupon data");

		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}

		return coupon;
	}

	@Override
	public Set<Coupon> getAllCoupons() throws Exception {
		try {
			this.con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e1) {
			throw new CantConnectToDbException();
		}
		Set<Coupon> set = new HashSet<Coupon>();

		try {
			Statement stm = this.con.createStatement();
			String sql = "SELECT * FROM Coupon";
			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				Coupon coupon = new Coupon();

				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStartDate(rs.getDate(3));
				coupon.setEndDate(rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setMessege(rs.getString(6));
				coupon.setCouponType(CouponType.valueOf(rs.getString("CouponType")));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));

				set.add(coupon);
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Coupons data");
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}
		return set;
	}

	@Override
	public Set<Coupon> getCouponsByType(CouponType couponType) throws Exception {
		try {
			this.con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new CantConnectToDbException();
		}
		try {

			String query = "SELECT * FROM Coupons WHERE TYPE=?";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setString(1, couponType.toString());
			ResultSet rs = pstmt.executeQuery();
			Coupon coupon = new Coupon();
			while (rs.next()) {
				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStartDate(rs.getDate(3));
				coupon.setEndDate(rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setMessege(rs.getString(6));
				coupon.setCouponType(CouponType.valueOf(rs.getString("CouponType")));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));

			}
			pstmt.close();

		} catch (SQLException e) {
			throw new Exception("Does not recognize coupons of this type");
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}
		return getCouponsByType(couponType);
	}

}