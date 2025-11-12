/**
 * 
 */

/**
 * @author gary
 *
 */
public class DefaultLambda implements PartiallyComparableLambda<Case> {
	
	private PartiallyOrderedComparison.Hook<Case> hook;
	
	public DefaultLambda() {
		hook = null;
	}
	
	public DefaultLambda(PartiallyOrderedComparison.Hook<Case> hook) {
		this.hook = hook;
	}
	
	public PartiallyOrderedComparison cmp(Case src, Case obj, Case referent) {
		if(hook != null) {
			return hook.cmp(src, obj, referent);
		}
		else {
			return src.relativeCompareTo(obj, referent);
		}
	}

}
