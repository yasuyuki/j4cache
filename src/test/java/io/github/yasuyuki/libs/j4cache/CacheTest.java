package io.github.yasuyuki.libs.j4cache;

import junit.framework.TestCase;

public class CacheTest extends TestCase {

	public void testGet() {
		Cache cache = Cache.getInstance(0, 0, new SimpleKeyTarget() {
			
			public Object loadValue(Object keyObj) {
				return keyObj;
			}
		});

		Object object = cache.get("key");

		assertEquals("key", object);

		object = cache.get("key");
		assertEquals("key", object);
	}

}
