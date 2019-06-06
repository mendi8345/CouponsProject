import java.time.LocalDate;

public class DateUtils {

	public static java.sql.Date GetCurrentDate() {
		LocalDate startDate = LocalDate.now();
		java.sql.Date date = java.sql.Date.valueOf(startDate);
		return date;
	}

	public static java.sql.Date GetEndDate() {
		LocalDate endDate = LocalDate.now();
		// endDate = endDate.plusDays(1);
		java.sql.Date date = java.sql.Date.valueOf(endDate);
		return date;

	}

	public static String getDBUrl() {
		// TODO Auto-generated method stub
		return "jdbc:derby://localhost:3301/JDBD;create=true";
	}

}
