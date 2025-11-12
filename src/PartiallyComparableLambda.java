/**
 * 
 */

/**
 * @author gary
 *
 */
public interface PartiallyComparableLambda<T extends PartiallyComparable<T>> {
	public PartiallyOrderedComparison cmp(T src, T obj, T ref);
}
