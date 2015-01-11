package core.helpers;

import java.awt.Color;

import core.exceptions.CoreExceptions.CoreNullPointerException;

/**
 * Colourful.
 * @author Master801
 */
public final class ColourHelper {

	public static final Color LIGHT_BLUE = ColourHelper.createNewCustomColour(108, 181, 255);
	public static final Color LIME_GREEN = ColourHelper.createNewCustomColour(102, 255, 51);
	public static final Color SILVER = ColourHelper.createNewCustomColour(192, 192, 192);
	public static final Color PURPLE = ColourHelper.createNewCustomColour(153, 0, 204);
	public static final Color BROWN = ColourHelper.createNewCustomColour(131, 89, 48);

	public static String convertColourToHex(Color colour) {
		return String.format("#%02x%02x%02x", colour.getRed(), colour.getBlue(), colour.getGreen());
	}

	public static String convertRGBToHex(int rgb) {
		return String.format("#%02x%02x%02x", rgb);
	}

	public static int convertHexToRGB(String hexColour) {
		Color hexToRGBColour = Color.decode(hexColour);
		if (hexToRGBColour == null) {
			throw new CoreNullPointerException("The Hex-To-RGB process has failed.");
		}
		return hexToRGBColour.getRGB();
	}

	/**
	 * <p> The first int in the array is the Red. </p>
	 * <p> The second int in the array is the Blue. </p>
	 * <p> The third int in the array is the Green. </p>
	 * <p> The fourth int in the array is the Alpha (transparency). </p>
	 */
	public static int[] convertColourToRGBArray(Color colour) {
		return new int[] { colour.getRed(), colour.getBlue(), colour.getGreen(), colour.getAlpha() };
	}

	public static int convertColourToRGB(Color colour) {
		return colour.getRGB();
	}

	public static Color createNewCustomColour(int red, int blue, int green) {
		return new Color(red, blue, green);
	}

}
