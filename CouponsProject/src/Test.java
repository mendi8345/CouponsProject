import java.sql.Connection;
import java.sql.DriverManager;

public class Test {

	public static void main(String[] args) throws Exception {

		// Create a connection to the database:
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection con = DriverManager.getConnection(Database.getDBUrl());
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Database.createTables(con);
		// Database.dropTables(con);

		Company company1 = new Company(1, " market", "4211", "superpharm@java.com");
		Company company2 = new Company(2, "dominos", "2345", "dominos@java.com");
		Company company3 = new Company(3, "rami levi", "6789", "ramilevi" + "@java.com");

		Coupon coupon1 = new Coupon(1, "gd", DateUtils.GetCurrentDate(), DateUtils.GetEndDate(), 2, "electric",
				CouponType.food, 5.0, "gggg");
		Coupon coupon2 = new Coupon(431, "pizza", DateUtils.GetCurrentDate(), DateUtils.GetEndDate(), 2, "food",
				CouponType.electric, 8.0, "@gmail");

		Customer customer1 = new Customer(1, "tom", "1234");
		Customer customer2 = new Customer(2, "mendy", "3456");
		Customer customer3 = new Customer(3, "kobi", "6789");

		AdminFacade adminFacade = new AdminFacade();
		System.out.println("----------------------------------- table company -----------------------------------");
		adminFacade.createCompany(company1);
		adminFacade.createCompany(company2);
		adminFacade.createCompany(company3);

		// adminFacade.getAllCompany();
		System.out.println("companyDBDAO.getCompCoupons" + companyDBDAO.getCompCoupons(company2));
		// adminFacade.removeCompany(company2); עובד
		// adminFacade.updateCompany(company1);עובד
		// System.out.println(adminFacade.getAllCompany());
		System.out.println();
		System.out.println("----------------------------------- table customer -----------------------------------");

		adminFacade.insertCustomer(customer1);
		adminFacade.insertCustomer(customer2);
		adminFacade.insertCustomer(customer3);

		// adminFacade.removeCustomer(customer1);עובד

		CompanyFacade companyFacade = new CompanyFacade(company3);
		System.out.println();
		System.out.println("----------------------------------- table coupon -----------------------------------");

		companyFacade.createCoupon(coupon1);
		companyFacade.createCoupon(coupon2);

		// couponDBDAO.removeCoupon(coupon);
		// System.out.println("get 1 company============= " +
		// CouponDBDAO.getAllCoupons());
		System.out.println("***");

		CustomerFacade customerFacade = new CustomerFacade(customer3);

		customerFacade.purchaseCoupon(coupon1);
		customerFacade.getAllPurchasedCoupon();
		// System.out.println(customerFacade.getAllCustomer());

	}

}