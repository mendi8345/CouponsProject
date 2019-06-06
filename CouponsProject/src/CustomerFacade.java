import java.util.Set;

public class CustomerFacade implements CouponClientFacade {

	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();

	private Customer customer;

	public CustomerFacade(Customer customer) {
		this.customer = customer;
	}

	public void purchaseCoupon(Coupon coupon) throws Exception {

		Coupon couponData = this.couponDAO.getCoupon(coupon.getId());
		System.out.println("couponData print" + couponData.toString());

		if (couponData == null) {
			throw new Exception("Coupon does not exist");
		}
		if (couponData.getAmount() < 0) {
			System.out.println(
					"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4couponData.getAmount" + couponData.getAmount());
			throw new Exception("Coupon does not available ");
		}
		// and not purchased already
		// if (getAllPurchasedCoupon().contains(couponData())) {
		// throw new Exception("Coupon already exist");
		// }
		// purchase
		this.customerDAO.associateCouponToCustomer(this.customer, couponData);

		// decrease amount

	}

	public Set<Coupon> getAllPurchasedCoupon() throws Exception {
		return this.customerDAO.getCustCoupons(this.customer);
	}

	public Set<Coupon> getAllPurchasedCouponByType(CouponType couponType) throws Exception {
		Set<Coupon> allCoupons = this.customerDAO.getCustCoupons(this.customer);
		for (Coupon c : allCoupons) {
			if (c.getCouponType() != couponType) {
				allCoupons.remove(c);
			}
		}
		return allCoupons;
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