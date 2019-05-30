
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private static Database instance = new Database();

	public static Database getInstance() {
		return instance;
	}

	public static String getDriverData() {
		return "org.apache.derby.jdbc.ClientDriver";
	}

	public static String getDBUrl() {
		return "jdbc:derby://localhost:3301/JDBD;create=true";
	}

	//
	public static void dropTables(Connection con) throws SQLException {
		Statement stmt = con.createStatement();

		String sqlCoupon;
		String sqlCompany;
		String sqlCustomer;
		String sqlCustomer_Coupon;
		String sqlCompany_Coupon;

		try {
			sqlCoupon = "drop table app.Coupon";
			sqlCompany = "drop table app.Company";
			sqlCustomer = "drop table app.Customer";
			sqlCustomer_Coupon = "drop table app.Customer_Coupon";
			sqlCompany_Coupon = "drop table app.Company_Coupon";
			System.out.println("DROP TABL TEST 1");

			stmt.executeUpdate(sqlCoupon);
			System.out.println("DROP TABL TEST 1");
			System.out.println("Success:" + sqlCoupon);
			stmt.executeUpdate(sqlCompany);
			System.out.println("Success:" + sqlCompany);
			stmt.executeUpdate(sqlCustomer);
			System.out.println("Success:" + sqlCustomer);
			stmt.executeUpdate(sqlCustomer_Coupon);
			System.out.println("Success:" + sqlCustomer_Coupon);
			stmt.executeUpdate(sqlCustomer_Coupon);
			System.out.println("Success:" + sqlCustomer_Coupon);

		} catch (Exception e) {
			System.out.println("Unable to drop tables");
		}
	}

	public static void createTables(Connection con) throws SQLException {
		Statement stmt1 = con.createStatement();

		String sqlCoupon;
		String sqlCompany;
		String sqlCustomer;
		String sqlCustomer_Coupon;
		String sqlCompany_Coupon;

		try {

			sqlCoupon = "create table Coupon("
					+ "id integer not null primary key generated always as identity(start with 1,increment by 1), "
					+ "title varchar(50) not null, " + "startDate date not null," + "endDate date not null,"
					+ "amount integer not null," + "messege varchar(50) not null," + "CouponType varchar(50)not null,"
					+ "price double not null," + "image varchar(50) not null)";

			stmt1.executeUpdate(sqlCoupon);
			System.out.println("success:" + sqlCoupon);
		} catch (Exception e) {
			System.out.println("exist");
		}

		try {
			Statement stmt2 = con.createStatement();
			sqlCompany = "create table Company ("
					+ "id integer not null primary key generated always as identity(start with 1, increment by 1), "
					+ "compName varchar(50) not null, " + "password varchar(50) not null,"
					+ "email varchar(50) not null)";
			stmt2.executeUpdate(sqlCompany);
			System.out.println("success:" + sqlCompany);

		} catch (Exception e) {
			System.out.println("exist");
		}

		try {
			Statement stmt3 = con.createStatement();

			sqlCustomer = "create table Customer ("
					+ "id integer not null primary key generated always as identity(start with 1, increment by 1), "
					+ "custName varchar(50) not null, " + "password varchar(50) not null)";

			stmt3.executeUpdate(sqlCustomer);
			System.out.println("success:" + sqlCustomer);
		} catch (Exception e) {
			System.out.println("exist");
		}

		try {
			Statement stmt4 = con.createStatement();

			sqlCompany_Coupon = "create table Company_Coupon(" + "comp_id integer not null references Company(id), "
					+ "coupon_id integer not null references Coupon(id))";
			stmt4.executeUpdate(sqlCompany_Coupon);

			System.out.println("success:" + sqlCompany_Coupon);
		} catch (Exception e) {
			System.out.println("exist");
		}

		try {
			Statement stmt5 = con.createStatement();

			sqlCustomer_Coupon = "create table Customer_Coupon(" + "cust_id integer not null references Customer(id),"
					+ "coupon_id integer not null references Coupon(id))";

			stmt5.executeUpdate(sqlCustomer_Coupon);

			System.out.println("success:" + sqlCustomer_Coupon);
		} catch (Exception e) {
			System.out.println("exist");
		}

	}

}