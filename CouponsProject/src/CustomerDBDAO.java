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

	@Override
	public void insertCustomer(Customer customer) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		String sql = "INSERT INTO Customer (custName,password)  VALUES(?,?)";

		try (PreparedStatement pstmt = this.con.prepareStatement(sql)) {

			pstmt.setString(1, customer.getCustName());
			pstmt.setString(2, customer.getPassword());

			pstmt.executeUpdate();
			System.out.println("Customer created" + customer.toString());
		} catch (SQLException e) {
			throw new Exception("Customer creation failed");
		} finally {
			this.con.close();
		}

	}

	@Override
	public void removeCustomer(Customer customer) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
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
			this.con.close();
		}
	}

	@Override
	public void updateCustomer(Customer customer, long id, String custName, String password) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());

		try (Statement stm = this.con.createStatement()) {
			String sql = "UPDATE Customer " + " SET name='" + customer.getCustName() + "', password='"
					+ customer.getPassword();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new Exception("update Customer failed");
		}

	}

	@Override
	public Customer getCustomer(long id) throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
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
			this.con.close();
		}
		return customer;
	}

	@Override
	public Set<Customer> getAllCustomer() throws Exception {
		this.con = DriverManager.getConnection(Database.getDBUrl());
		Set<Customer> set = new HashSet<>();
		String sql = "SELECT id FROM Customer";
		try (Statement stm = this.con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
			while (rs.next()) {
				int id = rs.getInt(1);
				String custName = rs.getString(1);
				String password = rs.getString(1);

				set.add(new Customer(id, custName, password));
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Customer data");
		} finally {
			this.con.close();
		}
		return set;
	}

	@Override
	public Set<Coupon> getAllCoupons(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}