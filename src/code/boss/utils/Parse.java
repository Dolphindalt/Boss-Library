package boss.utils;

public class Parse {

	public static Integer parseInteger(String s) {
		try {
		return Integer.parseInt(s);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Double parseDouble(String s) {
		try {
		return Double.parseDouble(s);
		} catch (Exception e) {
			return null;
		}
	}
	
}
