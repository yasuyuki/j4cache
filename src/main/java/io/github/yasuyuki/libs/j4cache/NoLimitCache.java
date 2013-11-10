/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;


import java.util.HashMap;
import java.util.Map;

/**
 * @author INOUE Yasuyuki
 * 
 */
public class NoLimitCache implements Cache {
	private volatile Map map;
	private CacheTarget target;

	NoLimitCache(CacheTarget target) {
		this.target = target;
		map = new HashMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.yasuyuki.libs.j4cache.Cache#get(java.lang.Object)
	 */
	public Object get(Object key) {
		Object keyObj = target.getKey(key);

		CachedValue value = (CachedValue) map.get(keyObj);
		if (value != null) {
			return value.getValue();
		}

		synchronized (map) {
			value = (CachedValue) map.get(keyObj);
			if (value != null) {
				return value.getValue();
			}

			value = new CachedValue(target.loadValue(keyObj));

			HashMap tempMap = new HashMap();
			tempMap.put(keyObj, value);

			map = tempMap;
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
