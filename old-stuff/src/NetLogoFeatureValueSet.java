import org.nlogo.api.ExtensionException;
import org.nlogo.core.ExtensionObject;
import org.nlogo.core.LogoList;

/**
 * @author GP40285
 *
 */
public class NetLogoFeatureValueSet extends FeatureSet.Value implements ExtensionObject {

	private final long id;
	private static long next = 0;

	private static final int TUPLE_SIZE = 2;
	
	public static final String typeName = "feature-set-value";

	/**
	 * @param features
	 */
	public NetLogoFeatureValueSet(NetLogoFeatureSet features) {
		features.super(features);
		id = next();
	}
	
	public static NetLogoFeatureValueSet manifest(LogoList value) throws ExtensionException {
		LogoList[] untuple = detuplise(value);
		
		NetLogoFeatureSet features = NetLogoFeatureSet.manifest(untuple[0]);
		
		NetLogoFeatureValueSet values = new NetLogoFeatureValueSet(features);
		
		values.set(untuple[1]);
		
		return values;
	}
	
	public static NetLogoFeatureValueSet manifest(LogoList value, NetLogoFeatureSet features) throws ExtensionException {
		NetLogoFeatureValueSet values = new NetLogoFeatureValueSet(features);
		
		values.set(value);
		
		return values;
	}

	private static final long next() {
		return ++next;
	}
	
	public static LogoList[] detuplise(LogoList list_of_tuples) {
		LogoList[] result = new LogoList[TUPLE_SIZE];
		
		for(int i = 0; i < TUPLE_SIZE; i++) {
			result[i] = LogoList.Empty();
		}
		
		for(int i = 0; i < list_of_tuples.length(); i++) {
			Object entry = list_of_tuples.get(i);
			
			if(entry instanceof LogoList) {
				LogoList tuple = (LogoList)entry;
				
				if(tuple.length() == TUPLE_SIZE) {
					for(int j = 0; j < TUPLE_SIZE; j++) {
						result[j] = result[j].lput(tuple.get(j));
					}
				}
				else {
					// TODO Error
				}
			}
			else {
				// TODO Error
			}
		}
		
		return result;
	}

	public LogoList asLogoList() {
		LogoList list = LogoList.Empty();
		for(Feature<?>.Value value: this) {
			LogoList tuple = LogoList.Empty();
			tuple.lput((NetLogoFeature)value.getFeature());
			tuple.lput(value.getValue());
			
			list.lput(tuple);
		}
		return list;
	}

	public void set(LogoList values) throws ExtensionException {
		if(values.length() != size()) {
			throw new ExtensionException("Attempt to set features from list of incompatible size " + values);
		}
		for(int i = 0; i < size(); i++) {
			try {
				set(i, values.get(i));
			}
			catch(CaseBaseException e) {
				throw new RuntimeException("BUG! " + e);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.nlogo.core.ExtensionObject#dump(boolean, boolean, boolean)
	 */
	@Override
	public String dump(boolean readable, boolean exporting, boolean reference) {
		StringBuilder buff = new StringBuilder();
		if(exporting) {
			buff.append(id);
			if(!reference) {
				buff.append(": ");
			}
		}
		if(!(reference && exporting)) {
			buff.append(this.toString());
		}

		return buff.toString();
	}

	/** 
	 * <!-- override getFeatureSet -->
	 *
	 * @see uk.ac.hutton.caber.FeatureSet.Value#getFeatureSet()
	 */
	@Override
	public NetLogoFeatureSet getFeatureSet() {
		return (NetLogoFeatureSet)super.getFeatureSet();
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
