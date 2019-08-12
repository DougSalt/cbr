/**
 * 
 */


/**
 * @author gp40285
 *
 */
public class StringFeature extends ComparableFeature<String> {

	/**
	 * @param name
	 * @param type
	 */
	public StringFeature(String name, Class<String> type) {
		super(name, type);
	}

	@Override
	public StringFeatureValue getValue(String value) {
		return new StringFeatureValue(value, this);
	}

	@Override
	public StringFeatureValue getQuery(String op, String value) {
		if(op.equals("RE")) {
			return new StringFeatureValue(value, this, true);
		}
		else {
			return (StringFeatureValue)super.getQuery(op, value);
		}
	}

}
