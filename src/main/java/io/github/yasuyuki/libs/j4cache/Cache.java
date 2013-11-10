package io.github.yasuyuki.libs.j4cache;

public interface Cache {

	/**
	 * @param key
	 * @return
	 */
	public abstract Object get(Object key);

	/**
	 * @return
	 */
	public int size();
}