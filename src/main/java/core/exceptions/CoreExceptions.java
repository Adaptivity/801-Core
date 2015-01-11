package core.exceptions;

import java.io.FileNotFoundException;

import core.helpers.StringHelper;

public final class CoreExceptions {

	/**
	 * For throwing class not found exceptions.
	 * @author Master801
	 */
	public static final class CoreClassNotFoundException extends ClassNotFoundException {

		public CoreClassNotFoundException(String message) {
			super(message + " Please contact Master801 for help!");
		}

		public CoreClassNotFoundException(String message, Object... advanced) {
			super(StringHelper.advancedMessage(message, advanced));
		}

	}

	/**
	 * For throwing null exceptions.
	 * @author Master801
	 */
	public static final class CoreNullPointerException extends NullPointerException {

		public CoreNullPointerException(String message, boolean severeMessage) {
			super(message + " Do not contact Master801 for help.");
		}

		public CoreNullPointerException(String message) {
			super(message + " Please contact Master801 for help!");
		}

		public CoreNullPointerException(String message, Object... advanced) {
			super(StringHelper.advancedMessage(message, advanced));
		}

	}

	/**
	 * For throwing index out of bounds exceptions.
	 * @author Master801
	 */
	public static final class CoreIndexOutOfBoundException extends IndexOutOfBoundsException {

		public CoreIndexOutOfBoundException(String message) {
			super(message + " Please contact Master801 for help!");
		}

		public CoreIndexOutOfBoundException(String message, Object... advanced) {
			super(StringHelper.advancedMessage(message, advanced));
		}

	}

	public static final class CoreFileNotFoundException extends FileNotFoundException {

		public CoreFileNotFoundException(String message) {
			super(message + " Please contact Master801 for help!");
		}

		public CoreFileNotFoundException(String message, Object... advanced) {
			super(StringHelper.advancedMessage(message, advanced));
		}

	}

}
