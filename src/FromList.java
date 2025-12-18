/**
 * 
 */

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.Reporter;
import org.nlogo.core.LogoList;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

/**
 * 
 */
public class FromList implements Reporter {

	@Override
	public Syntax getSyntax() {
		return SyntaxJ.reporterSyntax(new int[] {Syntax.ListType()}, Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] args, Context context) throws ExtensionException {
		if(args[0].get() instanceof LogoList) {
			LogoList list = (LogoList)args[0].get();
			
			return NetLogoCaseBase.fromLogoList(list, context);
		}
		else {
			throw new ExtensionException("cbr:from-list expects a list as its argument");
		}
	}

}
