/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;

import junit.framework.TestCase;

/**
 * @author INOUE Yasuyuki
 *
 */
public class NoSizeLimitTimeLimitCacheTest extends TestCase {

	public void testMultiple() throws InterruptedException {
		Cache cache = CacheFactory.getInstance(0, 50, new SimpleKeyTarget() {
			
			public Object loadValue(Object keyObj, Object resource) {
				return keyObj + "Value";
			}
		});

		String key = "key";
		Object object = cache.get(key, null);

		assertEquals("keyValue", object);

		String key2 = "key2";
		Object cache3 = cache.get(key2, null);

		assertEquals("key2Value", cache3);

		Thread.sleep(51);

		String key3 = "key3";
		cache.get(key3, null);
	}

}
