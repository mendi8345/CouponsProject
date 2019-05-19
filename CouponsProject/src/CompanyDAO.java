
import java.util.Set;

public interface CompanyDAO {

	void insertCompany(Company company) throws Exception;

	void removeCompany(Company company) throws Exception;

	void updateCompany(Company company, long id, String compName, String password, String email) throws Exception;

	Company getCompany(long id) throws Exception;

	Set<Company> getAllCompany() throws Exception;

	Set<Coupon> getCompCoupons(long id) throws Exception;

	boolean login(String compName, String password) throws Exception;

}
