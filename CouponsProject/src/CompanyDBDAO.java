
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
	Object set;
	private Set<Company> companys;

	@Override
	public void insertCompany(Company company) throws Exception {
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
			this.con.close();
		}

	}

	@Override
	public void removeCompany(Company company) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		String pre1 = "DELETE FROM Company WHERE id=?";

		try (PreparedStatement pstm1 = this.con.prepareStatement(pre1);) {
			this.con.setAutoCommit(false);
			pstm1.setLong(1, company.getId());
			pstm1.executeUpdate();
			this.con.commit();

		} catch (SQLException e) {
			try {
				this.con.rollback();
				System.out.println("removeCompany succeeded");
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove company");
		} finally {
			this.con.close();
		}
	}

	@Override
	public void updateCompany(Company company, long id, String compName, String password, String email)
			throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		// company.setId(id);
		// company.setCompName(compName);
		// company.setPassword(password);
		// company.setEmail(email);

		try (Statement stm = this.con.createStatement()) {
			String sql = "UPDATE Company " + " SET compName='" + company.getCompName() + "', password='"
					+ company.getPassword() + "', email='" + company.getEmail() + "' WHERE ID=" + company.getId();

			stm.executeUpdate(sql);
			System.out.println(company.toString());
		} catch (SQLException e) {
			throw new Exception("update Company failed");
		}

	}

	@Override
	public Company getCompany(long id) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		Company company = new Company();
		try (Statement stm = this.con.createStatement()) {
			String sql = "SELECT * FROM Company WHERE ID=" + id;

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
			this.con.close();
		}

		return company;
	}

	@Override
	public Set<Company> getAllCompany() throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		Set<Company> set = new HashSet<>();
		String sql = "SELECT * FROM Company";

		try (Statement stm = this.con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
			while (rs.next()) {
				long id = rs.getLong(1);
				String compName = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);

				set.add(new Company(id, compName, password, email));
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Company data");
		} finally {
			this.con.close();
		}
		return set;
	}

	@Override
	public Set<Coupon> getAllCoupons(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(String compName, String password) throws Exception {
		boolean loginStatus = false;

		try {
			String query = "SELECT * FROM Companies WHERE COMP_NAME=? AND PASSWORD=?";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setString(1, compName);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				loginStatus = true;
			}
		} catch (Exception e) {

			System.out.println(e);
		} finally {
			this.con.close();
		}
		return loginStatus;
	}

}
