package Exceptions;

import JavaBeans.Company;
import JavaBeans.Coupon;

public class CoponNotAvilable extends Exception {
	private Coupon coupon;

	public CoponNotAvilable(Coupon coupon) {
		this.coupon = coupon;
	}

	public String getMessage() {
		return "Coupon " + coupon.getTitle() + " not avilable !";
	}
}