package info.subsonic.picketlink.constant;

/**
 * This class is constants.
 * 
 * @author HOSHI Seigo
 */
public final class Constants {

	/**
	 * The private constructor.
	 */
	private Constants() {
	}

	/**
	 * The min length of loginName.
	 */
	public static final int LOGIN_NAME_MIN_LENGTH = 4;

	/**
	 * The max length of loginName.
	 */
	public static final int LOGIN_NAME_MAX_LENGTH = 255;

	/**
	 * The regexp of loginName.
	 */
	public static final String LOGIN_NAME_REGEXP = "[a-zA-Z0-9_\\-]+";

	/**
	 * The max length of name.
	 */
	public static final int NAME_MAX_LENGTH = 255;

	/**
	 * The regexp of password.
	 */
	public static final String PASSWORD_REGEXP = "[a-zA-Z0-9_!\"#$%\\&'\\(\\)\\-=\\^~\\\\\\|@`\\[\\{;\\+:\\*\\]\\}\\,<\\.>/\\?]+";

	/**
	 * The min length of password.
	 */
	public static final int PASSWORD_MIN_LENGTH = 6;

	/**
	 * The max length of password.
	 */
	public static final int PASSWORD_MAX_LENGTH = 64;

}
