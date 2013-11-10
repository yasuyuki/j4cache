/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;

/**
 * @author INOUE Yasuyuki
 * 
 */
public class CacheFactory {

	/**
	 * 
	 */
	CacheFactory() {
	}

	/**
	 * @param size
	 * @param timeout
	 * @return
	 */
	public static Cache getInstance(int size, long timeout, CacheTarget target) {
		if (size > 0) {
			if (timeout > 0) {
				return new LruTimeLimitCache(size, timeout, target);
			} else {
				return new LruNoTimeLimitCache(size, target);
			}
		} else {
			if (timeout > 0) {
				return new NoSizeLimitTimeLimitCache(timeout, target);
			} else {
				return new NoLimitCache(target);
			}
		}
	}

}
