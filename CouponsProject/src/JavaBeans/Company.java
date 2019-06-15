package JavaBeans;
import java.util.HashSet;
import java.util.Set;

public class Company {

	/**
	 * Data Members
	 */
	private long id;
	private String compName;
	private String password;
	private String email;
	private Set<Coupon> set = new HashSet<Coupon>();

	/**
	 * Full CTOR
	 */
	public Company(long id, String compName, String password, String email) {
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}

	/**
	 * empty CTOR
	 */
	public Company() {
	}

	/**
	 * getters & setters
	 */
	public long getId() {
		return this.id;
	}

	public String getCompName() {
		return this.compName;
	}

	public String getPassword() {
		return this.password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Company [id=" + this.id + ", compName=" + this.compName + ", password=" + this.password + ", email="
				+ this.email + "]";
	}

	public Set<Coupon> getSet() {
		return this.set;
	}

	public void setSet(Set<Coupon> set) {
		this.set = set;
	}

}
