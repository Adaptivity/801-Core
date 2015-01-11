package core.helpers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import core.exceptions.CoreExceptions.CoreNullPointerException;

/**
 * @author Master801
 */
public final class ImageHelper {

	/**
	 * This automatically closes the InputStream.
	 */
	public static BufferedImage getImageFromStream(InputStream stream) {
		if (stream == null) {
			throw new CoreNullPointerException("The InputStream is null!");
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CoreNullPointerException("Failed to find the Image from the InputStream!");
		}
		if (image == null) {
			throw new CoreNullPointerException("The BufferedImage is null!");
		}
		return image;
	}

}
