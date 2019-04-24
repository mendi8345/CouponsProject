import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class CouponDBDAO implements CouponDAO {
	Connection con;

	@Override
	public void insertCoupon(Coupon coupon) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		String sql = "INSERT INTO Coupon (title,startDate,endDate,amount,messege,couponType,price,image)  VALUES(?,?,?,?,?,?,?,?)";

		try (PreparedStatement pstmt = this.con.prepareStatement(sql)) {

			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2, coupon.getStartDate());
			pstmt.setDate(3, coupon.getEndDate());
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getMessege());
			pstmt.setString(6, coupon.getCouponType().name());
			pstmt.setDouble(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());
			pstmt.executeUpdate();

			System.out.println("Coupon created" + coupon.toString());
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
	public void updateCoupon(Coupon coupon, long id, String title, Date startDate, Date endDate, int amount,
			String messege, CouponType couponType, double price, String image) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		coupon.setId(id);
		coupon.setTitle(title);
		coupon.setStartDate(startDate);
		coupon.setEndDate(endDate);
		coupon.setAmount(amount);
		coupon.setMessege(messege);
		coupon.setCouponType(couponType);
		coupon.setPrice(price);
		coupon.setImage(image);

		try (Statement stm = this.con.createStatement()) {
			String sql = "UPDATE Coupon " + " SET title='" + coupon.getTitle() + "',  startDate='"
					+ coupon.getStartDate() + "', endDate='" + coupon.getEndDate() + "', amount='" + coupon.getAmount()
					+ "', messege='" + coupon.getMessege() + "', couponType='" + coupon.getCouponType() + "', price='"
					+ coupon.getPrice() + "', image='" + coupon.getImage() + "' WHERE ID=" + coupon.getId();

			stm.executeUpdate(sql);
			System.out.println(coupon.toString());
		} catch (SQLException e) {
			throw new Exception("update Coupon failed");
		}

	}

	@Override
	public Coupon getCoupon(long id) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		Coupon coupon = new Coupon();
		try (Statement stm = this.con.createStatement()) {
			String sql = "SELECT id FROM Coupon WHERE ID=" + id;
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
			throw new Exception("unable to get coupon data");
		} finally {
			this.con.close();
		}
		return coupon;
	}

	@Override
	public Set<Coupon> getAllCoupons() throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		Set<Coupon> set = new HashSet<>();

		try {
			Statement stm = this.con.createStatement();
			String sql = "SELECT * FROM Coupon";
			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				long id = rs.getLong(1);
				String title = rs.getString(2);
				Date startDate = rs.getDate(3);
				Date endDate = rs.getDate(4);
				int amount = rs.getInt(5);
				String messege = rs.getString(6);
				CouponType couponType = CouponType.valueOf(rs.getString("CouponType"));
				double price = rs.getDouble(8);
				String image = rs.getString(9);

				set.add(new Coupon(id, title, startDate, endDate, amount, messege, couponType, price, image));
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Coupon data");
		} finally {
			this.con.close();
		}
		return set;
	}

}