/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;

/**
 * @author INOUE Yasuyuki
 * 
 */
public abstract class SimpleKeyTarget implements CacheTarget {

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.yasuyuki.libs.j4cache.CacheTarget#getKey(java.lang.Object)
	 */
	public Object getKey(Object keyObj) {
		return keyObj;
	}
}
