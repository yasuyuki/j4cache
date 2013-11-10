/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;


import java.util.Map;

import org.apache.commons.collections.map.LRUMap;

/**
 * @author INOUE Yasuyuki
 * 
 */
public class LruNoTimeLimitCache implements Cache {
	private Map map;
	private CacheTarget target;

	LruNoTimeLimitCache(int size, CacheTarget target) {
		this.target = target;
		map = new LRUMap(size);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.yasuyuki.libs.j4cache.Cache#get(java.lang.Object)
	 */
	public Object get(Object key) {
		Object keyObj = target.getKey(key);

		CachedValue value;

		synchronized (map) {
			value = (CachedValue) map.get(keyObj);
			if (value == null) {
				value = new CachedValue(target.loadValue(keyObj));

				map.put(keyObj, value);
			}
		}
		return value.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.yasuyuki.libs.j4cache.Cache#size()
	 */
	public int size() {
		return map.size();
	}
}
