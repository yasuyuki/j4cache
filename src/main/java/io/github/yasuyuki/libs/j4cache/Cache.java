/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.LRUMap;

/**
 * @author INOUE Yasuyuki
 * 
 */
public class Cache {
	private long timeout;
	private volatile Map map;
	private CacheTarget target;
	private boolean sized;

	Cache(int size, long timeout, CacheTarget target) {
		this.timeout = timeout;
		this.target = target;
		this.sized = size > 0;
		if (sized) {
			map = new LRUMap(size);
		} else {
			map = new HashMap();
		}
	}

	/**
	 * @param size
	 * @param timeout
	 * @return
	 */
	public static Cache getInstance(int size, long timeout, CacheTarget target) {
		return new Cache(size, timeout, target);
	}

	/**
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		Object keyObj = target.getKey(key);
		CachedValue value = (CachedValue) map.get(keyObj);
		long now;
		if (timeout == 0) {
			now = 0;
		} else {
			now = System.currentTimeMillis();
		}

		if (value != null && !value.isLimitOver(now)) {
			return value.getValue();
		}

		synchronized (map) {
			value = (CachedValue) map.get(keyObj);
			if (value != null && !value.isLimitOver(now)) {
				return value.getValue();
			}

			value = new CachedValue(target.loadValue(keyObj), now);

			Map tmpMap;
			if (sized) {
				tmpMap = (Map) ((LRUMap) map).clone();
			} else {
				tmpMap = (Map) ((HashMap) map).clone();
			}

			tmpMap.put(keyObj, value);
			map = tmpMap;
		}
		return value.getValue();
	}
}
