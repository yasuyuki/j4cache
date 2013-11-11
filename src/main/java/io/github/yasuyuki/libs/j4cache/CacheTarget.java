/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;

/**
 * @author INOUE Yasuyuki
 * 
 */
public interface CacheTarget {
	/**
	 * @param keyObj
	 * @return
	 */
	public Object getKey(Object keyObj);

	/**
	 * @param keyObj
	 * @param resource
	 * @return
	 */
	public Object loadValue(Object keyObj, Object resource);
}
