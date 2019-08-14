/**
 * 
 */
import java.util.HashMap;
import java.util.Map;

import org.nlogo.core.ExtensionObject;

/**
 * @author GP40285
 *
 */
public class NetLogoFeature extends Feature<Object> implements ExtensionObject {
	public static Map<String, NetLogoFeature> features = new HashMap<String, NetLogoFeature>();
	public static final String typeName = "feature";
	/**
	 * @param name
	 * @param type
	 */
	public NetLogoFeature(String name) {
		super(name, Object.class);
	}
	
	public <T> NetLogoFeature(Feature<T> feature) {
		super(feature.getName(), Object.class);
	}

	/** 
	 * <!-- override getQuery -->
	 *
	 * @see uk.ac.hutton.caber.Feature#getQuery(java.lang.String, java.lang.Object)
	 */
	@Override
	public NetLogoFeatureValue getQuery(String op, Object value) {
		return (NetLogoFeatureValue)super.getQuery(op, value);
	}

	/* (non-Javadoc)
	 * @see org.nlogo.core.ExtensionObject#dump(boolean, boolean, boolean)
	 */
	@Override
	public String dump(boolean readable, boolean exporting, boolean reference) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.nlogo.core.ExtensionObject#getExtensionName()
	 */
	@Override
	public String getExtensionName() {
		return CBRExtension.EXTENSION_NAME;
	}

	/* (non-Javadoc)
	 * @see org.nlogo.core.ExtensionObject#getNLTypeName()
	 */
	@Override
	public String getNLTypeName() {
		return typeName;
	}

	/* (non-Javadoc)
	 * @see org.nlogo.core.ExtensionObject#recursivelyEqual(java.lang.Object)
	 */
	@Override
	public boolean recursivelyEqual(Object obj) {
		return super.equals(obj);
	}

}
