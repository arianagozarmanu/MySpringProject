package util;

import java.sql.Timestamp;

public final class serviceUtils {

	public static java.util.Date getCurrentDate() {
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
}
