import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;



/**
 * <!-- ComparisonResult -->
 * 
 *  Implements four reporters returning NetLogo extension objects
 *  for each of the four possible results of a comparison:
 *  
 *  +  `eq`
 *  +  `lt`
 *  +  `gt`
 *  +  `incmp`
 */
public class ComparisonResult implements Reporter
{
	private final PartiallyOrderedComparison command;
	
	public ComparisonResult(PartiallyOrderedComparison command) {
		this.command = command;
	}
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		switch(command) {
		case EQUAL:
			return NetLogoPartialComparison.EQ;
		case LESS_THAN:
			return NetLogoPartialComparison.LT;
		case GREATER_THAN:
			return NetLogoPartialComparison.GT;
		case INCOMPARABLE:
			return NetLogoPartialComparison.IN;
		default:
			throw new ExtensionException("PANIC!");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(Syntax.WildcardType());
	}
}
