import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class CustomerDBDAO implements CustomerDAO {
	Connection con;
	Customer customer;
	CouponDBDAO couponDBDAO = new CouponDBDAO();

	@Override
	public void insertCustomer(Customer customer) throws Exception {

		boolean customerExist = false;
		Set<Customer> allCustomer = new HashSet<Customer>();
		allCustomer = getAllCustomer();
		for (Customer c : allCustomer) {
			if (c.getCustName().equals(customer.getCustName())) {
				customerExist = true;
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Customer name already exist!");
				break;

			}
		}
		if (!customerExist) {

			this.con = ConnectionPool.getInstance().getConnection();
			String sql = "INSERT INTO Customer (custName,password)  VALUES(?,?)";

			try (PreparedStatement pstmt = this.con.prepareStatement(sql)) {

				pstmt.setString(1, customer.getCustName());
				pstmt.setString(2, customer.getPassword());

				pstmt.executeUpdate();
				System.out.println("Customer created" + customer.toString());
			} catch (SQLException e) {
				throw new Exception("Customer creation failed");
			} finally {
				ConnectionPool.getInstance().returnConnection(this.con);
			}
		}
	}

	@Override
	public void removeCustomer(Customer customer) throws Exception {
		this.con = ConnectionPool.getInstance().getConnection();

		CouponDBDAO couponDBDAO = new CouponDBDAO();

		Set<Coupon> custCoupons = new HashSet<Coupon>();
		custCoupons = getCustCoupons(customer);

		for (Coupon c : custCoupons) {

			long id = c.getId();
			System.out.println(c.getId());
			this.con = DriverManager.getConnection(Database.getDBUrl());

			String sql = "DELETE FROM CUSTOMER_COUPON WHERE COUPON_ID=?";
			System.out.println("//////////////////////**test1");
			PreparedStatement pstm = this.con.prepareStatement(sql);
			pstm.setLong(1, id);
			pstm.executeUpdate();
			pstm.close();

		}
		String pre1 = "DELETE FROM Customer WHERE id=?";

		try (PreparedStatement pstm1 = this.con.prepareStatement(pre1);) {
			this.con.setAutoCommit(false);
			pstm1.setLong(1, customer.getId());
			pstm1.executeUpdate();
			this.con.commit();
		} catch (SQLException e) {
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove customer");
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws Exception {
		this.con = ConnectionPool.getInstance().getConnection();
		String sql = "UPDATE Customer SET password=?";

		try {
			PreparedStatement pstmt = this.con.prepareStatement(sql);
			pstmt.setString(1, customer.getPassword());
			pstmt.executeUpdate(sql);
			pstmt.close();

		} catch (SQLException e) {

			throw new Exception("update Customer failed");
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);

		}
	}

	@Override
	public Customer getCustomer(long id) throws Exception {
		this.con = ConnectionPool.getInstance().getConnection();
		Customer customer = new Customer();
		try (Statement stm = this.con.createStatement()) {
			String sql = "SELECT * FROM Customer WHERE ID=" + id;
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			customer.setId(rs.getLong(1));
			customer.setCustName(rs.getString(2));
			customer.setPassword(rs.getString(3));

		} catch (SQLException e) {
			throw new Exception("unable to get customer data");
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}
		return customer;
	}

	@Override
	public Set<Customer> getAllCustomer() throws Exception {
		this.con = ConnectionPool.getInstance().getConnection();
		Set<Customer> set = new HashSet<>();
		String sql = "SELECT * FROM Customer";
		try (Statement stm = this.con.createStatement()) {
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong(1));
				customer.setCustName(rs.getString(2));
				customer.setPassword(rs.getString(3));

				set.add(customer);
			}

		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Customer data");
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}
		return set;
	}

	@Override
	public boolean login(String custName, String password) throws Exception {
		boolean loginStatus = false;
		this.con = ConnectionPool.getInstance().getConnection();

		try {
			String sql = "SELECT * FROM Customer WHERE custName=? AND password=?";
			PreparedStatement pstmt = this.con.prepareStatement(sql);
			pstmt.setString(1, custName);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				loginStatus = true;
			}
		} catch (Exception e) {

			System.out.println(e);
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}
		return loginStatus;
	}

	@Override
	public Set<Coupon> getCustCoupons(Customer customer) throws Exception {
		Set<Coupon> set = new HashSet<Coupon>();
		this.con = ConnectionPool.getInstance().getConnection();
		System.out.println("getCustCoupons test 1");
		try {
			String sql = "SELECT * FROM Coupon as c " + "JOIN Customer_Coupon cc " + "ON c.ID = cc.COUPON_ID "
					+ "WHERE cc.CUST_ID = ?";

			PreparedStatement pstmt = this.con.prepareStatement(sql);
			pstmt.setLong(1, customer.getId());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				Coupon coupon = new Coupon();

				coupon.setId(rs.getLong("ID"));
				coupon.setTitle(rs.getString("Title"));
				coupon.setStartDate(rs.getDate("StartDate"));
				coupon.setEndDate(rs.getDate("endDate"));
				coupon.setAmount(rs.getInt("Amount"));
				coupon.setMessege(rs.getString("Messege"));
				coupon.setCouponType(CouponType.valueOf(rs.getString("CouponType")));
				coupon.setPrice(rs.getDouble("Price"));
				coupon.setImage(rs.getString("Image"));

				set.add(coupon);

			}

			pstmt.close();
		} catch (SQLException e) {
			throw new Exception("unable to get CustCoupons data");
		}
		return set;

	}

	@Override
	public void associateCouponToCustomer(Customer customer, Coupon coupon) throws Exception {
		boolean purchasedAlready = false;
		Set<Coupon> allCoupon = new HashSet<Coupon>();
		allCoupon = getCustCoupons(customer);
		for (Coupon c : allCoupon) {
			if (c.getTitle().equals(coupon.getTitle())) {
				purchasedAlready = true;
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Company name already exist!");
				break;

			}
		}
		if (!purchasedAlready) {
			this.con = ConnectionPool.getInstance().getConnection();
			try {
				String sql = "INSERT INTO Customer_Coupon (CUST_ID, COUPON_ID) VALUES (?,?)";
				PreparedStatement pstmt = this.con.prepareStatement(sql);
				pstmt.setLong(1, customer.getId());
				pstmt.setLong(2, coupon.getId());
				pstmt.executeUpdate();

				pstmt.close();
				coupon.setAmount(coupon.getAmount() + 100);
				this.couponDBDAO.updateCoupon(coupon);

			} catch (SQLException e) {

				System.out.println("Unable to drop tables");
			} finally {

				ConnectionPool.getInstance().returnConnection(this.con);

			}
		}

	}
}