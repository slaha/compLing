package cz.compling.utils;

/**
 *
 * This class simply holds a value of type T.
 *
 * <p>Its usage is to pass value as param, change the value in the method and return it back
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 15.2.14 9:49</dd>
 * </dl>
 */
public class Reference<T> {

	public T value;

	public Reference() {
		this(null);
	}

	public Reference(T value) {
		this.value = value;
	}
}
