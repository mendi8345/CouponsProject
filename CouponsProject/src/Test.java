import java.sql.Connection;
import java.sql.DriverManager;

public class Test {

	public static void main(String[] args) throws Exception {

		// Create a connection to the database:
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection con = DriverManager.getConnection(Database.getDBUrl());

		AdminFacade adminFacade = new AdminFacade();

		Database.createTables(con);
		// Database.dropTables(con);
		System.out.println("***");
		System.out.println("----------------------------------- table company -----------------------------------");
		Company company1 = new Company(1, "super market", "4211", "superpharm@java.com");
		Company company2 = new Company(2, "dominos", "2345", "dominos@java.com");
		Company company3 = new Company(3, "rami levi", "6789", "ramilevi@java.com");

		adminFacade.insertCompany(company1);
		adminFacade.insertCompany(company2);
		adminFacade.insertCompany(company3);

		adminFacade.removeCompany(company1);
		System.out.println("getAllCompany " + adminFacade.getAllCompany());

		adminFacade.updateCompany(company1, 2, "Mendi", "Tom", "blabla@gmail.com");

		System.out.println("get 1 company " + adminFacade.getCompany(4));
		System.out.println(adminFacade.getAllCompany());

		System.out.println("***");

		System.out.println("----------------------------------- table coupon -----------------------------------");
		Coupon coupon = new Coupon(1, "gd", DateUtils.GetCurrentDate(), DateUtils.GetEndDate(), 2, "electric",
				CouponType.food, 5.0, "gggg");
		CompanyFacade companyFacade = new CompanyFacade(company1);
		System.out.println(companyFacade);
		// adminFacade.insertCoupon(coupon);
		// couponDBDAO.removeCoupon(coupon);
		// System.out.println("get 1 company============= " +
		// CouponDBDAO.getAllCoupons());
		System.out.println("***");
		System.out.println("----------------------------------- table customer -----------------------------------");

		Customer customer1 = new Customer(1, "tom", "1234");
		Customer customer2 = new Customer(2, "mendy", "3456");
		Customer customer3 = new Customer(3, "kobi", "6789");

		CustomerFacade customerFacade = new CustomerFacade(customer1);

		adminFacade.insertCustomer(customer1);
		adminFacade.insertCustomer(customer2);
		adminFacade.insertCustomer(customer3);

		adminFacade.removeCustomer(customer1);

		// System.out.println(customerFacade.getAllCustomer());

	}

}