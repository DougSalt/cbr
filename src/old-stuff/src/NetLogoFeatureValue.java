/**
 * 
 */
import org.nlogo.core.ExtensionObject;
import org.nlogo.core.LogoList;

/**
 * @author GP40285
 *
 */
public class NetLogoFeatureValue extends Feature<Object>.Value implements ExtensionObject {

	public static final String typeName = "feature-value";

	protected NetLogoFeatureValue(Object value, NetLogoFeature feature) {
		feature.super(value, feature);
	}

	public NetLogoFeatureValue(Feature<?>.Value feature) {
		this(feature.getValue(), new NetLogoFeature(feature.getFeature()));
	}

	public NetLogoFeatureValue(String value) throws ClassNotFoundException {
		this(Feature.parseValueString(value));
	}

	public LogoList asList() {
		LogoList list = LogoList.Empty();

		list = list.lput(getFeature());
		list = list.lput(getValue());

		return list;
	}

	/**
	 * <!-- override getFeature -->
	 *
	 * @see uk.ac.hutton.caber.Feature.Value#getFeature()
	 */
	@Override
	public NetLogoFeature getFeature() {
		return (NetLogoFeature) super.getFeature();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nlogo.core.ExtensionObject#dump(boolean, boolean, boolean)
	 */
	@Override
	public String dump(boolean readable, boolean exporting, boolean reference) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nlogo.core.ExtensionObject#getExtensionName()
	 */
	@Override
	public String getExtensionName() {
		return CBRExtension.EXTENSION_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nlogo.core.ExtensionObject#getNLTypeName()
	 */
	@Override
	public String getNLTypeName() {
		return typeName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nlogo.core.ExtensionObject#recursivelyEqual(java.lang.Object)
	 */
	@Override
	public boolean recursivelyEqual(Object obj) {
		return super.equals(obj);
	}

}
