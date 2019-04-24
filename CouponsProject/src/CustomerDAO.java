import java.util.Set;

public interface CustomerDAO {

	void insertCustomer(Customer customer) throws Exception;

	void removeCustomer(Customer customer) throws Exception;

	void updateCustomer(Customer customer, long id, String custName, String password) throws Exception;

	Customer getCustomer(long id) throws Exception;

	Set<Customer> getAllCustomer() throws Exception;

	Set<Coupon> getAllCoupons(long id);

}