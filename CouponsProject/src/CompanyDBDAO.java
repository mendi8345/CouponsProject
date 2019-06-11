
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class CompanyDBDAO implements CompanyDAO {
	Connection con;

	@Override
	public void insertCompany(Company company) throws Exception {

		boolean companyExist = false;
		Set<Company> allCompanies = new HashSet<Company>();
		allCompanies = getAllCompany();
		for (Company c : allCompanies) {
			if (c.getCompName().equals(company.getCompName())) {
				companyExist = true;
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Company name already exist!");
				break;

			}
		}
		if (!companyExist) {

			this.con = DriverManager.getConnection(Database.getDBUrl());
			String sql = "INSERT INTO Company (compName,password,email)  VALUES(?,?,?)";

			try (PreparedStatement pstmt = this.con.prepareStatement(sql)) {

				pstmt.setString(1, company.getCompName());
				pstmt.setString(2, company.getPassword());
				pstmt.setString(3, company.getEmail());

				pstmt.executeUpdate();
				System.out.println("Company created" + company.toString());
			} catch (SQLException e) {
				throw new Exception("Company creation failed");
			} finally {
				ConnectionPool.getInstance().returnConnection(this.con);
			}
		}
	}

	@Override
	public void removeCompany(Company company) throws Exception {

		CouponDBDAO couponDBDAO = new CouponDBDAO();

		Set<Coupon> compCoupons = new HashSet<Coupon>();
		compCoupons = getCompCoupons(company);

		for (Coupon c : compCoupons) {
			System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh" + c.toString());
			couponDBDAO.removeCoupon(c);

		}
		this.con = ConnectionPool.getInstance().getConnection();
		String pre1 = "DELETE FROM Company WHERE id = ?";
		try (PreparedStatement pstm = this.con.prepareStatement(pre1);) {
			pstm.setLong(1, company.getId());
			System.out.println("removeCompany test  1");

			pstm.executeUpdate();
			pstm.close();
			System.out.println("removeCompany succeedes");

			this.con.commit();

		} catch (SQLException e) {
			this.con.commit();

			try {
				this.con.rollback();
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}

		} finally {

			ConnectionPool.getInstance().returnConnection(this.con);
		}
	}

	@Override
	public void updateCompany(Company company) throws Exception {
		this.con = ConnectionPool.getInstance().getConnection();

		try {
			String sql = "UPDATE Company " + " SET password='" + company.getPassword() + "', email='"
					+ company.getEmail() + "' WHERE ID=" + company.getId();
			PreparedStatement pstmt = this.con.prepareStatement(sql);
			pstmt.setString(1, company.getPassword());
			pstmt.setString(2, company.getEmail());

			pstmt.executeUpdate(sql);
			System.out.println(company.toString());
		} catch (SQLException e) {
			throw new Exception("update Company failed");
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}

	}

	@Override
	public Company getCompany(long id) throws Exception {
		this.con = ConnectionPool.getInstance().getConnection();
		Company company = new Company();
		try (Statement stm = this.con.createStatement()) {
			String sql = "SELECT * FROM Company WHERE ID=?";

			ResultSet rs = stm.executeQuery(sql);
			rs.next();

			company.setId(rs.getLong(1));

			company.setCompName(rs.getString(2));

			company.setPassword(rs.getString(3));
			company.setEmail(rs.getString(4));

		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("unable to get company data");
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}

		return company;
	}

	@Override
	public Set<Company> getAllCompany() throws Exception {
		this.con = ConnectionPool.getInstance().getConnection();
		Set<Company> set = new HashSet<Company>();

		try {
			Statement stm = this.con.createStatement();
			String sql = "SELECT * FROM Company";
			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				Company company = new Company();

				company.setId(rs.getLong(1));
				company.setCompName(rs.getString(2));
				company.setPassword(rs.getString(3));
				company.setEmail(rs.getString(4));
				// System.out.println("this is" + company.toString());
				set.add(company);

			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get company data");
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}
		return set;
	}

	@Override
	public Set<Coupon> getCompCoupons(Company company) throws Exception {
		Set<Coupon> coupons = new HashSet<Coupon>();

		try {
			this.con = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM Coupon as c " + "JOIN Company_Coupon cc " + "ON c.ID = cc.COUPON_ID "
					+ "WHERE cc.Comp_ID = ?";
			PreparedStatement pstmt = this.con.prepareStatement(sql);
			pstmt.setLong(1, company.getId());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Coupon coupon = new Coupon();

				coupon.setId(rs.getLong("id"));
				coupon.setTitle(rs.getString("title"));
				coupon.setStartDate(rs.getDate("startDate"));
				coupon.setEndDate(rs.getDate("endDate"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setMessege(rs.getString("Messege"));
				coupon.setCouponType(CouponType.valueOf(rs.getString("CouponType")));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
			}
			pstmt.close();
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);

		}
		return coupons;
	}

	@Override
	public boolean login(String compName, String password) throws Exception {
		boolean loginStatus = false;

		try {
			this.con = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM Company WHERE compName=? and password=?";
			PreparedStatement pstmt = this.con.prepareStatement(sql);
			pstmt.setString(1, compName);
			pstmt.setString(2, password);
			System.out.println("JKHJKHJGHJGHMFFGFDDFFDDFFHJLKLKLKL;LK;LKLKLK" + password);

			ResultSet rs = pstmt.executeQuery();
			System.out.println("9999999999999" + rs.getString(1));
			if (rs.next()) {
				loginStatus = true;
			}
		} catch (Exception e) {

			System.out.println(e);
		} finally {

		}
		return loginStatus;
	}

}
