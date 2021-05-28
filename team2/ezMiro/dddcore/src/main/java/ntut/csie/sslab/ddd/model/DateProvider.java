package ntut.csie.sslab.ddd.model;

import java.time.Instant;

public class DateProvider {
	private static Instant date = null;
	
	public static Instant now() {
		if(date == null) {
			return Instant.now();
		}
		return date;
	}
	
	public static void setDate(Instant now) {
		date = now;
	}
	
	public static void resetDate() {
		date = null;
	}
}
