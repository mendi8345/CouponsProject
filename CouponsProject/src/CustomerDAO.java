import java.util.Set;

public interface CustomerDAO {

	void insertCustomer(Customer customer) throws Exception;

	void removeCustomer(Customer customer) throws Exception;

	void updateCustomer(Customer customer, long id, String custName, String password) throws Exception;

	Customer getCustomer(long id) throws Exception;

	Set<Customer> getAllCustomer() throws Exception;

	Set<Coupon> getCustCoupons(Customer customer) throws Exception;

	boolean login(String custName, String password) throws Exception;

	void associateCouponToCustomer(Coupon coupon, Customer customer) throws Exception;

}