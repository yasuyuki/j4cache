/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;

/**
 * @author INOUE Yasuyuki
 * 
 */
public interface CacheCleanableTarget extends CacheTarget {

	/**
	 * @param keyObj
	 * @param value
	 * @return
	 */
	public boolean clean(Object keyObj, Object value);

}
