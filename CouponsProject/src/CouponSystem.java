
public class CouponSystem {
	private ConnectionPool connectionPool;
	private CompanyDAO companyDAO;
	private CouponDAO couponDAO;
	private CustomerDAO customerDAO;

	private static DailyTask dt = new DailyTask();

	private static CouponSystem instance = new CouponSystem();

	public static CouponSystem getInstance() {
		return instance;
	}

	public CouponSystem() {
		try {
			this.connectionPool = ConnectionPool.getInstance();

			dt.startThread();
		} catch (Exception e) {

		}
	}

	public CompanyDAO getCompanyDAO() {
		return this.companyDAO;
	}

	public CouponDAO getCouponDAO() {
		return this.couponDAO;
	}

	public CustomerDAO getCustomerDAO() {
		return this.customerDAO;
	}

	public CouponClientFacade login(String name, String password, ClientType clientType) throws Exception {
		if (clientType == ClientType.admin) {
			if ((name == "admin") && (password == "1234")) {
				AdminFacade adminFacade = new AdminFacade();

				return adminFacade;
			}
		}

		if (clientType == ClientType.company) {

			CompanyDBDAO companyDBDAO = new CompanyDBDAO();

			boolean loginStatus = companyDBDAO.login(name, password);
			System.out.println("companyDBDAO.login(name, password)" + companyDBDAO.login(name, password));

			if (loginStatus) {
				Company company = new Company();
				// company.setCompName(name);
				// company.setPassword(password);
				CompanyFacade companyFacade = new CompanyFacade(company);
				// companyFacade.setCompanyDAO(this.companyDAO);
				// companyFacade.setCouponDAO(this.couponDAO);

				return companyFacade;

			}
		}

		if (clientType == ClientType.customer) {

			CustomerDBDAO customerDBDAO = new CustomerDBDAO();
			boolean loginStatus = customerDBDAO.login(name, password);
			System.out.println("customerDBDAO.login(name, password)" + customerDBDAO.login(name, password));
			if (loginStatus) {

				Customer customer = new Customer();
				CustomerFacade customerFacade = new CustomerFacade(customer);

				return customerFacade;

			}
		}

		return null;

	}
}