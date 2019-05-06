
import java.sql.Date;
import java.util.Set;

public class CompanyFacade implements CouponClientFacade {

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
		return this.companyDAO.getAllCoupons(this.getCompany().getId());

	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		// TODO Auto-generated method stub
		return null;
	}
}