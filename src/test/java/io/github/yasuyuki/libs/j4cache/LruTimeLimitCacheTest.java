package io.github.yasuyuki.libs.j4cache;

import junit.framework.TestCase;

public class LruTimeLimitCacheTest extends TestCase {

	public void testGet() throws InterruptedException {
		Cache cache = CacheFactory.getInstance(1, 30, new SimpleKeyTarget() {
			
			public Object loadValue(Object keyObj) {
				return keyObj + "Value";
			}
		});

		String key = "key";
		Object object = cache.get(key);

		assertEquals("keyValue", object);

		Object object2 = cache.get(key);
		assertSame(object, object2);

		Thread.sleep(31);

		object2 = cache.get(key);
		assertNotSame(object, object2);

		String key2 = "key2";
		Object cache3 = cache.get(key2);

		Object cache4 = cache.get(key2);
		assertSame(cache3, cache4);

		object = cache.get(key);

		cache4 = cache.get(key2);
		assertNotSame(cache3, cache4);
	}

}
