package JavaBeans;
import java.util.HashSet;
import java.util.Set;

public class Customer {

	private long id;
	private String custName;
	private String password;
	private Set<Coupon> coupons = new HashSet<Coupon>();

	/**
	 * Full CTOR
	 */
	public Customer(long id, String custName, String password) {
		this.id = id;
		this.custName = custName;
		this.password = password;
	}

	/**
	 * Empty CTOR
	 */
	public Customer() {
	}

	/**
	 * getters & setters
	 */
	public long getId() {
		return this.id;
	}

	public String getCustName() {
		return this.custName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [id=" + this.id + ", custName=" + this.custName + ", password=" + this.password + "]";
	}

	public Set<Coupon> getCoupons() {
		return this.coupons;
	}

	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

}
