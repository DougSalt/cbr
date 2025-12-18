import org.nlogo.api.Reporter;
import org.nlogo.api.*;
import org.nlogo.core.*;

/**
 * 
 */

public class Size implements Reporter {
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase cb = (NetLogoCaseBase)args[0].get();
			return cb.size();
		}
		else {
			throw new ExtensionException("Argument to cbr:size must be a case base");
		}
	}

	@Override
	public Syntax getSyntax() {
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType() }, Syntax.NumberType());
	}
}
