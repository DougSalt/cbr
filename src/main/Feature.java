/**
 * Feature.java, uk.ac.hutton.caber
 *
 * Copyright (C) The James Hutton Institute 2016
 *
 * This file is part of caber
 *
 * caber is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * caber is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * caber. If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.Set;

/**
 * <!-- Feature -->
 * 
 * A class to capture one of the dimensions of a {@link State} or
 * {@link Outcome}
 * 
 * @author Gary Polhill
 */
public class Feature<T> {

	/**
	 * The Java data type of this Feature
	 */
	private final Class<T> type;

	/**
	 * The name of this Feature
	 */
	private final String name;

	/**
	 * <!-- Feature constructor -->
	 * 
	 * @param name A name to use for the Feature
	 * @param type A type to use for the Feature
	 */
	public Feature(String name, Class<T> type) {
		if (type == null) {
			throw new IllegalArgumentException("Cannot create a Feature with a null type");
		}
		if (name == null || name.equals("") || name.matches("^\\s+$")) {
			throw new IllegalArgumentException("Cannot create a Feature with a null or empty name (" + name + ")");
		}
		this.type = type;
		this.name = name;
	}

	/**
	 * <!-- fromString -->
	 * 
	 * Allow a Feature to be constructed from its {@link #toString()} value
	 *
	 * @param str A string created by a previous call to {@link #toString()}
	 * @return a new {@link Feature} with appropriate type and name
	 */
	public static <V> Feature<?> fromString(String str) {
		if (str == null) {
			throw new IllegalArgumentException("Cannot create a Feature from a null Feature string");
		}
		if (!str.endsWith(")")) {
			throw new IllegalArgumentException(
					"Cannot create a Feature from invalid Feature string \"" + str + "\" (no clase bracket");
		}
		int open = str.lastIndexOf("(");
		if (open < 0) {
			throw new IllegalArgumentException(
					"Cannot create a Feature from invalid Feature string \"" + str + "\" (no open bracket)");
		}
		if (open < 2) {
			throw new IllegalArgumentException("Cannot create a Feature from invalid Feature string \"" + str
					+ "\" (no obvious label -- missing space before open bracket?)");
		}
		if (str.charAt(open - 1) != ' ') {
			throw new IllegalArgumentException("Cannot create a Feature from invalid Feature string \"" + str
					+ "\" (no space before open bracket)");
		}
		String className = str.substring(open + 1, str.length() - 1);
		String name = str.substring(0, open - 1);
		try {
			@SuppressWarnings("unchecked")
			Class<V> type = (Class<V>) Class.forName(className);
			return new Feature<V>(name, type);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(
					"Cannot create a Feature from Feature string \"" + str + "\" (class \"className\" not found");
		}
	}

	/**
	 * <!-- getType -->
	 *
	 * @return the type of this Feature
	 */
	public Class<T> getType() {
		return type;
	}

	/**
	 * <!-- getName -->
	 *
	 * @return the name of this Feature
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- getValue --> Use this method to get {@link Feature.Value}s from their
	 * basic Java datatypes
	 * 
	 * @param value a Java object to treat as a Feature.Value
	 * @return a {@link Feature.Value} object
	 */
	public Value getValue(T value) {
		return new Value(value, this);
	}

	@SuppressWarnings("unchecked")
	public Value getObjectValue(Object value) {
		if (type.isAssignableFrom(value.getClass())) {
			return new Value((T) value, this);
		} else {
			throw new IllegalArgumentException("\"" + value + "\", with class " + value.getClass().getSimpleName()
					+ " cannot be assigned to feature \"" + name + "\" type " + type.getSimpleName());
		}
	}

	/**
	 * <!-- getQuery -->
	 * 
	 * @param value
	 * @return a {@link Feature.Value} object to treat as a query
	 */
	public Value getQuery(String op, T value) {
		if (!op.equals("EQ")) {
			throw new IllegalArgumentException(
					"Unrecognized operator for query against value of type " + value.getClass() + ": " + op);
		}
		return new Value(value, this);
	}

	/**
	 * <!-- toString -->
	 * 
	 * A string representation of the Feature, which can be reconstituted as a
	 * Feature using {@link #fromString(String)}.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + " (" + type.getCanonicalName() + ")";
	}

	/**
	 * <!-- equals -->
	 * 
	 * Test for equality with another Feature. The condition for mutual
	 * assignability (necessary for equality to work both ways) probably means that
	 * the classes must be the same.
	 *
	 * Test for equality with another Feature.
	 * 
	 * @param other
	 * @return <code>true</code> if the two Features are the same.
	 */
	public boolean equals(Feature<?> other) {
		return (name.equals(other.getName()) && type.isAssignableFrom(other.getType())
				&& other.getType().isAssignableFrom(type));
	}

	protected enum WildCard {
		NONE, ANYTHING, NOTHING, ONE_OF, NONE_OF;

		/**
		 * <!-- toString -> String -->
		 *
		 * @param wildcard_data
		 * @return
		 */
		public String toString(Set<?> wildcard_data) {
			StringBuffer buf = new StringBuffer("WildCard(");
			buf.append(this.toString());
			switch (this) {
			case NONE:
			case ANYTHING:
			case NOTHING:
				break;
			case ONE_OF:
			case NONE_OF:
				buf.append(" {");
				if (wildcard_data != null) {
					boolean first = true;
					for (Object thing : wildcard_data) {
						if (first) {
							first = false;
						} else {
							buf.append(", ");
						}
						buf.append(thing.toString());
					}
				}
				buf.append("}");
			}
			buf.append(")");
			return buf.toString();
		}
	}

	public static Feature<?>.Value parseValueString(String str) throws ClassNotFoundException {
		String[] parts = str.split(" = ");
		Feature<?> f = Feature.fromString(parts[0]);
		return f.getObjectValue(StringInitializer.parseNewInstance(parts[1]));
	}

	/**
	 * <!-- Value -->
	 * 
	 * Values of {@link Feature}s.
	 *
	 * @author Gary Polhill
	 */
	public class Value {

		/**
		 * The value
		 */
		protected final T value;

		/**
		 * Reference back to the {@link Feature} this is a value of.
		 */
		protected final Feature<T> feature;

		protected final WildCard wildcard;

		protected final Set<T> wildcard_data;

		/**
		 * <!-- Value constructor -->
		 * 
		 * The reason for having Value as an embedded class of Feature is so that
		 * Feature.Values can only be constructed using their corresponding Feature.
		 * This ensures that no Feature.Values have undefined Features.
		 *
		 * @param value
		 * @param feature
		 */
		protected Value(T value, Feature<T> feature) {
			if (value == null) {
				throw new IllegalArgumentException("Cannot create a Feature.Value with a null value");
			}
			// The check below is needed in case the generics have not worked to
			// pick up issues at compile time. One way this can happen is by a
			// call to fromString()
			if (!value.getClass().isAssignableFrom(feature.getType())) {
				throw new IllegalArgumentException("Cannot assign value " + value + " (class " + value.getClass()
						+ ") to feature \"" + feature.getName() + "\" with type " + feature.getType());
			}
			this.value = value;
			this.feature = feature;
			this.wildcard = WildCard.NONE;
			this.wildcard_data = null;
		}

		protected Value(Feature<T> feature, WildCard wildcard) {
			if (wildcard == null || wildcard == WildCard.NONE) {
				throw new IllegalArgumentException("Bug!");
			}
			this.wildcard = wildcard;
			this.wildcard_data = null;
			this.value = null;
			this.feature = feature;
		}

		protected Value(Feature<T> feature, WildCard wildcard, Set<T> data) {
			if (wildcard == null || wildcard == WildCard.NONE) {
				throw new IllegalArgumentException("Bug!");
			}
			this.wildcard = wildcard;
			this.wildcard_data = data;
			this.value = null;
			this.feature = feature;
		}

		/**
		 * <!-- getValue -->
		 *
		 * @return the value
		 */
		public T getValue() {
			return value;
		}

		/**
		 * <!-- getFeature -->
		 *
		 * @return the feature
		 */
		public Feature<T> getFeature() {
			return feature;
		}

		/**
		 * <!-- toString -->
		 * 
		 * A string for the feature value (the same as the string of the value)
		 *
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			if (wildcard != WildCard.NONE) {
				return wildcard.toString(wildcard_data);
			}
			return value.toString();
		}

		/**
		 * <!-- equals -->
		 * 
		 * Test for equality with another feature value
		 *
		 * @param other
		 * @return <code>true</code> if the value and feature are equal.
		 */
		public boolean equals(Feature<?>.Value other) {
			if (wildcard != WildCard.NONE) {
				if (feature.equals(other.getFeature()) && wildcard == other.wildcard) {
					switch (wildcard) {
					case NONE:
						throw new RuntimeException("PANIC!");
					case ANYTHING:
					case NOTHING:
						return true;
					case ONE_OF:
					case NONE_OF:
						if (wildcard_data == null || other.wildcard_data == null) {
							return wildcard_data == other.wildcard_data;
						} else {
							return wildcard_data.equals(other.wildcard_data);
						}
					default:
						throw new RuntimeException("BUG! unimplemented wildcard " + wildcard);
					}
				} else {
					return false;
				}
			} else {
				return (value.equals(other.getValue()) && feature.equals(other.getFeature()));
			}
		}

		/**
		 * <!-- matches -->
		 * 
		 * Test that this feature value matches another. Default action is to test for
		 * equality
		 * 
		 * @param other
		 * @return result of {@link #equals(Value)}
		 */
		public boolean matches(Feature<?>.Value other) {
			switch (wildcard) {
			case NONE:
				if (other.wildcard == WildCard.NONE) {
					return equals(other);
				} else {
					return other.matches(this);
				}
			case ANYTHING:
				return true;
			case NOTHING:
				return false;
			case ONE_OF:
				if (wildcard_data == null) {
					// One of {} is the same as NOTHING
					return false;
				} else if (other.wildcard == WildCard.ANYTHING) {
					return true;
				} else if (other.wildcard == WildCard.NOTHING) {
					return false;
				} else if (other.wildcard == WildCard.NONE) {
					return wildcard_data.contains(other.getValue());
				} else {
					return equals(other);
				}
			case NONE_OF:
				if (wildcard_data == null) {
					// None of {} is the same as ANYTHING
					return true;
				} else if (other.wildcard == WildCard.ANYTHING) {
					return true;
				} else if (other.wildcard == WildCard.NOTHING) {
					return false;
				} else if (other.wildcard == WildCard.NONE) {
					return !wildcard_data.contains(other.getValue());
				} else {
					return equals(other);
				}
			default:
				throw new RuntimeException("BUG! unimplemented wildcard " + wildcard);
			}
		}
	}
}
