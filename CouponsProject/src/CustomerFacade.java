import java.sql.Date;
import java.util.Set;

public class CustomerFacade implements CouponClientFacade {

	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();

	private Customer customer;
	private Coupon coupon;

	public Customer getCustomer() {
		return this.customer;
	}

	public CustomerFacade(Customer customer) {
		this.customer = customer;
	}

	public void insertCoupon(Coupon coupon) throws Exception {
		this.couponDAO.insertCoupon(coupon);
	}

	public void removeCoupon(Coupon coupon) throws Exception {
		this.couponDAO.removeCoupon(coupon);

	}

	public void updateCoupon(Coupon coupon, long id, String title, Date startDate, Date endDate, int amount,
			String messege, CouponType couponType, double price, String image) throws Exception {
		this.couponDAO.insertCoupon(coupon);
	}

	public Coupon getCoupon(long id) throws Exception {
		return this.couponDAO.getCoupon(id);

	}

	public Set<Coupon> getAllCoupons() throws Exception {
		return this.customerDAO.getAllCoupons(this.getCustomer().getId());

	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		// TODO Auto-generated method stub
		return null;
	}
}