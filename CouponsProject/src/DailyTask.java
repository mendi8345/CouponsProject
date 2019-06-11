import java.util.HashSet;
import java.util.Set;

public class DailyTask implements Runnable {
	Company company;
	CompanyFacade companyFacade = new CompanyFacade(this.company);
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	Coupon coupon = new Coupon();

	@Override
	public void run() {
		System.out.println("jkfhljjsdahjfjsaljfsgdfjdsjfjjsfjsfhjdshjfhjshfjjfs");

		try {

			Set<Coupon> allCoupons = new HashSet<Coupon>();
			allCoupons = this.couponDBDAO.getAllCoupons();
			while (allCoupons != null) {
				System.out.println("run");
				for (Coupon c : allCoupons) {
					System.out.println("inside FOR");
					if (c.getEndDate().equals(DateUtils.GetCurrentDate())) {
						System.out.println("inside IF");
						this.couponDBDAO.removeCoupon(c);
						this.companyFacade.createCoupon(c);
					}
				}
			}
			Thread.sleep(10000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startThread() throws Exception {
		Thread thread = new Thread();

		thread.start();
		thread.run();

	}

}