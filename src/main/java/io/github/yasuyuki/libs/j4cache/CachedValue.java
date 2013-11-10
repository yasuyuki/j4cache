/**
 * 
 */
package io.github.yasuyuki.libs.j4cache;

/**
 * @author INOUE Yasuyuki
 * 
 */
public class CachedValue {
	private final Object value;
	private final long limit;

	CachedValue(Object value, long now, long limit) {
		this.value = value;
		this.limit = now + limit;
	}

	public CachedValue(Object value) {
		this.value = value;
		this.limit = 0;
	}

	/**
	 * @return
	 */
	boolean isNull() {
		return value == null;
	}

	/**
	 * @param now
	 * @return
	 */
	boolean isLimitOver(long now) {
		return limit < now;
	}

	/**
	 * @return
	 */
	public Object getValue() {
		return value;
	}
}
