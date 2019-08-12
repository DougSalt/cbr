/**
 * This file is part of caber.
 *
 * caber is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * caber is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOASE. See the
 * GNU General Public License for more detials.
 *
 * You should have received a copy of the GNU General Public License
 * along with caber. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) The James Hutton Institute 2018
 */


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;

/**
 * <!-- StringInitializer -->
 *
 * Class to enable initialization of objects from strings, and
 * facilitate the storage of those objects as strings in ways that
 * maximize the readability of the string (e.g. when seen in a file)
 * and restorability. A Base64 serialization of the object is used
 * if it cannot be rebuilt from its <code>toString()</code> method.
 *
 * @author Gary Polhill
 * 
 * @param <T>
 */
public class StringInitializer<T> {
	/**
	 * The string value read (or to write)
	 */
	private final String value_str;

	/**
	 * The class of the object
	 */
	private final Class<T> cls;

	/**
	 * Whether the string value is in Base 64 form rather than
	 * <code>toString()</code>
	 */
	private final boolean is_base64;

	/**
	 * Prefix used to detect Base 64 initialization string
	 */

	public static final String BASE64_START = "Base64[";

	/**
	 * Suffix of Base 64 initialization string
	 */
	public static final String BASE64_END = "]";

	/**
	 * <!-- StringInitializer constructor -->
	 * 
	 * Constructor for a string value and a class. The value string
	 * <code>str</code>
	 * can be Base 64 formatted, when it will begin with the public constants
	 * <code>BASE64_START</code> and <code>BASE64_END</code>
	 *
	 * @param str
	 *          The string value to be used to assign to an instance
	 * @param cls
	 *          The class the instance is to be.
	 */
	public StringInitializer(String str, Class<T> cls) {
		this.value_str = str;
		this.cls = cls;
		this.is_base64 = (value_str.startsWith(BASE64_START) && value_str.endsWith(BASE64_END));
	}

	/**
	 * <!-- StringInitializer constructor -->
	 * 
	 * Constructor for a formatted string indicating the class and
	 * value in the format <code><i>class</i>(<i>value</i>)</code>
	 *
	 * @param str
	 */
	@SuppressWarnings("unchecked")
	public StringInitializer(String str) {
		int open_bracket = str.indexOf("(");
		int close_bracket = str.lastIndexOf(")");
		if(open_bracket < 0 || close_bracket < 0) {
			throw new IllegalArgumentException(
					"Cannot initialize from string \"" + str + "\" because it is not formatted as class(value)");
		}
		try {
			this.cls = (Class<T>)Class.forName(str.substring(0, open_bracket));
		}
		catch(ClassNotFoundException e) {
			throw new IllegalStateException("Can't find class " + str.substring(0, open_bracket));
		}
		this.value_str = str.substring(open_bracket + 1, close_bracket);
		this.is_base64 = (value_str.startsWith(BASE64_START) && value_str.endsWith(BASE64_END));
	}

	/**
	 * <!-- StringInitializer constructor -->
	 * 
	 * Constructor using an object, which is useful for confirming
	 * at least partially that the object will be restored from its
	 * value string. Try creating a new instance first using the
	 * <code>obj</code>'s <code>toString()</code> method. If the
	 * result <code>equals(obj)</code> then use this more readable
	 * format as the value string; otherwise, use the Base 64
	 * conversion. The Base 64 conversion is not checked, largely
	 * because there isn't an alternative if this doesn't work.
	 * 
	 * @param obj
	 * @param enforceBase64
	 *          enforce Base 64 encoding, and avoid creating a new instance even
	 *          if <code>obj</code>'s class has a single-argument
	 *          <code>String</code> constructor.
	 */
	@SuppressWarnings("unchecked")
	public StringInitializer(boolean enforceBase64, T obj) {
		this.cls = (Class<T>)obj.getClass();
		if(!enforceBase64 && isInitializable(cls)) {
			T new_obj = newInstance(cls, obj.toString());
			if(new_obj != null && new_obj.equals(obj)) {
				this.value_str = obj.toString();
				this.is_base64 = false;
			}
			else {
				this.value_str = toBase64String(obj);
				this.is_base64 = true;
			}
		}
		else {
			this.value_str = toBase64String(obj);
			this.is_base64 = true;
		}
	}

	/**
	 * <!-- isInitializable -> boolean -->
	 *
	 * @param c
	 *          The class to check
	 * @return <code>true</code> if the class has a constructor with a single
	 *         <code>String</code> argument
	 */
	public static boolean isInitializable(Class<?> c) {
		try {
			c.getConstructor(String.class);
			return true;
		}
		catch(NoSuchMethodException | SecurityException e) {
			return false;
		}
	}

	/**
	 * <!-- getStringConstructor -> Constructor<V> -->
	 *
	 * @param c
	 *          The class to retrieve the <code>String</code> constructor of
	 * @return The constructor, or <code>null</code> if no constructor exists. You
	 *         can check using {@link #isInitializable()} first
	 */
	public static <V> Constructor<V> getStringConstructor(Class<V> c) {
		try {
			return c.getConstructor(String.class);
		}
		catch(NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	/**
	 * <!-- newInstance -> V -->
	 * 
	 * Create a new instance from a class and a string. The class must have a
	 * single <code>String</code>-argument constructor, otherwise the method
	 * will return <code>null</code>. <code>null</code> is also returned if
	 * the constructor can't be called for any reason.
	 *
	 * @param c
	 *          The class to construct an instance of
	 * @param str
	 *          The <code>String</code> to use to initialize the instance with
	 * @return The instance, or <code>null</code> if it could not be created for
	 *         any reason
	 */
	public static <V> V newInstance(Class<V> c, String str) {
		Constructor<V> cons = getStringConstructor(c);
		try {
			return cons.newInstance(str);
		}
		catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return null;
		}
	}

	/**
	 * <!-- parseNewInstance -> Object -->
	 *
	 * Create a new instance from a formatted string of the form
	 * <code><i>class</i>(<i>value</i>)</code>.
	 * If the <code><i>value</i></code> begins with {@link #BASE64_START} and ends
	 * with {@link #BASE64_END}
	 * then the <code><i>class</i></code> does not need to have a single-argument
	 * <code>String</code> constructor.
	 *
	 * @param str
	 *          A formatted string of the form
	 *          <code><i>class</i>(<i>value</i>)</code>
	 * @return An initialized object of the given class, or <code>null</code>
	 * @throws ClassNotFoundException
	 *           if the class in the formatted string can't be found.
	 */
	public static Object parseNewInstance(String str) throws ClassNotFoundException {
		int open_bracket = str.indexOf("(");
		int close_bracket = str.lastIndexOf(")");
		if(open_bracket < 0 || close_bracket < 0) {
			throw new IllegalArgumentException(
					"Cannot initialize from string \"" + str + "\" because it is not formatted as class(value)");
		}
		Class<?> cls = Class.forName(str.substring(0, open_bracket));
		if(isInitializable(cls)) {
			return newInstance(cls, str.substring(open_bracket + 1, close_bracket));
		}
		else {
			String b64_value = str.substring(open_bracket + 1, close_bracket);
			return newBase64Instance(b64_value);
		}
	}

	/**
	 * <!-- newBase64Instance -> Object -->
	 * 
	 * Build an instance using a Base 64 formatted value string
	 *
	 * @param b64_value
	 *          The value string with which the instance is to be built, which
	 *          must begin with {@link #BASE64_START} and end with
	 *          {@link #BASE64_END}
	 * @return The instance, or <code>null</code> if it could not be constructed
	 *         for any reason
	 * @throws ClassNotFoundException
	 *           if the class to instantiate the instance from is not available
	 */
	public static Object newBase64Instance(String b64_value) throws ClassNotFoundException {
		if(!(b64_value.startsWith(BASE64_START) && b64_value.endsWith(BASE64_END))) {
			return null;
		}
		String value = b64_value.substring(BASE64_START.length(), b64_value.length() - BASE64_END.length());
		try {
			byte[] valbytes = Base64.getDecoder().decode(value);
			ByteArrayInputStream b = new ByteArrayInputStream(valbytes);

			ObjectInputStream stream = new ObjectInputStream(b);
			Object obj = stream.readObject();
			b.close();
			return obj;
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * <!-- toBase64String -> String -->
	 *
	 * Create a string from an object suitable for rebuilding it with
	 * {@link #newBase64Instance(String)}
	 *
	 * @param obj
	 *          The object to serialize
	 * @return a Base 64 formatted serialization string
	 */
	public static String toBase64String(Object obj) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		try {
			ObjectOutputStream stream = new ObjectOutputStream(b);
			stream.writeObject(obj);
			stream.close();
			return BASE64_START + Base64.getEncoder().encodeToString(b.toByteArray()) + BASE64_END;
		}
		catch(IOException e) {
			throw new IllegalStateException("Unable to create Base64 String from " + obj + ": " + e);
		}
	}

	/**
	 * <!-- isInitializable -> boolean -->
	 *
	 * @return <code>true</code> if the {@link #cls} has a single
	 *         <code>String</code> argument constructor
	 */
	public boolean isInitializable() {
		return isInitializable(cls);
	}

	/**
	 * <!-- isBase64 -> boolean -->
	 *
	 * @return <code>true</code> if the object is encoded in Base64 format (rather
	 *         than using <code>toString()</code>).
	 */
	public boolean isBase64() {
		return is_base64;
	}

	/**
	 * <!-- newInstance -> T -->
	 *
	 * @return a new instance of the {@link #cls} using the {@link #value_str}, or
	 *         <code>null</code> if this could not be achieved.
	 */
	@SuppressWarnings("unchecked")
	public T newInstance() {
		if(is_base64) {
			try {
				Object obj = newBase64Instance(value_str);
				if(cls.isAssignableFrom(obj.getClass())) {
					return (T)obj;
				}
				else {
					throw new IllegalStateException("Initialization from Base 64 string \"" + value_str + "\" yields an object of class "
							+ obj.getClass().getName() + " instead of " + cls.getName() + " as expected");
				}
			}
			catch(ClassNotFoundException e) {
				throw new IllegalStateException(
						"Intitialization from Base 64 string \"" + value_str + "\" yields a ClassNotFoundException \"" + e.getMessage()
								+ "\" for a class " + cls.getName() + " that should be present");
			}
		}
		else {
			return newInstance(cls, value_str);
		}
	}

	/**
	 * <!-- toString -> String -->
	 *
	 * @return A string suitable for parsing with
	 *         {@link #parseNewInstance(String)} or initializing this instance of
	 *         <code>StringInitializer</code> using its
	 *         {@link #StringInitializer(String)} constructor
	 */
	@Override
	public String toString() {
		return cls.getName() + "(" + value_str + ")";
	}

	/**
	 * <!-- override equals -->
	 * 
	 * Check if two <code>StringInitializer</code>s are equal.
	 * This uses strict equality -- the classes must be the same as
	 * well as the string values (implying the same encoding). This
	 * avoids issues with creating new instances if this is
	 * something to be avoided.
	 * For a less strict interpretation of equality, see
	 * {@link #looselyEquals(Object)}.
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof StringInitializer) {
			StringInitializer<?> siobj = (StringInitializer<?>)obj;
			if(!siobj.cls.equals(cls)) return false;
			else if(!siobj.is_base64 == is_base64) return false;
			else if(!siobj.value_str.equals(value_str)) return false;
			else {
				return true;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * <!-- looselyEquals -> boolean -->
	 * 
	 * Loose equality -- essentially the object this initializer
	 * creates is equal to <code>obj</code> or the object <code>obj</code>
	 * creates if <code>obj</code> is a <code>StringInitializer</code>.
	 *
	 * @param obj
	 * @return <code>true</code> if <code>this</code> creates the same object as
	 *         <code>obj</code> (creates)
	 */
	public boolean looselyEquals(Object obj) {
		if(obj == null) return false;
		T newThis = newInstance();
		if(obj instanceof StringInitializer) {
			return newThis.equals(((StringInitializer<?>)obj).newInstance());
		}
		else {
			return newThis.equals(obj);
		}
	}

	/**
	 * <!-- restores -> boolean -->
	 *
	 * @param obj
	 * @return <code>true</code> if instantiating an instance from the
	 *         {@link #cls} and {@link #value_str} yields a result that
	 *         <code>equals(obj)</code>.
	 */
	public boolean restores(T obj) {
		T new_obj = newInstance();
		return new_obj.equals(obj);
	}

	/**
	 * <!-- toInitializableString -> String -->
	 *
	 * <p>
	 * Generate a string from <code>obj</code> that can be used to initialize it
	 * if possible, which will take the form of the formatted string suitable
	 * for use with {@link #parseNewInstance(String)} and the
	 * {@link #StringInitializer(String)} constructor.
	 * This method will cause a new instance of class <code>obj</code> belongs to
	 * if that class has a single-argument <code>String</code> constructor. <i>So
	 * don't
	 * use this method if creating a new instance is inappropriate!</i> This is
	 * done to
	 * check that <code>obj</code>'s {@link Object#toString()} method yields a
	 * string
	 * that {@link #restores(Object)} an object that <code>equals(obj)</code> when
	 * passed
	 * to the constructor.
	 * </p>
	 * 
	 * <p>
	 * If the class of <code>obj</code> doesn't have a single-argument
	 * <code>String</code> constructor, or calling that constructor with the
	 * results of
	 * <code>obj.toString()</code>
	 * then a Base 64 serialization string is returned, including
	 * {@link #BASE64_START} prefix
	 * and {@link #BASE64_END} suffix.
	 * </p>
	 * 
	 * @param obj
	 * @return An initialization string
	 */
	@SuppressWarnings("unchecked")
	public static <V> String toInitializableString(V obj) {
		if(isInitializable(obj.getClass()) && (new StringInitializer<V>(obj.toString(), (Class<V>)obj.getClass())).restores(obj)) {
			return obj.getClass().getName() + "(" + obj.toString() + ")";
		}
		else {
			return obj.getClass().getName() + "(" + toBase64String(obj) + ")";
		}
	}

}
