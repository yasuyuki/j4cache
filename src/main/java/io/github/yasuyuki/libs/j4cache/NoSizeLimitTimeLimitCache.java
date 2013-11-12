/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author INOUE Yasuyuki
 * 
 */
public class NoSizeLimitTimeLimitCache implements Cache {
	private volatile LinkedHashMap map;
	private long timeout;
	private CacheTarget target;

	NoSizeLimitTimeLimitCache(long timeout, CacheTarget target) {
		this.target = target;
		this.timeout = timeout;
		map = new LinkedHashMap();
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
		value = (CachedValue) map.get(keyObj);
		if (value != null && !value.isLimitOver(now)) {
			return value.getValue();
		}

		synchronized (map) {
			value = (CachedValue) map.get(keyObj);
			if (value != null && !value.isLimitOver(now)) {
				return value.getValue();
			}

			value = new CachedValue(target.loadValue(keyObj, resource), now, timeout);

			LinkedHashMap tempMap = (LinkedHashMap) map.clone();
			tempMap.put(keyObj, value);

			clean(tempMap, now);

			map = tempMap;
		}
		return value.getValue();
	}

	/**
	 * @param tempMap
	 * @param now
	 */
	private void clean(LinkedHashMap tempMap, long now) {
		for (Iterator it = tempMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Entry) it.next();
			CachedValue value = (CachedValue) entry.getValue();
			if (!value.isLimitOver(now)) {
				return;
			}
			it.remove();
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
