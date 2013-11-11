/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.LRUMap;

/**
 * @author INOUE Yasuyuki
 * 
 */
public class LruTimeLimitCache implements Cache {
	private LRUMap map;
	private long timeout;
	private CacheTarget target;

	LruTimeLimitCache(int size, long timeout, CacheTarget target) {
		this.target = target;
		this.timeout = timeout;
		map = new LRUMap(size);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.yasuyuki.libs.j4cache.Cache#get(java.lang.Object)
	 */
	public Object get(Object key, Object resource) {
		Object keyObj = target.getKey(key);
		long now = System.currentTimeMillis();

		CachedValue value;

		synchronized (map) {
			value = (CachedValue) map.get(keyObj);
			if (value != null && !value.isLimitOver(now)) {
				return value.getValue();
			}

			value = new CachedValue(target.loadValue(keyObj, resource), now, timeout);

			clean(map, now);

			map.put(keyObj, value);
		}
		return value.getValue();
	}

	/**
	 * @param tempMap
	 * @param now
	 */
	private void clean(LRUMap tempMap, long now) {
		for (Iterator it = tempMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Entry) it.next();
			CachedValue value = (CachedValue) entry.getValue();
			if (!value.isLimitOver(now)) {
				return;
			}
			tempMap.remove(entry.getKey());
		}
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
