package DAO;

import java.util.Set;

import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;

public interface CouponDAO {

	void insertCoupon(Company company, Coupon coupon) throws Exception;

	void removeCoupon(Coupon coupon) throws Exception;

	void updateCoupon(Coupon coupon) throws Exception;

	Coupon getCoupon(long id) throws Exception;

	Set<Coupon> getAllCoupons() throws Exception;

	Set<Coupon> getCouponsByType(CouponType couponType) throws Exception;

}
