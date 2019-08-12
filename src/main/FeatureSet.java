/**
 * FeatureSet.java, uk.ac.hutton.caber
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


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <!-- FeatureSet -->
 * 
 * A FeatureSet is a specific set of {@link Feature}s.
 * 
 * @author Gary Polhill
 */
public class FeatureSet implements Collection<Feature<?>> {

	/**
	 * Map of feature name to feature
	 */
	private Map<String, Feature<?>> featureMap;

	/**
	 * Order in which feature names added to FeatureSet
	 */
	private List<String> order;

	/**
	 * <!-- FeatureSet constructor -->
	 * 
	 * Build an empty FeatureSet
	 */
	public FeatureSet() {
		featureMap = new HashMap<String, Feature<?>>();
		order = new ArrayList<String>();
	}

	private static final String UNASSIGNED_FEATURE_STRING = " (unassigned)";

	/**
	 * <!-- FeatureSet constructor -->
	 * 
	 * Build a FeatureSet from a varargs collection of existing Features
	 * 
	 * @param features
	 * @throws FeatureSetException
	 */
	public FeatureSet(Feature<?>... features) throws CaseBaseException {
		this();

		for(Feature<?> feature : features) {
			addFeature(feature);
		}
	}

	/**
	 * <!-- FeatureSet constructor -->
	 * 
	 * Build a FeatureSet from anything Iterable. Note that the ordering may be
	 * unpredictable depending on the type of the Iterable.
	 * 
	 * @param features
	 * @throws FeatureSetException
	 */
	public FeatureSet(Iterable<Feature<?>> features) throws CaseBaseException {
		this();

		for(Feature<?> feature : features) {
			addFeature(feature);
		}
	}

	/**
	 * <!-- addFeature -->
	 * 
	 * Add a Feature to the FeatureSet. The Feature must not be null.
	 * 
	 * @param feature
	 *          the feature to add
	 * @throws FeatureSetException
	 *           if you add a Feature with the same name as one already added
	 */
	public void addFeature(Feature<?> feature) throws CaseBaseException {
		if(feature == null) {
			throw new IllegalArgumentException("Attempt to add null Feature to FeatureSet");
		}
		if(featureMap.containsKey(feature.getName())) {
			throw new CaseBaseException("FeatureSet " + this + " already contains a Feature with name \"" + feature.getName() + "\"");
		}
		featureMap.put(feature.getName(), feature);
		order.add(feature.getName());
	}

	/**
	 * <!-- addFeature -->
	 * 
	 * Convenience method to add a feature with separately creating the feature
	 * 
	 * @param name
	 *          Name of the feature
	 * @param type
	 *          Type of the feature
	 * @throws CaseBaseException
	 */
	public <T> void addFeature(String name, Class<T> type) throws CaseBaseException {
		addFeature(new Feature<T>(name, type));
	}

	/**
	 * <!-- hasFeature -->
	 * 
	 * @param name
	 *          name of a feature
	 * @return <code>true</code> if the feature name is present.
	 */
	public boolean hasFeature(String name) {
		if(name == null) return false;
		return featureMap.containsKey(name);
	}

	/**
	 * <!-- hasFeature -> boolean -->
	 *
	 * @param f
	 * @return <code>true</code> if this FeatureSet contains a feature that
	 *         <code>equals(f)</code>
	 */
	public boolean hasFeature(Feature<?> f) {
		if(f == null) return false;
		if(featureMap.containsKey(f.getName())) {
			return f.equals(featureMap.get(f.getName()));
		}
		else {
			return false;
		}
	}

	/**
	 * <!-- getFeature -->
	 * 
	 * @param name
	 *          name of a feature
	 * @return feature with that name
	 * @throws FeatureSetException
	 *           if the feature name is not present
	 */
	public Feature<?> getFeature(String name) throws CaseBaseException {
		if(!hasFeature(name)) {
			throw new CaseBaseException("FeatureSet " + this + " does not have a Feature named \"" + name + "\"");
		}
		return featureMap.get(name);
	}

	/**
	 * <!-- getFeature -->
	 * 
	 * Return a feature by the index
	 * 
	 * @param ix
	 *          index (starting at 0)
	 * @return the feature
	 * @throws FeatureSetException
	 *           if the feature is out of the range [0, n[ where n is the
	 *           number of features
	 */
	public Feature<?> getFeature(int ix) throws CaseBaseException {
		if(ix < 0) {
			throw new CaseBaseException("Invalid negative index on FeatureSet ordering (" + ix + ")");
		}
		if(ix >= order.size()) {
			throw new CaseBaseException("Index " + ix + " out of range [0, " + (order.size() - 1) + "] of FeatureSet " + this);
		}
		try {
			return getFeature(order.get(ix));
		}
		catch(CaseBaseException e) {
			throw new RuntimeException("BUG! " + e);
		}
	}

	public Feature<?> getFeature(Feature<?> f) throws CaseBaseException {
		if(!hasFeature(f)) {
			throw new CaseBaseException("FeatureSet has no feature " + f);
		}
		else {
			return featureMap.get(f.getName());
		}
	}

	/**
	 * <!-- hasType -->
	 * 
	 * Check if a feature has a particular type, accessed by name
	 * 
	 * @param name
	 *          feature name
	 * @param type
	 *          type
	 * @return <code>true</code> if the named feature is assignable from the
	 *         specified type
	 * @throws CaseBaseException
	 *           if there is no feature with that name
	 */
	public boolean hasType(String name, Class<?> type) throws CaseBaseException {
		return getFeature(name).getType().isAssignableFrom(type);
	}

	/**
	 * <!-- hasType -->
	 * 
	 * Check if a feature has a particular type, accessed by index
	 * 
	 * @param ix
	 *          feature index (starting at 0)
	 * @param type
	 *          type
	 * @return <code>true</code> if the (ix + 1)<sup>th</sup> feature is
	 *         assignable from the specified type
	 * @throws CaseBaseException
	 *           if ix is out of range
	 */
	public boolean hasType(int ix, Class<?> type) throws CaseBaseException {
		return getFeature(ix).getType().isAssignableFrom(type);
	}

	public Class<?> getType(String name) throws CaseBaseException {
		return getFeature(name).getType();
	}

	public Class<?> getType(int ix) throws CaseBaseException {
		return getFeature(ix).getType();
	}

	/**
	 * <!-- setValues -->
	 * 
	 * Return a populated feature set value array using the arguments
	 * 
	 * @param objects
	 *          the values to populate the feature set with
	 * @return the FeatureSet.Value
	 * @throws CaseBaseException
	 */
	public FeatureSet.Value setValues(Object... objects) throws CaseBaseException {
		if(objects.length != order.size()) {
			throw new IllegalArgumentException(
					"Mismatched value set size (" + objects.length + ") and number of features (" + order.size() + ")");
		}

		FeatureSet.Value value = new FeatureSet.Value(this);

		for(int i = 0; i < objects.length; i++) {
			value.set(i, objects[i]);
		}

		return value;
	}

	/**
	 * <!-- setValues -->
	 * 
	 * Allow values to be set from a list.
	 * 
	 * @param objects
	 * @return
	 * @throws CaseBaseException
	 */
	public FeatureSet.Value setValues(List<Object> objects) throws CaseBaseException {
		if(objects.size() != order.size()) {
			throw new IllegalArgumentException(
					"Mismatched value set size (" + objects.size() + ") and number of features (" + order.size() + ")");
		}

		FeatureSet.Value value = new FeatureSet.Value(this);

		Iterator<String> nameIX = order.iterator();
		Iterator<Object> valueIX = objects.iterator();

		while(nameIX.hasNext() && valueIX.hasNext()) {
			value.set(nameIX.next(), valueIX.next());
		}

		return value;
	}

	/**
	 * <!-- getEmptyValues() -->
	 * 
	 * @return an unpopulated FeatureSet.Value
	 */
	public FeatureSet.Value getEmptyValues() {
		return new FeatureSet.Value(this);
	}

	@Override
	public boolean equals(Object other) {
		if(other == null) {
			return false;
		}
		else if(other instanceof FeatureSet) {
			FeatureSet other_fs = (FeatureSet)other;

			if(size() != other_fs.size()) return false;

			for(int i = 0; i < size(); i++) {
				try {
					if(!getFeature(i).equals(other_fs.getFeature(i))) return false;
				}
				catch(CaseBaseException e) {
					throw new RuntimeException("BUG! " + e);
				}
			}

			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * <!-- Value -->
	 * 
	 * Nested class to get the values of a FeatureSet.
	 * 
	 * @author Gary Polhill
	 */
	/**
	 * <!-- Value -->
	 *
	 * @author GP40285
	 *
	 */
	public class Value implements Iterable<Feature<?>.Value> {

		/**
		 * Map of Feature to Feature.Value
		 */
		private Map<Feature<?>, Feature<?>.Value> values;

		/**
		 * Reference back to the FeatureSet this FeatureSet.Value is
		 */
		private final FeatureSet features;

		/**
		 * <!-- Value constructor -->
		 * 
		 * Private constructor
		 * 
		 * @param features
		 *          FeatureSet this is a value of
		 */
		protected Value(FeatureSet features) {
			values = new HashMap<Feature<?>, Feature<?>.Value>();
			this.features = features;
		}

		/**
		 * <!-- size -->
		 *
		 * @return The size of the feature value set.
		 */
		public int size() {
			return features.size();
		}

		/**
		 * <!-- getFeatureSet -->
		 * 
		 * @return the feature set
		 */
		public FeatureSet getFeatureSet() {
			return features;
		}

		/**
		 * <!-- set -->
		 * 
		 * Set a feature to a value. This will overwrite any existing value.
		 * 
		 * @param name
		 *          name of the feature to set
		 * @param data
		 *          value to set it to
		 * @throws CaseBaseException
		 *           if the Feature doesn't exist, or doesn't have the right
		 *           type
		 */
		public <T> void set(String name, T data) throws CaseBaseException {
			set(features.getFeature(name), data);
		}

		/**
		 * <!-- set -->
		 * 
		 * Set a feature to a value. This will overwrite any existing value.
		 * 
		 * @param ix
		 *          number of the feature to set
		 * @param data
		 *          value to set it to
		 * @throws CaseBaseException
		 *           if the Feature number is out of range, or doesn't have
		 *           the right type
		 */
		public <T> void set(int ix, T data) throws CaseBaseException {
			set(features.getFeature(ix), data);
		}

		/**
		 * <!-- set -->
		 * 
		 * Method called by {@link #set(String, T)} and {@link #set(int, T)}
		 * 
		 * @param f
		 *          feature to set
		 * @param data
		 *          data to set it to
		 * @throws CaseBaseException
		 *           if the Feature has the wrong type
		 */
		private <T> void set(Feature<?> f, T data) throws CaseBaseException {
			if(!features.hasFeature(f)) {
				throw new CaseBaseException("FeatureSet does not contain the feature " + f.getName());
			}
			if(f.getType().isAssignableFrom(data.getClass())) {
				@SuppressWarnings("unchecked")
				Feature<T> fT = (Feature<T>)f;
				values.put(features.getFeature(f.getName()), fT.getValue(data));
			}
			else {
				throw new CaseBaseException("Feature \"" + f.getName() + "\" has type " + f.getType().getName()
						+ ", which is not assignable from type " + data.getClass().getName() + " of data " + data);
			}
		}

		/**
		 * <!-- isSet -> boolean -->
		 *
		 * @param f
		 * @return <code>true</code> if feature <code>f</code> is set in this
		 *         FeatureSet.Value
		 */
		public boolean isSet(Feature<?> f) {
			if(features.hasFeature(f)) {
				try {
					return values.containsKey(features.getFeature(f));
				}
				catch(CaseBaseException e) {
					throw new RuntimeException("BUG! Have checked that FeatureSet contains feature " + f);
				}
			}
			else {
				return false;
			}
		}

		/**
		 * <!-- get -->
		 * 
		 * Get the value of a feature with the specified name
		 * 
		 * @param name
		 *          name of the Feature
		 * @param type
		 *          expected datatype as an array (of size 0 -- just do
		 *          <code>new String[0]</code>, for example
		 * @return the value of the Feature
		 * @throws CaseBaseException
		 *           if the Feature doesn't exist or hasn't had its value set
		 */
		public <T> T get(String name, T[] type) throws CaseBaseException {
			Feature<?> f = features.getFeature(name);
			return get(f, type);
		}

		/**
		 * <!-- get -->
		 * 
		 * Get the value of the (ix + 1)<sup>th</sup> feature
		 * 
		 * @param ix
		 *          index of the feature (starting at 0)
		 * @param type
		 *          expected datatype as an array (of size 0 -- just do
		 *          <code>new String[0]</code>, for example)
		 * @return the value of the feature
		 * @throws CaseBaseException
		 *           if the index is out of range or the feature has not been
		 *           given a value
		 */
		public <T> T get(int ix, T[] type) throws CaseBaseException {
			Feature<?> f = features.getFeature(ix);
			return get(f, type);
		}

		/**
		 * <!-- get -->
		 * 
		 * Get the value of a specific feature
		 * 
		 * @param f
		 *          the Feature to get the value of
		 * @return the value of the Feature
		 * @throws CaseBaseException
		 *           if the Feature has not been given a value
		 */
		public <T> T get(Feature<T> f) throws CaseBaseException {
			if(values.containsKey(features.getFeature(f))) {
				@SuppressWarnings("unchecked")
				Feature<T>.Value v = (Feature<T>.Value)values.get(f);
				return v.getValue();
			}
			else {
				throw new CaseBaseException("Feature " + f + " has not been assigned a value");
			}
		}

		/**
		 * <!-- get -->
		 * 
		 * Private accessor to a Feature.Value called by {@link #get(String, T[])}
		 * and {@link #get(int, T[])}
		 * 
		 * @param f
		 *          Feature to get the value of
		 * @param type
		 *          Expected type of the feature
		 * @return feature value
		 * @throws CaseBaseException
		 *           if feature not given a value
		 */
		private <T> T get(Feature<?> f, T[] type) throws CaseBaseException {
			if(values.containsKey(f)) {
				return get(values.get(f), type);
			}
			else {
				throw new CaseBaseException("Feature " + f + " has not been assigned a value");
			}
		}

		/**
		 * <!-- get -->
		 * 
		 * Private accessor used by {@link get(Feature, T)}. Checks that the
		 * type is correct before returning a value
		 * 
		 * @param v
		 *          Feature.Value to get the value of
		 * @param type
		 *          Expected type
		 * @return value of the feature
		 * @throws CaseBaseException
		 *           if the expected type is not assignable from the Feature
		 *           type.
		 */
		private <T> T get(Feature<?>.Value v, T[] type) throws CaseBaseException {
			if(type.getClass().getComponentType().isAssignableFrom(v.getFeature().getType())) {
				@SuppressWarnings("unchecked")
				Feature<T>.Value vT = (Feature<T>.Value)v;
				return vT.getValue();
			}
			else {
				throw new CaseBaseException("Feature \"" + v.getFeature().getName() + "\" has type " + v.getFeature().getType().getName()
						+ ", which cannot be used to assign to data of type " + type.getClass().getComponentType().getName());
			}
		}

		/**
		 * <!-- getValue -> Feature<?>.Value -->
		 *
		 * Method to access the Feature.Value of the specified Feature
		 * 
		 * @param f
		 * @return
		 */
		protected Feature<?>.Value getValue(Feature<?> f) {
			if(!features.hasFeature(f)) return null;

			try {
				f = features.getFeature(f);
			}
			catch(CaseBaseException e) {
				throw new RuntimeException("BUG! have already checked that feature " + f + " is in this FeatureSet");
			}
			if(values.containsKey(f)) return values.get(f);
			return null;
		}

		/**
		 * <!-- matches -->
		 * 
		 * A feature set value matches a feature value if it has a value for
		 * the feature of the feature value (by name or ID) that matches the
		 * value.
		 *
		 * @param value
		 * @return <code>true</code> if the feature set value matches
		 */
		public boolean matches(Feature<?>.Value value) {
			if(value == null) return false;
			Feature<?> whichFeature = value.getFeature();
			if(!values.containsKey(whichFeature)) {
				if(features.hasFeature(value.getFeature())) {
					try {
						whichFeature = features.getFeature(value.getFeature());
					}
					catch(CaseBaseException e) {
						// We've tested if it has the feature already, so getFeature()
						// should not have thrown an exception
						throw new RuntimeException("BUG! " + e);
					}
					if(!values.containsKey(whichFeature)) {
						// No entry for this feature yet
						return false;
					}
				}
				else {
					return false;
				}
			}

			return values.get(whichFeature).matches(value);
		}

		/**
		 * <!-- matches -> boolean -->
		 * 
		 * Convenience method to match several feature values at once
		 *
		 * @param values
		 * @return
		 */
		public boolean matches(Iterable<Feature<?>.Value> values) {
			for(Feature<?>.Value value : values) {
				if(!matches(value)) return false;
			}
			return true;
		}

		/**
		 * <!-- matches -> boolean -->
		 * 
		 * Match another FeatureSet.Value -- all that is required
		 * is that all values that are set in other match the
		 * corresponding feature in this FeatureSet.Value. Hence,
		 * if A matches B, B not necessarily matches A.
		 *
		 * @param other
		 * @return
		 */
		public boolean matches(FeatureSet.Value other) {
			for(Feature<?> feature : other.features) {
				if(other.isSet(feature) && !matches(other.getValue(feature))) return false;
			}
			return true;
		}

		/**
		 * <!-- allSet -->
		 * 
		 * Check if all features have been assigned values
		 * 
		 * @return <code>true</code> if all features have been assigned values.
		 */
		public boolean allSet() {
			for(Feature<?> f : features) {
				if(!values.containsKey(f)) return false;
			}
			return true;
		}

		@Override
		public String toString() {
			StringBuffer str = new StringBuffer("{");

			for(Feature<?> f : features) {
				if(str.length() > 1) str.append(", ");
				str.append(f.toString());
				if(values.containsKey(f)) {
					str.append(" = ");
					str.append(values.get(f).toString());
				}
				else {
					str.append(UNASSIGNED_FEATURE_STRING);
				}
			}

			str.append("}");

			return str.toString();
		}

		@Override
		public boolean equals(Object other) {
			if(other == null) {
				return false;
			}
			else if(other instanceof FeatureSet.Value) {
				FeatureSet.Value other_fsv = (FeatureSet.Value)other;

				if(!features.equals(other_fsv.features)) return false;

				for(int i = 0; i < features.size(); i++) {
					try {
						Feature<?> my_feature = features.getFeature(i);
						Feature<?> other_feature = other_fsv.features.getFeature(i);

						if(values.containsKey(my_feature)) {
							if(other_fsv.values.containsKey(other_feature)) {
								if(!values.get(my_feature).equals(other_fsv.get(other_feature))) {
									// We've both got values for this feature; they're different
									return false;
								}
							}
							else {
								// I have a value for this feature; the other doesn't
								return false;
							}
						}
						else if(other_fsv.values.containsKey(other_feature)) {
							// I don't have a value for this feature; the other does
							return false;
						}

					}
					catch(CaseBaseException e) {
						// Checking the equality of the two feature sets first above
						// should ensure we don't get an exception
						throw new RuntimeException("BUG! " + e);
					}
				}

				return true;
			}
			else {
				// Other isn't a feature set value
				return false;
			}
		}

		@Override
		public Iterator<Feature<?>.Value> iterator() {
			LinkedList<Feature<?>.Value> fl = new LinkedList<Feature<?>.Value>();
			for(Feature<?> f : features) {
				if(values.containsKey(f)) {
					fl.add(values.get(f));
				}
				else {
					fl.add(null);
				}
			}
			return fl.iterator();
		}
	}

	@Override
	public Iterator<Feature<?>> iterator() {
		LinkedList<Feature<?>> fl = new LinkedList<Feature<?>>();

		for(String name : order) {
			fl.addLast(featureMap.get(name));
		}

		return fl.iterator();
	}

	@Override
	public boolean add(Feature<?> f) {
		try {
			addFeature(f);
			return true;
		}
		catch(CaseBaseException e) {
			return false;
		}
	}

	@Override
	public boolean addAll(Collection<? extends Feature<?>> c) {
		for(Feature<?> f : c) {
			if(!add(f)) return false;
		}
		return true;
	}

	@Override
	public void clear() {
		featureMap.clear();
		order.clear();
	}

	@Override
	public boolean contains(Object o) {
		if(o instanceof Feature<?>) {
			Feature<?> f = (Feature<?>)o;
			if(featureMap.containsKey(f.getName()) && featureMap.get(f.getName()).equals(f)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for(Object o : c) {
			if(!contains(o)) return false;
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return order.isEmpty();
	}

	@Override
	public boolean remove(Object o) {
		if(o instanceof Feature<?>) {
			Feature<?> f = (Feature<?>)o;
			if(featureMap.containsKey(f.getName()) && order.remove(f.getName())) {
				featureMap.remove(f.getName());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for(Object o : c) {
			if(!remove(o)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		for(Feature<?> f : featureMap.values()) {
			if(!c.contains(f)) {
				if(!remove(f)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int size() {
		return order.size();
	}

	@Override
	public Feature<?>[] toArray() {
		Feature<?> arr[] = new Feature<?>[order.size()];

		int i = 0;
		for(Feature<?> f : this) {
			arr[i] = f;
			i++;
		}
		return arr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {

		if(a == null) {
			throw new NullPointerException();
		}
		if(Feature.class.isAssignableFrom(a.getClass().getComponentType())) {
			if(a.length >= order.size()) {
				int i = 0;
				for(Feature<?> f : this) {
					a[i] = (T)f;
					i++;
				}
				return a;
			}
			else {
				return (T[])toArray();
			}
		}
		else {
			throw new ArrayStoreException();
		}
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer("{");
		for(Feature<?> f : this) {
			if(str.length() > 1) str.append(", ");
			str.append(f.toString());
		}
		str.append("}");
		return str.toString();
	}

	public static FeatureSet parseString(String fsstr) throws CaseBaseException {
		if(fsstr.startsWith("{") && fsstr.endsWith("}")) {
			fsstr = fsstr.substring(1, fsstr.length() - 1);
			String[] parts = fsstr.split(", ");
			FeatureSet fs = new FeatureSet();

			for(String part : parts) {
				fs.add(Feature.fromString(part));
			}

			return fs;
		}
		else {
			throw new CaseBaseException(
					"Invalid feature set value string \"" + fsstr + "\" -- must start with \"{\" and end with \"}\"");
		}
	}

	public static FeatureSet.Value parseValueString(String fsvstr) throws CaseBaseException, ClassNotFoundException {
		if(fsvstr.startsWith("{") && fsvstr.endsWith("}")) {
			fsvstr = fsvstr.substring(1, fsvstr.length() - 1);
			String[] parts = fsvstr.split(", ");
			FeatureSet fs = new FeatureSet();
			FeatureSet.Value fsv = fs.getEmptyValues();
			for(int i = 0; i < parts.length; i++) {
				String fstr;
				String vstr = null;
				if(parts[i].indexOf(" = ") >= 0) {
					String[] fvstr = parts[i].split(" = ");
					fstr = fvstr[0];
					vstr = fvstr[1];
				}
				else if(parts[i].endsWith(UNASSIGNED_FEATURE_STRING)) {
					fstr = parts[i].substring(0, parts[i].length() - UNASSIGNED_FEATURE_STRING.length());
				}
				else {
					throw new CaseBaseException("Invalid feature set value string \"" + fsvstr + "\" -- part \"" + parts[i]
							+ "\" must contain \" = \" or end with \"" + UNASSIGNED_FEATURE_STRING + "\"");
				}
				Feature<?> f = Feature.fromString(fstr);
				fs.add(f);
				if(vstr != null) {
					fsv.set(f, StringInitializer.parseNewInstance(vstr));
				}
			}
			return fsv;
		}
		else {
			throw new CaseBaseException(
					"Invalid feature set value string \"" + fsvstr + "\" -- must start with \"{\" and end with \"}\"");
		}
	}

}
