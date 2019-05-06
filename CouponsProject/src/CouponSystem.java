
public class CouponSystem {

	private static CouponSystem instance = new CouponSystem();

	public static CouponSystem getInstance() {
		return instance;
	}

	public CouponSystem() {

		// TODO Auto-generated constructor stub
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
			boolean loginSuccess = companyDBDAO.login(name, password);

			if (loginSuccess) {

				Company company = new Company();
				CompanyFacade companyFacade = new CompanyFacade(company);

				return companyFacade;

			}
		}
		
		if (clientType == ClientType.customer) {

			CustomerDBDAO customerDBDAO = new CustomerDBDAO();
			boolean loginSuccess = customerDBDAO.login(name, password);

			if (loginSuccess) {

				Company company = new Company();
				CompanyFacade companyFacade = new CompanyFacade(company);

				return companyFacade;

			}
		}
		
		return null;

	}
}