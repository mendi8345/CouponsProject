
import java.sql.Connection;
import java.util.Set;

public class CompanyFacade implements CouponClientFacade {

	Connection con;
	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();

	private Company company;
	private Coupon coupon;

	public Company getCompany() {
		return this.company;
	}

	public CompanyFacade(Company company) {
		this.company = company;
	}

	public void createCoupon(Coupon coupon) throws Exception {

		this.couponDAO.insertCoupon(this.company, coupon);
	}

	public void removeCoupon(Coupon coupon) throws Exception {
		this.couponDAO.removeCoupon(coupon);

	}

	public void updateCoupon(Coupon coupon) throws Exception {
		this.couponDAO.updateCoupon(coupon);
	}

	public Coupon getCoupon(long id) throws Exception {
		return this.couponDAO.getCoupon(id);

	}

	public Set<Coupon> getCompCoupons() throws Exception {
		return this.companyDAO.getCompCoupons(this.getCompany().getId());

	}
	//
	// public Set<Coupon> getCouponsByType(CouponType couponType) throws Exception {
	// Set<Coupon> allCoupons = this.getCompCoupons()Coupons();
	// return this.allC;
	//
	// }

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		// TODO Auto-generated method stub
		return null;
	}
}