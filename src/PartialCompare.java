/**
 * 
 */

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.Reporter;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

/**
 * 
 */
public class PartialCompare implements Reporter {

	@Override
	public Syntax getSyntax() {
		return SyntaxJ.reporterSyntax(new int[] {Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType()}, Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] args, Context context) throws ExtensionException {
		return NetLogoCaseBase.sn_cmp(args[0].get(), args[1].get(), args[2].get());
	}

}
