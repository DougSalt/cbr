/**
 * 
 */


/**
 * @author gp40285
 *
 */
public class StringFeatureValue extends ComparableFeatureValue<String> {

	protected final boolean RE;

	/**
	 * @param value
	 * @param feature
	 */
	protected StringFeatureValue(String value, Feature<String> feature) {
		super(value, feature);
		RE = false;
	}

	/**
	 * @param value
	 * @param feature
	 * @param op
	 */
	public StringFeatureValue(String value, Feature<String> feature,
			ComparableFeatureValue.Op op) {
		super(value, feature, op);
		RE = false;
	}
	
	public StringFeatureValue(String value, Feature<String> feature, boolean RE) {
		super(value, feature, ComparableFeatureValue.Op.EQ);
		this.RE = RE;
	}
	
	public boolean matches(StringFeatureValue other) {
		if(!query && other.RE) {
			return value.matches(other.getValue());
		}
		else {
			return super.matches(other);
		}
	}

}
