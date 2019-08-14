public class ComparableFeature<T extends Comparable<T>> extends Feature<T> {
	/**
	 * @param name
	 * @param type
	 */
	public ComparableFeature(String name, Class<T> type) {
		super(name, type);
	}

	@Override
	public ComparableFeatureValue<T> getValue(T value) {
		return new ComparableFeatureValue<T>(value, this);
	}

	@Override
	public ComparableFeatureValue<T> getQuery(String op, T value) {
		ComparableFeatureValue.Op opop = ComparableFeatureValue.Op.valueOf(op);
		if (opop == null) {
			throw new IllegalArgumentException(
					"Invalid query operator for value of type "
							+ value.getClass() + ": " + op);
		}
		return new ComparableFeatureValue<T>(value, this, opop);
	}
}
