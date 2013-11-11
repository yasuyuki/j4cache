/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;

import junit.framework.TestCase;

/**
 * @author INOUE Yasuyuki
 *
 */
public class LruNoTimeLimitCacheTest extends TestCase {

	public void testGet() throws InterruptedException {
		Cache cache = CacheFactory.getInstance(1, 0, new SimpleKeyTarget() {
			
			public Object loadValue(Object keyObj, Object resource) {
				return keyObj + "Value";
			}
		});

		String key = "key";
		Object object = cache.get(key, null);

		assertEquals("keyValue", object);

		Object object2 = cache.get(key, null);
		assertSame(object, object2);

		Thread.sleep(5);

		object2 = cache.get(key, null);
		assertSame(object, object2);
	}

}
