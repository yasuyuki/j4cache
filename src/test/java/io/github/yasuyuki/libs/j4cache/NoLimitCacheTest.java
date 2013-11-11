package io.github.yasuyuki.libs.j4cache;

import junit.framework.TestCase;

public class NoLimitCacheTest extends TestCase {

	public void testGet() {
		Cache cache = CacheFactory.getInstance(0, 0, new SimpleKeyTarget() {
			
			public Object loadValue(Object keyObj, Object resource) {
				return keyObj + "";
			}
		});

		String key = "key";
		Object object = cache.get(key, null);

		assertEquals(key, object);

		Object object2 = cache.get(key, null);
		assertSame(object, object2);
	}

}
