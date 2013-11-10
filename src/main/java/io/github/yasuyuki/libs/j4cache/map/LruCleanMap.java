/**
 * 
 */
package io.github.yasuyuki.libs.j4cache.map;

import io.github.yasuyuki.libs.j4cache.CacheCleanableTarget;
import io.github.yasuyuki.libs.j4cache.CachedValue;

import org.apache.commons.collections.map.AbstractLinkedMap;
import org.apache.commons.collections.map.LRUMap;

/**
 * @author INOUE Yasuyuki
 * 
 */
public class LruCleanMap extends LRUMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2133508562813502601L;
	private CacheCleanableTarget target;

	/**
	 * @param maxSize
	 */
	public LruCleanMap(int maxSize, CacheCleanableTarget target) {
		super(maxSize);
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections.map.LRUMap#removeLRU(org.apache.commons
	 * .collections.map.AbstractLinkedMap.LinkEntry)
	 */
	protected boolean removeLRU(AbstractLinkedMap.LinkEntry entry) {
		CachedValue value = (CachedValue) entry.getValue();
		return target.clean(entry.getKey(), value.getValue());
	}
}
