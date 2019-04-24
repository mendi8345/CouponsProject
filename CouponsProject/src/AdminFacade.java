import java.util.Set;

public class AdminFacade implements CouponClientFacade {

	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();

	private Coupon coupon;
	private Company company;/// yuyuytytyr
	private Customer customer;

	// public AdminFacade(Coupon coupon) {
	// this.coupon = coupon;
	// }
	private String name = "admin";
	private String password = "1234";

	public AdminFacade() {

	}

	public void insertCompany(Company company) throws Exception {
		this.companyDBDAO.insertCompany(company);
	}

	public void removeCompany(Company company) throws Exception {
		this.companyDBDAO.removeCompany(company);
	}

	public void updateCompany(Company company, long id, String compName, String password, String email)
			throws Exception {
		company.setId(id);
		company.setCompName(compName);
		company.setPassword(password);
		company.setEmail(email);

		this.companyDBDAO.updateCompany(company, id, compName, password, email);
	}

	public Company getCompany(long id) {
		return this.company;
	}

	public Set<Company> getAllCompany() throws Exception {
		// CompanyDBDAO companyDAO=new CompanyDBDAO();
		return this.companyDBDAO.getAllCompany();
	}

	public void insertCustomer(Customer customer) throws Exception {
		this.customerDBDAO.insertCustomer(customer);
	}

	public void removeCustomer(Customer customer) throws Exception {
		this.customerDBDAO.removeCustomer(customer);
	}

	public void updateCustomer(Customer customer, long id, String custName, String password) throws Exception {
		customer.setCustName(custName);
		customer.setPassword(password);
		this.customerDBDAO.updateCustomer(customer, id, custName, password);

	}

	public Customer getCustomer(long id) {
		return this.customer;
	}

	public Set<Customer> getAllCustomer() throws Exception {
		// ProductDBDAO comDAO=new ProductDBDAO();
		return this.customerDBDAO.getAllCustomer();
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {

		if (clientType == ClientType.admin) {
			if ((name == "admin") && (password == "1234")) {
				AdminFacade adminFacade = new AdminFacade();

				return adminFacade;
			}
		}
		return null;

	}
}