package DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import DAO.CompanyDAO;
import DB.ConnectionPool;
import Exceptions.CantConnectToDbException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;

/**
 * @Author - Mendy&Tom
 *
 * @Description: In this class we need to implement all the method in CompanyDao
 *               every method getting connection to database and return to
 *               connection queue when the method finished
 *
 */

public class CompanyDBDAO implements CompanyDAO {
	ConnectionPool connectionPool;
	Connection con;
	Company company;

	public CompanyDBDAO() {
	}

	@Override
	public void insertCompany(Company company) throws Exception {

		boolean companyExist = false;
		Set<Company> allCompanies = new HashSet<Company>();
		allCompanies = getAllCompany();
		for (Company c : allCompanies) {
			if (c.getCompName().equals(company.getCompName())) {
				companyExist = true;
				System.out.println("|----------Company name already exist----------|");
				break;

			}
		}
		if (!companyExist) {

			try {
				this.con = ConnectionPool.getInstance().getConnection();
			} catch (Exception e1) {
				throw new CantConnectToDbException();
			}
			String sql = "INSERT INTO Company (compName,password,email)  VALUES(?,?,?)";

			try (PreparedStatement pstmt = this.con.prepareStatement(sql)) {

				pstmt.setString(1, company.getCompName());
				pstmt.setString(2, company.getPassword());
				pstmt.setString(3, company.getEmail());

				pstmt.executeUpdate();
				System.out.println(" created " + company.toString());
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
			couponDBDAO.removeCoupon(c);

		}
		try {
			this.con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e1) {
			throw new CantConnectToDbException();
		}
		String pre1 = "DELETE FROM Company WHERE id = ?";
		try (PreparedStatement pstm = this.con.prepareStatement(pre1);) {
			pstm.setLong(1, company.getId());

			pstm.executeUpdate();
			pstm.close();
			System.out.println("removeCompany succeedes");

			this.con.commit();

		} catch (SQLException e) {
			throw new Exception("removeCompany faild");
		} finally {

			ConnectionPool.getInstance().returnConnection(this.con);
		}
	}

	@Override
	public void updateCompany(Company company) throws Exception {
		try {
			this.con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e1) {
			throw new CantConnectToDbException();
		}
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
		try {
			this.con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e1) {
			throw new CantConnectToDbException();
		}
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
		try {
			this.con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e1) {
			throw new CantConnectToDbException();
		}
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
			throw new Exception("cannot get companies data");
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}
		return set;
	}

	@Override
	public Set<Coupon> getCompCoupons(Company company) throws Exception {
		Set<Coupon> coupons = new HashSet<Coupon>();

		try {
			try {
				this.con = ConnectionPool.getInstance().getConnection();
			} catch (Exception e1) {
				throw new CantConnectToDbException();
			}
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
			throw new Exception("cannot get companiys coupons data");
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
		} catch (Exception e1) {
			throw new CantConnectToDbException();
		}
		try {
			String sql = "SELECT * FROM COMPANY WHERE compName=? AND password=?";
			PreparedStatement pstmt = this.con.prepareStatement(sql);
			pstmt.setString(1, compName);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				loginStatus = true;
			}
		} catch (SQLException e) {

		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);

		}
		return loginStatus;
	}

	@Override
	public long GetCompIdByName(String compName) throws Exception {
		long compId = 0;

		try {
			this.con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e1) {
			throw new CantConnectToDbException();
		}

		try {
			String sql3 = "SELECT ID FROM company WHERE CompName=?";
			PreparedStatement pstmt3 = this.con.prepareStatement(sql3);

			pstmt3.setString(1, compName);
			ResultSet rs1 = pstmt3.executeQuery();
			rs1.toString();

			if (rs1.next()) {
				compId = rs1.getLong("ID");

			}
		} catch (SQLException e) {
		} finally {
			ConnectionPool.getInstance().returnConnection(this.con);
		}

		return compId;

	}

}