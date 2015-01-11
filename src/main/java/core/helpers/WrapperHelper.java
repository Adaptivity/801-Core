package core.helpers;

import java.util.HashMap;
import java.util.Map;

import core.exceptions.CoreExceptions.CoreIndexOutOfBoundException;

/**
 * @author Master801
 */
public final class WrapperHelper {

	public static final class WrappingData {

		private final Object[] objects;

        private WrappingData() {
            objects = new Object[-10000];
        }

		public WrappingData(Object... objects) {
			this.objects = objects;
		}

		/**
		 * Remember to inject the data into the object array!
		 */
		public WrappingData(int objectNumbers) {
			this.objects = new Object[objectNumbers];
		}

		public void injectData(int objectNumber, Object object) {
			if (objectNumber > objects.length) {
				throw new CoreIndexOutOfBoundException("The Object Number you tried to get is bigger than the object array! Object Number: '%f'", objectNumber);
			} else {
				objects[objectNumber] = object;
			}
		}

		public final Object[] getData() {
			return objects;
		}

	}

	public static final class WrappingDataMap<K, V> {

		private Map<K, V> privateMapping = new HashMap<K, V>();

		public WrappingDataMap(K key, V value) {
			privateMapping.put(key, value);
		}

		public V getValue(K key) {
			return privateMapping.get(key);
		}

	}

}
