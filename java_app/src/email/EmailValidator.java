package email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	/**
	 * Validate hex with regular expression
	 * 
	 * @param hex
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public boolean validate(final String hex) {

		matcher = pattern.matcher(hex);
		return matcher.matches();

	}

	public static void main(String[] args) {
		EmailValidator emailV = new EmailValidator();
		String hei = "kensivalie@gmail.com";
		String halla = "hahahahha";
		boolean valid = emailV.validate(hei);
		boolean valid2 = emailV.validate(halla);

		System.out.println("Email is valid " + hei + valid);

		System.out.println("Email is valid " + halla + valid2);

	}
}