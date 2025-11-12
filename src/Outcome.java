import org.nlogo.api.Reporter;
import org.nlogo.api.*;
import org.nlogo.core.*;

public class Outcome implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCase) {
			NetLogoCase logoCase = (NetLogoCase)args[0].get();

			return logoCase.getOutcome();
		}
		else {
			throw new ExtensionException("Argument to cbr:outcome must be a case");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType() }, Syntax.WildcardType());
	}
}
