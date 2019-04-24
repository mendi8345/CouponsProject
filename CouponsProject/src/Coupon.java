import java.sql.Date;

public class Coupon {
	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private String messege;
	private CouponType couponType;
	private double price;
	private String image;

	public Coupon() {
	}

	public Coupon(long id, String title, Date startDate, Date endDate, int amount, String messege,
			CouponType couponType, double price, String image) {
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.couponType = couponType;
		this.messege = messege;
		this.price = price;
		this.image = image;
	}

	public long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public int getAmount() {
		return this.amount;
	}

	public String getMessege() {
		return this.messege;
	}

	public double getPrice() {
		return this.price;
	}

	public String getImage() {
		return this.image;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setMessege(String messege) {
		this.messege = messege;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CouponType getCouponType() {
		return this.couponType;
	}

	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + this.id + ", title=" + this.title + ", startDate=" + this.startDate + ", endDate="
				+ this.endDate + ", amount=" + this.amount + ", messege=" + this.messege + ", couponType="
				+ this.couponType + ", price=" + this.price + ", image=" + this.image + "]";
	}
}
