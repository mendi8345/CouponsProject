import java.sql.Connection;
import java.sql.DriverManager;

public class Test {

	public static void main(String[] args) throws Exception {

		// Create a connection to the database:
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection con = DriverManager.getConnection(Database.getDBUrl());
		// Database.dropTables(con);

		Database.createTables(con);
		CouponSystem couponSystem = CouponSystem.getInstance();

		Company company1 = new Company(1, " market", "4211", "superpharm@java.com");
		Company company2 = new Company(2, "dominos", "2345", "dominos@java.com");
		Company company3 = new Company(3, "rami levi", "6789", "ramilevi" + "@java.com");

		Coupon coupon1 = new Coupon(1, "gd", DateUtils.GetCurrentDate(), DateUtils.GetEndDate(), 600, "electric",
				CouponType.food, 5.0, "gggg");
		Coupon coupon2 = new Coupon(2, "pizza", DateUtils.GetCurrentDate(), DateUtils.GetEndDate(), 700, "food",
				CouponType.electric, 8.0, "@gmail");

		Customer customer1 = new Customer(1, "tom", "1234");
		Customer customer2 = new Customer(2, "mendy", "3456");
		Customer customer3 = new Customer(3, "kobi", "6789");

		AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234", ClientType.admin);

		// CompanyFacade companyFacade1 = (CompanyFacade) couponSystem.login("market",
		// "4211", ClientType.company);
		// CompanyFacade companyFacade2 = (CompanyFacade) couponSystem.login("dominos",
		// "2345", ClientType.company);
		//
		CustomerFacade customerFacade1 = (CustomerFacade) couponSystem.login("tom", "1234", ClientType.customer);
		CustomerFacade customerFacade2 = (CustomerFacade) couponSystem.login("mendy", "3456", ClientType.customer);
		CompanyFacade companyFacade = new CompanyFacade(company1);
		System.out.println("----------------------------------- table company -----------------------------------");
		adminFacade.createCompany(company1);
		adminFacade.createCompany(company2);
		adminFacade.createCompany(company3);

		// adminFacade.getAllCompany();
		// adminFacade.updateCompany(company1);עובד
		// System.out.println(adminFacade.getAllCompany());
		System.out.println();
		System.out.println("----------------------------------- table customer -----------------------------------");

		adminFacade.insertCustomer(customer1);
		adminFacade.insertCustomer(customer2);
		adminFacade.insertCustomer(customer3);

		System.out.println();
		System.out.println("----------------------------------- table coupon -----------------------------------");

		companyFacade.createCoupon(coupon1);
		companyFacade.createCoupon(coupon2);

		// couponDBDAO.removeCoupon(coupon);
		// System.out.println("get 1 company============= " +
		// CouponDBDAO.getAllCoupons());
		System.out.println("***");

		customerFacade1.purchaseCoupon(coupon1);
		customerFacade1.purchaseCoupon(coupon2);
		// DailyTask dailyTask = new DailyTask();
		// dailyTask.startThread();
		// adminFacade.removeCustomer(customer3);
		// adminFacade.removeCompany(company1);
		// adminFacade.removeCompany(company2);
		// adminFacade.removeCompany(company3);

		// new Thread(new DailyTask()).start();
		System.out.println(customerFacade1.getAllPurchasedCouponByPrice(9));
		// System.out.println(customerFacade.getAllCustomer());

	}

}