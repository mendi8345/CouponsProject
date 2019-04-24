
import java.sql.Date;
import java.util.Set;

public interface CouponDAO {
	
	  void insertCoupon(Coupon coupon) throws Exception;
	  

	  void removeCoupon(Coupon coupon) throws Exception;
	  

	  void updateCoupon(Coupon coupon, long id, String title, Date startDate, Date endDate, int amount, String messege,
			CouponType couponType, double price, String image) throws Exception;
	  

	Coupon getCoupon(long id) throws Exception;
	

	Set<Coupon> getAllCoupons() throws Exception;
	

}
