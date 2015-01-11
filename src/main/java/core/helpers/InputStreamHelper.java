package core.helpers;

import core.exceptions.CoreExceptions.CoreFileNotFoundException;
import core.exceptions.CoreExceptions.CoreNullPointerException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Master801
 */
public final class InputStreamHelper {

	/**
	 * Yes, this does check if the file does indeed exist.
	 */
	public static InputStream getStreamFromResource(String resource, boolean overrideError) {
		if (resource == null) {
			throw new CoreNullPointerException("Resource is null!");
		}
		InputStream stream = InputStreamHelper.class.getResourceAsStream(resource);
		if (overrideError) {
			return stream;
		}
		if (stream == null) {
            throw new CoreNullPointerException("Input Stream is null! Resource: '%s'", resource);
		}
		return stream;
	}

	public static InputStream getStreamFromURL(String url) {
		if (url == null) {
			throw new CoreNullPointerException("The URL is null!");
		}
		InputStream stream = null;
		try {
			stream = new URL(url).openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (stream == null) {
			try {
				throw new CoreFileNotFoundException("The URL you specified cannot be found! Resource: '%s'.", url);
			} catch (CoreFileNotFoundException e) {
				e.printStackTrace();
				throw new CoreNullPointerException("URL Resource is null!");
			}
		}
		return stream;
	}

	public static void closeInputStream(InputStream stream) {
		if (stream == null) {
			throw new CoreNullPointerException("The InputStream is null!");
		}
		try {
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CoreNullPointerException("Failed to close the InputStream! InputStream: '%s'.", stream);
		}
	}

	public static InputStreamReader readFromInputStream(InputStream stream) {
		if (stream == null) {
			throw new CoreNullPointerException("The InputStream is null!");
		}
		return new InputStreamReader(stream);
	}

	public static void closeInputStreamReader(InputStreamReader reader) {
		if (reader == null) {
			throw new CoreNullPointerException("The InputStreamReader is null!");
		}
		try {
			reader.close();
			reader = null;
		} catch (IOException e) {
			e.printStackTrace();
			throw new CoreNullPointerException("Failed to close the InputStreamReader! InputStreamReader: '%s'.", reader);
		}
	}

    public static InputStream openStreamFromEntry(ZipFile zipFile, ZipEntry zipEntry) {
        if (zipFile == null) {
            return null;
        }
        if (zipEntry == null) {
            return null;
        }
        try {
            return zipFile.getInputStream(zipEntry);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
