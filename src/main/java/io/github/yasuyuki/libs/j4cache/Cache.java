package io.github.yasuyuki.libs.j4cache;

public interface Cache {

	/**
	 * @param key
	 * @param resource
	 * @return
	 */
	public abstract Object get(Object key, Object resource);

	/**
	 * @return
	 */
	public int size();
}