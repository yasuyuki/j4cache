package io.github.yasuyuki.libs.j4cache;

import junit.framework.TestCase;

public class LruTimeLimitCacheTest extends TestCase {

	public void testGet() throws InterruptedException {
		Cache cache = CacheFactory.getInstance(1, 50, new SimpleKeyTarget() {
			
			public Object loadValue(Object keyObj, Object resource) {
				return keyObj + "Value";
			}
		});

		String key = "key";
		Object object = cache.get(key, null);

		assertEquals("keyValue", object);

		Object object2 = cache.get(key, null);
		assertSame(object, object2);

		Thread.sleep(51);

		object2 = cache.get(key, null);
		assertNotSame(object, object2);

		String key2 = "key2";
		Object cache3 = cache.get(key2, null);

		Object cache4 = cache.get(key2, null);
		assertSame(cache3, cache4);

		object = cache.get(key, null);

		cache4 = cache.get(key2, null);
		assertNotSame(cache3, cache4);
	}

}
