
public class Company_Coupon {
	private long comp_id;
	private long coupon_id;

	public Company_Coupon() {
	}

	public Company_Coupon(long comp_id, long coupon_id) {
		this.comp_id = comp_id;
		this.coupon_id = coupon_id;
	}

	public long getComp_id() {
		return this.comp_id;
	}

	public long getCoupon_id() {
		return this.coupon_id;
	}

	public void setComp_id(long comp_id) {
		this.comp_id = comp_id;
	}

	public void setCoupon_id(long coupon_id) {
		this.coupon_id = coupon_id;
	}

}
