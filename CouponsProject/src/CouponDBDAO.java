import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class CouponDBDAO implements CouponDAO {

	Connection con;
	Company company;

	@Override

	public void insertCoupon(Company company, Coupon coupon) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		String sql = "INSERT INTO Coupon (title,startDate,endDate,amount,messege,couponType,price,image)  VALUES(?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement pstmt = this.con.prepareStatement(sql);

			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2, coupon.getStartDate());
			pstmt.setDate(3, coupon.getEndDate());
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getMessege());
			pstmt.setString(6, coupon.getCouponType().name());
			pstmt.setDouble(7, coupon.getPrice());

			pstmt.setString(8, coupon.getImage());

			pstmt.executeUpdate();
			pstmt.close();

			long id = 0;
			sql = "SELECT ID FROM Coupon WHERE TITLE=?";
			pstmt = this.con.prepareStatement(sql);
			pstmt.setString(1, coupon.getTitle());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				id = rs.getLong("ID");
			}
			pstmt.close();

			System.out.println("Coupon created  " + coupon.toString());
			sql = "INSERT INTO Company_Coupon (COMP_ID,COUPON_ID) VALUES(?, ?)";
			pstmt = this.con.prepareStatement(sql);
			pstmt.setLong(1, company.getId());
			pstmt.setLong(2, id);
			System.out.println("11111111111");

			pstmt.executeUpdate();
			System.out.println("hsfhsghfghsgdfds");

			pstmt.close();

		} catch (SQLException e) {
			throw new Exception("Coupons insert failed");
		} finally {
			this.con.close();
		}
	}

	@Override
	public void removeCoupon(Coupon coupon) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		String pre1 = "DELETE FROM Coupon WHERE id=?";

		try (PreparedStatement pstm1 = this.con.prepareStatement(pre1);) {
			this.con.setAutoCommit(false);
			pstm1.setLong(1, coupon.getId());
			pstm1.executeUpdate();
			this.con.commit();

		} catch (SQLException e) {
			try {
				this.con.rollback();
				System.out.println("removes coupon succeeded");
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove coupon");
		} finally {
			this.con.close();
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());

		try {
			String sql = "UPDATE Coupon SET endDate=?, price=?, amount=?, messege=?, image=? WHERE ID=?";
			System.out.println("updateCoupon test 1");

			PreparedStatement pstmt = this.con.prepareStatement(sql);
			System.out.println("updateCoupon test 1");

			pstmt.setDate(1, coupon.getEndDate());
			pstmt.setDouble(2, coupon.getPrice());
			pstmt.setInt(3, coupon.getAmount());
			pstmt.setString(4, coupon.getMessege());
			pstmt.setString(5, coupon.getImage());
			pstmt.setLong(6, coupon.getId());

			pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {

			throw new Exception(e);
		} finally {
			this.con.close();

		}
	}

	@Override
	public Coupon getCoupon(long id) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		Coupon coupon = new Coupon();

		try (Statement stm = this.con.createStatement()) {
			String sql = "SELECT * FROM Coupon WHERE ID=" + id;
			System.out.println("getCoupon test 1");

			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			coupon.setId(rs.getLong(1));
			System.out.println("getCoupon test 2");

			coupon.setTitle(rs.getString(2));
			System.out.println("getCoupon test 4");

			coupon.setStartDate(rs.getDate(3));
			coupon.setEndDate(rs.getDate(4));
			coupon.setAmount(rs.getInt(5));
			coupon.setMessege(rs.getString(6));
			coupon.setCouponType(CouponType.valueOf(rs.getString("CouponType")));
			coupon.setPrice(rs.getDouble(8));
			coupon.setImage(rs.getString(9));

		} catch (SQLException e) {
			throw new Exception("unable to get coupon data");

		} finally {
			this.con.close();
		}

		return coupon;
	}

	@Override
	public Set<Coupon> getAllCoupons() throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
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
			throw new Exception("cannot get Coupon data");
		} finally {
			this.con.close();
		}
		return set;
	}

	@Override
	public Set<Coupon> getCouponsByType(CouponType couponType) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());

		try {

			String query = "SELECT * FROM Coupons WHERE TYPE=?";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setString(1, couponType.toString());
			ResultSet rs = pstmt.executeQuery();
			Coupon coupon = null;
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
			throw new Exception(e);
		} finally {
			this.con.close();
		}
		return getCouponsByType(couponType);
	}

}