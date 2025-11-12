import org.nlogo.core.ExtensionObject;

/**
 * 
 */

/**
 * @author gary
 *
 */
public class NetLogoPartialComparison implements ExtensionObject {
	public static String typeName = "poc";
	private PartiallyOrderedComparison comparison;
	private final long id;
	private static long next = 0L;
	public static final NetLogoPartialComparison EQ = new NetLogoPartialComparison(PartiallyOrderedComparison.EQUAL);
	public static final NetLogoPartialComparison GT = new NetLogoPartialComparison(PartiallyOrderedComparison.GREATER_THAN);
	public static final NetLogoPartialComparison LT = new NetLogoPartialComparison(PartiallyOrderedComparison.LESS_THAN);
	public static final NetLogoPartialComparison IN = new NetLogoPartialComparison(PartiallyOrderedComparison.INCOMPARABLE);
	
	private NetLogoPartialComparison(PartiallyOrderedComparison comparison) {
		this.comparison = comparison;
		id = next();
	}
	
	private static final synchronized long next() {
		return ++next;
	}
	
	public boolean isEqual() {
		return comparison == PartiallyOrderedComparison.EQUAL;
	}
	
	public boolean isLess() {
		return comparison == PartiallyOrderedComparison.LESS_THAN;
	}
	
	public boolean isMore() {
		return comparison == PartiallyOrderedComparison.GREATER_THAN;
	}
	
	public boolean isComparable() {
		return comparison != PartiallyOrderedComparison.INCOMPARABLE;
	}
	
	public boolean isIncomparable() {
		return comparison == PartiallyOrderedComparison.INCOMPARABLE;
	}
	
	public boolean isLessOrEqual() {
		return isEqual() || isLess();
	}
	
	public boolean isMoreOrEqual() {
		return isEqual() || isMore();
	}
	
	public boolean isLessOrMore() {
		return isLess() || isMore();
	}

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
			buff.append(comparison.toString());
		}

		return buff.toString();
	}

	@Override
	public String getExtensionName() {
		return CBRExtension.EXTENSION_NAME;
	}

	@Override
	public String getNLTypeName() {
		return typeName;
	}

	@Override
	public boolean recursivelyEqual(Object obj) {
		if(obj instanceof NetLogoPartialComparison) {
			return comparison == ((NetLogoPartialComparison)obj).comparison;
		}
		else {
			return false;
		}
	}

}
