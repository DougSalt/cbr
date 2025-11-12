import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

public class GetTime implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCase) {
			NetLogoCase logoCase = (NetLogoCase)args[0].get();

			return logoCase.getTime();
		}
		else {
			throw new ExtensionException("Argument to cbr:get-time must be a case");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType() }, Syntax.NumberType());
	}
}
