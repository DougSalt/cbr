/**
 * 
 */
import java.util.*;

import org.nlogo.core.ExtensionObject;
import org.nlogo.core.LogoList;


/**
 * @author GP40285
 *
 */
public class NetLogoFeatureSet extends FeatureSet implements ExtensionObject {
	private boolean locked;
	private static Map<String, NetLogoFeatureSet> featureSets = new HashMap<String, NetLogoFeatureSet>();
	
	public static final String typeName = "feature-set";

	/**
	 * 
	 */
	public NetLogoFeatureSet() {
		locked = false;
	}

	public NetLogoFeatureSet(String value) throws ClassNotFoundException, CaseBaseException {
		this(FeatureSet.parseString(value));
	}
	
	public static NetLogoFeatureSet manifest(LogoList arg) {
		NetLogoFeatureSet featureSet = new NetLogoFeatureSet();

		try
		{
			for (int i = 0; i < arg.size(); i++)
			{
				Feature<?> feature = Feature.fromString(arg.get(i).toString());
				featureSet.addFeature(feature);
			}

			return featureSet;
		}
		catch (CaseBaseException e)
		{
			e.printStackTrace();
		}

		return null;
	}
	
	public void lock() {
		locked = true;
		featureSets.put(toString(), this);
	}

	@Override
	public void addFeature(Feature<?> feature) throws CaseBaseException {
		if(locked) {
			throw new CaseBaseException("Attempt to add feature " + feature + " to locked feature set " + this);
		}
		super.addFeature(feature);
	}

	/**
	 * @param features
	 * @throws CaseBaseException
	 */
	public NetLogoFeatureSet(Feature<?>... features) throws CaseBaseException {
		super(features);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param features
	 * @throws CaseBaseException
	 */
	public NetLogoFeatureSet(Iterable<Feature<?>> features) throws CaseBaseException {
		super(features);
		// TODO Auto-generated constructor stub
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
