/**
 * 
 */


/**
 * @author gp40285
 *
 */
public class ComparableFeatureValue<T extends Comparable<T>> extends Feature<T>.Value {
	public enum Op {
		GT, LT, GE, LE, EQ, NE;
	}
	
	protected final boolean query;
	protected final Op op;
	

	protected ComparableFeatureValue(
			T value, Feature<T> feature) {
		this(value, feature, false, Op.EQ);
	}
	
	public ComparableFeatureValue(T value, Feature<T> feature, Op op) {
		this(value, feature, true, op);
	}
	
	private ComparableFeatureValue(T value, Feature<T> feature, boolean query, Op op) {
		feature.super(value, feature);
		this.query = query;
		this.op = op;
	}

	public boolean matches(ComparableFeatureValue<T> other) {
		if(!query) {
			int cmp = value.compareTo(other.getValue());
			switch(((ComparableFeatureValue<?>)other).op) {
			case GT:
				return cmp > 0;
			case LT:
				return cmp < 0;
			case GE:
				return cmp >= 0;
			case LE:
				return cmp <= 0;
			case EQ:
				return cmp == 0;
			case NE:
				return cmp != 0;
			default:
				throw new RuntimeException("Panic!");
			}
		}
		else {
			return super.matches(other);
		}
	}
}
