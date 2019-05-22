import java.util.EmptyStackException;
import java.util.Set;

public class CustomerFacade implements CouponClientFacade {

	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();

	private Customer customer;
	private Coupon coupon;

	public CustomerFacade(Customer customer) {
		this.customer = customer;
	}

	public void purchaseCoupon(Coupon coupon) throws Exception {

		Coupon couponData = this.couponDAO.getCoupon(coupon.getId());

		if (couponData == null) {
			throw new EmptyStackException();
		}
		if (couponData.getAmount() <= 0) {

			throw new EmptyStackException();
		}
		// and not purchased already
		if (getAllPurchasedCoupon().contains(couponData)) {
			throw new EmptyStackException();
		}
		// purchase
		this.customerDAO.associateCouponToCustomer(couponData, this.customer);

		// decrease amount
		couponData.setAmount(couponData.getAmount() - 1);
		this.couponDAO.updateCoupon(couponData);

	}

	public Set<Coupon> getAllPurchasedCoupon() throws Exception {
		return this.customerDAO.getCustCoupons(this.customer);
	}

	public void getAllPurchasedCouponByType(CouponType couponType) throws Exception {

	}

	public Set<Coupon> getAllPurchasedCouponByPrice(double price) throws Exception {
		Set<Coupon> allCoupons = this.customerDAO.getCustCoupons(this.customer);

		for (Coupon c : allCoupons) {
			if (c.getPrice() > price) {
				allCoupons.remove(c);
			}
		}
		return allCoupons;
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		// TODO Auto-generated method stub
		return null;
	}

}