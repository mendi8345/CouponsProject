import java.util.Set;

public class AdminFacade implements CouponClientFacade {

	private CouponDAO couponDAO = new CouponDBDAO();
	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CompanyDAO companyDAO = new CompanyDBDAO();

	private Coupon coupon;
	private Company company;
	private Customer customer;

	// public AdminFacade(Coupon coupon) {
	// this.coupon = coupon;
	// }
	private String name = "admin";
	private String password = "1234";

	public AdminFacade() {

	}

	public void createCompany(Company company) throws Exception {
		this.companyDAO.insertCompany(company);
	}

	public void removeCompany(Company company) throws Exception {
		this.companyDAO.removeCompany(company);
	}

	public void updateCompany(Company company) throws Exception {

		this.companyDAO.updateCompany(company);
	}

	public Company getCompany(long id) {
		return this.company;
	}

	public Set<Company> getAllCompany() throws Exception {
		// CompanyDBDAO companyDAO=new CompanyDBDAO();
		return this.companyDAO.getAllCompany();
	}

	public void insertCustomer(Customer customer) throws Exception {
		this.customerDAO.insertCustomer(customer);
	}

	public void removeCustomer(Customer customer) throws Exception {
		this.customerDAO.removeCustomer(customer);
	}

	public void updateCustomer(Customer customer) throws Exception {
		this.customerDAO.updateCustomer(customer);

	}

	public Customer getCustomer(long id) {
		return this.customer;
	}

	public Set<Customer> getAllCustomer() throws Exception {
		// ProductDBDAO comDAO=new ProductDBDAO();
		return this.customerDAO.getAllCustomer();
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {

		return null;

	}
}